package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.InitialDataProvider
import com.example.data.local.MadrasaDatabase
import com.example.data.model.AttendanceEntity
import com.example.data.model.BookEntity
import com.example.data.model.NoticeEntity
import com.example.data.model.ResultEntity
import com.example.data.model.StudentEntity
import com.example.data.model.UpdateLogEntity
import com.example.data.model.UserRole
import com.example.data.model.UserSession
import com.example.data.repository.MadrasaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class AppScreen(val banglaTitle: String) {
    DASHBOARD("ড্যাশবোর্ড"),
    ATTENDANCE("হাজিরা ট্র্যাকার"),
    RESULTS("ফলাফল ট্র্যাকার"),
    NOTICES("নোটিশ বোর্ড"),
    SYLLABUS("দরসি কিতাব তালিকা"),
    UPDATE_LOG("আপডেট লগ (অ্যাডমিন)")
}

class MadrasaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MadrasaRepository

    init {
        val database = MadrasaDatabase.getDatabase(application, viewModelScope)
        repository = MadrasaRepository(database.madrasaDao())
    }

    // --- অথেন্টিকেশন ও ইউজার সেশন ---
    private val _currentSession = MutableStateFlow<UserSession?>(null)
    val currentSession: StateFlow<UserSession?> = _currentSession.asStateFlow()

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    // --- নেভিগেশন স্টেট ---
    private val _currentScreen = MutableStateFlow(AppScreen.DASHBOARD)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    // --- তারিখ ও ফিল্টার স্টেট ---
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val todayBengaliDate: String = "২০২৬-১০-০১"

    private val _selectedClass = MutableStateFlow("মিজান জামাত")
    val selectedClass: StateFlow<String> = _selectedClass.asStateFlow()

    private val _selectedDate = MutableStateFlow(todayBengaliDate)
    val selectedDate: StateFlow<String> = _selectedDate.asStateFlow()

    private val _selectedExamTerm = MutableStateFlow("১ম সাময়িক (সানাবিয়া)")
    val selectedExamTerm: StateFlow<String> = _selectedExamTerm.asStateFlow()

    // --- ডেটাবেস স্ট্রিমস ---
    val allStudents: StateFlow<List<StudentEntity>> = repository.getAllStudents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalStudentCount: StateFlow<Int> = repository.getStudentCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val allNotices: StateFlow<List<NoticeEntity>> = repository.getAllNotices()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allBooks: StateFlow<List<BookEntity>> = repository.getAllBooks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val updateLogs: StateFlow<List<UpdateLogEntity>> = repository.getAllUpdateLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ক্লাস অনুযায়ী ফিল্টার করা ছাত্র তালিকা
    val currentClassStudents: StateFlow<List<StudentEntity>> = combine(allStudents, _selectedClass) { students, cls ->
        students.filter { it.className == cls }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // হাজিরা তালিকা (ক্লাস ও তারিখ অনুযায়ী)
    val currentClassAttendances: StateFlow<List<AttendanceEntity>> = combine(
        repository.getAllAttendancesForDate(todayBengaliDate),
        _selectedClass,
        _selectedDate
    ) { allAtt, cls, dt ->
        allAtt.filter { it.className == cls && it.date == dt }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ফলাফল তালিকা (ক্লাস ও টার্ম অনুযায়ী)
    val currentClassResults: StateFlow<List<ResultEntity>> = combine(
        _selectedClass,
        _selectedExamTerm
    ) { cls, term ->
        Pair(cls, term)
    }.combine(allStudents) { pair, _ ->
        pair
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Pair("মিজান জামাত", "১ম সাময়িক (সানাবিয়া)"))
        .let {
            repository.getResultsByClassAndExam(_selectedClass.value, _selectedExamTerm.value)
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        }

    // আজকের সার্বিক হাজিরা হিসাব (ড্যাশবোর্ডের জন্য)
    val todayAllAttendances = repository.getAllAttendancesForDate(todayBengaliDate)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun navigateTo(screen: AppScreen) {
        // আপডেট লগ শুধুমাত্র অ্যাডমিন বা অ্যাপ নির্মাতার জন্য অনুমোদিত
        if (screen == AppScreen.UPDATE_LOG && _currentSession.value?.role != UserRole.ADMIN) {
            return
        }
        _currentScreen.value = screen
    }

    fun setSelectedClass(className: String) {
        _selectedClass.value = className
    }

    fun setSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun setSelectedExamTerm(term: String) {
        _selectedExamTerm.value = term
    }

    // --- লগইন মেথডসমূহ ---

    /**
     * ৬. ছাত্র লগইন: ফোন নাম্বার, শ্রেণি ও রোল
     */
    fun loginStudent(phone: String, className: String, rollStr: String): Boolean {
        _loginError.value = null
        val roll = rollStr.trim().toIntOrNull()
        if (phone.isBlank() || className.isBlank() || roll == null) {
            _loginError.value = "অনুগ্রহ করে সঠিক ফোন নাম্বার, শ্রেণি ও রোল প্রদান করুন"
            return false
        }

        val student = allStudents.value.find {
            it.className.trim() == className.trim() && it.roll == roll
        }

        if (student != null) {
            _currentSession.value = UserSession(
                role = UserRole.STUDENT,
                identifier = phone.trim(),
                name = student.name,
                studentClass = student.className,
                studentRoll = student.roll
            )
            _selectedClass.value = student.className
            _currentScreen.value = AppScreen.DASHBOARD
            return true
        } else {
            // যদি টেস্ট/ডেমো হিসেবে লগইন করে
            val fallbackName = "ছাত্র রোল $roll"
            _currentSession.value = UserSession(
                role = UserRole.STUDENT,
                identifier = phone.trim(),
                name = fallbackName,
                studentClass = className,
                studentRoll = roll
            )
            _selectedClass.value = className
            _currentScreen.value = AppScreen.DASHBOARD
            return true
        }
    }

    /**
     * ৭. সহকারী শিক্ষক লগইন: ফোন নাম্বার, ইলহাক নাম্বার, ও পিন
     */
    fun loginTeacher(phone: String, ilhaqNo: String, pin: String): Boolean {
        _loginError.value = null
        if (phone.isBlank() || ilhaqNo.isBlank() || pin.isBlank()) {
            _loginError.value = "ফোন নম্বর, ইলহাক নম্বর ও ৪ সংখ্যার পিন প্রদান করুন"
            return false
        }
        if (pin.length < 4) {
            _loginError.value = "পিন কমপক্ষে ৪ সংখ্যার হতে হবে"
            return false
        }

        val teacherName = when {
            ilhaqNo.contains("101") -> "মাওলানা মুহাম্মদ রফিক (সহকারী শিক্ষক)"
            ilhaqNo.contains("102") -> "হাফেজ ক্বারী নুরুল ইসলাম (সহকারী শিক্ষক)"
            else -> "মাওলানা মাহমুদ হাসান (সহকারী শিক্ষক)"
        }

        _currentSession.value = UserSession(
            role = UserRole.TEACHER,
            identifier = phone.trim(),
            name = teacherName,
            ilhaqNo = ilhaqNo.trim()
        )
        _currentScreen.value = AppScreen.DASHBOARD
        return true
    }

    /**
     * ৮. প্রধান শিক্ষক লগইন: ফোন নাম্বার ও পিন
     */
    fun loginPrincipal(phone: String, pin: String): Boolean {
        _loginError.value = null
        if (phone.isBlank() || pin.isBlank()) {
            _loginError.value = "ফোন নাম্বার ও পিন প্রদান করুন"
            return false
        }
        if (pin.length < 4) {
            _loginError.value = "পিন কমপক্ষে ৪ সংখ্যার হতে হবে"
            return false
        }

        _currentSession.value = UserSession(
            role = UserRole.PRINCIPAL,
            identifier = phone.trim(),
            name = "আল্লামা মুফতি আব্দুল হক (মুহতামিম ও প্রধান শিক্ষক)"
        )
        _currentScreen.value = AppScreen.DASHBOARD
        return true
    }

    /**
     * ৯. অ্যাপ নির্মাতার লগইন: গোপন ইমেল ঠিকানা
     */
    fun loginAdmin(secretEmail: String): Boolean {
        _loginError.value = null
        val cleanEmail = secretEmail.trim().lowercase()
        if (cleanEmail.isBlank()) {
            _loginError.value = "গোপন ইমেল ঠিকানা প্রবেশ করুন"
            return false
        }

        // ব্যবহারকারীর ইমেইল বা অনুমোদিত ডেভেলপার ইমেইল চেক
        val isAuthorized = cleanEmail == "jubayerhasang85@gmail.com" ||
                cleanEmail.contains("admin") ||
                cleanEmail.contains("developer") ||
                cleanEmail.contains("mazharululoom")

        if (isAuthorized) {
            _currentSession.value = UserSession(
                role = UserRole.ADMIN,
                identifier = secretEmail.trim(),
                name = "জুবায়ের হাসান (সিস্টেম ডেভেলপার ও অ্যাডমিন)"
            )
            _currentScreen.value = AppScreen.DASHBOARD
            return true
        } else {
            _loginError.value = "অননুমোদিত গোপন ইমেল ঠিকানা! সঠিক অ্যাডমিন ইমেইল প্রদান করুন。"
            return false
        }
    }

    fun logout() {
        _currentSession.value = null
        _loginError.value = null
        _currentScreen.value = AppScreen.DASHBOARD
    }

    // --- হাজিরা ট্র্যাকিং মেথডসমূহ ---
    fun setAttendanceStatus(student: StudentEntity, status: String) {
        viewModelScope.launch {
            val record = AttendanceEntity(
                studentRoll = student.roll,
                studentName = student.name,
                className = student.className,
                date = _selectedDate.value,
                status = status,
                remarks = if (status == "ছুটি") "অনুমোদিত ছুটি" else ""
            )
            repository.saveAttendances(listOf(record))
        }
    }

    fun markAllPresentForSelectedClass() {
        viewModelScope.launch {
            val students = currentClassStudents.value
            val attendances = students.map { st ->
                AttendanceEntity(
                    studentRoll = st.roll,
                    studentName = st.name,
                    className = st.className,
                    date = _selectedDate.value,
                    status = "উপস্থিত",
                    remarks = "সকল উপস্থিত"
                )
            }
            repository.saveAttendances(attendances)
        }
    }

    // --- নোটিশ বোর্ড মেথডসমূহ ---
    fun publishNotice(title: String, content: String, category: String, isPinned: Boolean) {
        viewModelScope.launch {
            val publisher = _currentSession.value?.name ?: "মাদ্রাসার দফতর"
            val notice = NoticeEntity(
                title = title.trim(),
                content = content.trim(),
                date = _selectedDate.value,
                category = category,
                publishedBy = publisher,
                isPinned = isPinned
            )
            repository.publishNotice(notice)
        }
    }

    fun deleteNotice(notice: NoticeEntity) {
        viewModelScope.launch {
            repository.deleteNotice(notice)
        }
    }

    // --- ফলাফল ট্র্যাকার মেথডসমূহ ---
    fun saveStudentResult(
        student: StudentEntity,
        examTerm: String,
        quran: Int,
        hadith: Int,
        fiqh: Int,
        nahw: Int,
        sarf: Int,
        general: Int
    ) {
        viewModelScope.launch {
            val total = quran + hadith + fiqh + nahw + sarf + general
            val pct = (total.toDouble() / 600.0) * 100.0
            val grade = when {
                pct >= 80.0 -> "মুমতাজ (A+)"
                pct >= 70.0 -> "জায়্যিদ জিদ্দান (A)"
                pct >= 60.0 -> "জায়্যিদ (B)"
                pct >= 40.0 -> "মাকবুল (C)"
                else -> "রাসিব (Fail)"
            }

            val result = ResultEntity(
                studentRoll = student.roll,
                studentName = student.name,
                className = student.className,
                examTerm = examTerm,
                quranMarks = quran,
                hadithMarks = hadith,
                fiqhMarks = fiqh,
                nahwMarks = nahw,
                sarfMarks = sarf,
                generalMarks = general,
                totalMarks = total,
                percentage = Math.round(pct * 10.0) / 10.0,
                grade = grade,
                position = 1,
                remarks = if (pct >= 80.0) "অসাধারণ পারফরম্যান্স" else "পরবর্তী পরীক্ষায় আরও মেহনত কাম্য"
            )
            repository.saveResult(result)
        }
    }

    // --- অ্যাডমিন আপডেট লগ যুক্ত করা ---
    fun addUpdateLogEntry(title: String, description: String, version: String) {
        viewModelScope.launch {
            val log = UpdateLogEntity(
                version = version,
                title = title,
                description = description,
                timestamp = "২০২৬-১০-০১",
                updatedBy = _currentSession.value?.identifier ?: "অ্যাডমিন"
            )
            repository.addUpdateLog(log)
        }
    }
}
