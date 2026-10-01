package com.example.data.repository

import com.example.data.local.MadrasaDao
import com.example.data.model.AttendanceEntity
import com.example.data.model.BookEntity
import com.example.data.model.NoticeEntity
import com.example.data.model.ResultEntity
import com.example.data.model.StudentEntity
import com.example.data.model.UpdateLogEntity
import kotlinx.coroutines.flow.Flow

class MadrasaRepository(private val dao: MadrasaDao) {

    // Students
    fun getAllStudents(): Flow<List<StudentEntity>> = dao.getAllStudents()
    fun getStudentsByClass(className: String): Flow<List<StudentEntity>> = dao.getStudentsByClass(className)
    fun getStudentCount(): Flow<Int> = dao.getStudentCount()
    suspend fun getStudentForLogin(phone: String, className: String, roll: Int): StudentEntity? =
        dao.getStudentForLogin(phone, className, roll)
    suspend fun insertStudent(student: StudentEntity): Long = dao.insertStudent(student)

    // Attendances
    fun getAttendancesByClassAndDate(className: String, date: String): Flow<List<AttendanceEntity>> =
        dao.getAttendancesByClassAndDate(className, date)
    fun getAttendancesForStudent(className: String, roll: Int): Flow<List<AttendanceEntity>> =
        dao.getAttendancesForStudent(className, roll)
    fun getAllAttendancesForDate(date: String): Flow<List<AttendanceEntity>> =
        dao.getAllAttendancesForDate(date)
    suspend fun saveAttendances(list: List<AttendanceEntity>) = dao.insertAttendances(list)
    suspend fun updateAttendance(attendance: AttendanceEntity) = dao.updateAttendance(attendance)

    // Notices
    fun getAllNotices(): Flow<List<NoticeEntity>> = dao.getAllNotices()
    suspend fun publishNotice(notice: NoticeEntity): Long = dao.insertNotice(notice)
    suspend fun deleteNotice(notice: NoticeEntity) = dao.deleteNotice(notice)

    // Results
    fun getResultsByClassAndExam(className: String, examTerm: String): Flow<List<ResultEntity>> =
        dao.getResultsByClassAndExam(className, examTerm)
    fun getResultsForStudent(className: String, roll: Int): Flow<List<ResultEntity>> =
        dao.getResultsForStudent(className, roll)
    suspend fun saveResult(result: ResultEntity): Long = dao.insertResult(result)

    // Books
    fun getBooksByClass(className: String): Flow<List<BookEntity>> = dao.getBooksByClass(className)
    fun getAllBooks(): Flow<List<BookEntity>> = dao.getAllBooks()

    // Update Logs
    fun getAllUpdateLogs(): Flow<List<UpdateLogEntity>> = dao.getAllUpdateLogs()
    suspend fun addUpdateLog(log: UpdateLogEntity): Long = dao.insertUpdateLog(log)
}
