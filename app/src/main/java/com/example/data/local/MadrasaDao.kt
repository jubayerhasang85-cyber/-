package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.AttendanceEntity
import com.example.data.model.BookEntity
import com.example.data.model.NoticeEntity
import com.example.data.model.ResultEntity
import com.example.data.model.StudentEntity
import com.example.data.model.UpdateLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MadrasaDao {

    // --- ছাত্র সংক্রান্ত ---
    @Query("SELECT * FROM students ORDER BY className, roll ASC")
    fun getAllStudents(): Flow<List<StudentEntity>>

    @Query("SELECT * FROM students WHERE className = :className ORDER BY roll ASC")
    fun getStudentsByClass(className: String): Flow<List<StudentEntity>>

    @Query("SELECT * FROM students WHERE phone = :phone AND className = :className AND roll = :roll LIMIT 1")
    suspend fun getStudentForLogin(phone: String, className: String, roll: Int): StudentEntity?

    @Query("SELECT COUNT(*) FROM students")
    fun getStudentCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudents(students: List<StudentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: StudentEntity): Long

    // --- হাজিরা সংক্রান্ত ---
    @Query("SELECT * FROM attendances WHERE className = :className AND date = :date ORDER BY studentRoll ASC")
    fun getAttendancesByClassAndDate(className: String, date: String): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendances WHERE className = :className AND studentRoll = :roll ORDER BY date DESC")
    fun getAttendancesForStudent(className: String, roll: Int): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendances WHERE date = :date")
    fun getAllAttendancesForDate(date: String): Flow<List<AttendanceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendances(attendances: List<AttendanceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: AttendanceEntity)

    @Update
    suspend fun updateAttendance(attendance: AttendanceEntity)

    // --- নোটিশ বোর্ড সংক্রান্ত ---
    @Query("SELECT * FROM notices ORDER BY isPinned DESC, id DESC")
    fun getAllNotices(): Flow<List<NoticeEntity>>

    @Query("SELECT * FROM notices WHERE category = :category ORDER BY id DESC")
    fun getNoticesByCategory(category: String): Flow<List<NoticeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotice(notice: NoticeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotices(notices: List<NoticeEntity>)

    @Delete
    suspend fun deleteNotice(notice: NoticeEntity)

    // --- ফলাফল ট্র্যাকার সংক্রান্ত ---
    @Query("SELECT * FROM results WHERE className = :className AND examTerm = :examTerm ORDER BY totalMarks DESC")
    fun getResultsByClassAndExam(className: String, examTerm: String): Flow<List<ResultEntity>>

    @Query("SELECT * FROM results WHERE className = :className AND studentRoll = :roll ORDER BY id DESC")
    fun getResultsForStudent(className: String, roll: Int): Flow<List<ResultEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResults(results: List<ResultEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: ResultEntity): Long

    // --- কিতাব ও বিষয়সমূহ ---
    @Query("SELECT * FROM books WHERE className = :className ORDER BY id ASC")
    fun getBooksByClass(className: String): Flow<List<BookEntity>>

    @Query("SELECT * FROM books ORDER BY className, id ASC")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<BookEntity>)

    // --- আপডেট লগ (প্রশাসক মাত্র) ---
    @Query("SELECT * FROM update_logs ORDER BY id DESC")
    fun getAllUpdateLogs(): Flow<List<UpdateLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateLog(log: UpdateLogEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateLogs(logs: List<UpdateLogEntity>)
}
