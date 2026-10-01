package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * ইউজার রোল সমূহ
 */
enum class UserRole(val banglaTitle: String) {
    STUDENT("ছাত্র"),
    TEACHER("সহকারী শিক্ষক"),
    PRINCIPAL("প্রধান শিক্ষক"),
    ADMIN("অ্যাপ নির্মাতা / অ্যাডমিন")
}

/**
 * বর্তমান সক্রিয় ইউজার সেশন
 */
data class UserSession(
    val role: UserRole,
    val identifier: String, // ফোন নম্বর বা ইমেল
    val name: String,
    val studentClass: String = "",
    val studentRoll: Int = 0,
    val ilhaqNo: String = ""
)

/**
 * ছাত্র টেবিল
 */
@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val roll: Int,
    val name: String,
    val className: String,
    val phone: String,
    val fatherName: String,
    val guardianPhone: String = "",
    val address: String = "মাদ্রাসা ছাত্রাবাস"
)

/**
 * হাজিরা ট্র্যাকিং টেবিল
 */
@Entity(tableName = "attendances")
data class AttendanceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentRoll: Int,
    val studentName: String,
    val className: String,
    val date: String, // যেমন "২০২৬-১০-০১" বা "2026-10-01"
    val status: String, // "উপস্থিত", "অনুপস্থিত", "ছুটি", "বিলম্ব"
    val remarks: String = ""
)

/**
 * নোটিশ বোর্ড টেবিল
 */
@Entity(tableName = "notices")
data class NoticeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val date: String,
    val category: String, // "জরুরি", "পরীক্ষা", "ছুটি", "সাধারণ"
    val publishedBy: String,
    val isPinned: Boolean = false
)

/**
 * বিষয়ভিত্তিক ফলাফল মডেল ও টেবিল
 */
@Entity(tableName = "results")
data class ResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentRoll: Int,
    val studentName: String,
    val className: String,
    val examTerm: String, // "১ম সাময়িক (সানাবিয়া)", "অর্ধ-বার্ষিক (নিসফী)", "বার্ষিক ফাইনাল"
    val quranMarks: Int,
    val hadithMarks: Int,
    val fiqhMarks: Int,
    val nahwMarks: Int,
    val sarfMarks: Int,
    val generalMarks: Int, // বাংলা/গণিত/ইংরেজি/সাধারণ জ্ঞান
    val totalMarks: Int,
    val percentage: Double,
    val grade: String, // "মুমতাজ (A+)", "জায়্যিদ জিদ্দান (A)", "জায়্যিদ (B)", "মাকবুল (C)", "রাসিব (Fail)"
    val position: Int = 1,
    val remarks: String = "সন্তোষজনক"
)

/**
 * প্রতিটি শ্রেণির কিতাব ও পাঠ্যবই তালিকা
 */
@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val className: String,
    val bookName: String,
    val subject: String,
    val author: String,
    val totalMarks: Int = 100,
    val description: String = ""
)

/**
 * আপডেট লগ (শুধুমাত্র প্রশাসকের জন্য দৃশ্যমান)
 */
@Entity(tableName = "update_logs")
data class UpdateLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val version: String,
    val title: String,
    val description: String,
    val timestamp: String,
    val updatedBy: String = "সিস্টেম ডেভেলপার"
)
