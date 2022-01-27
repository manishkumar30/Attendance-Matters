package com.example.attendancetracker

class AttendanceRepository(private val db:AttendanceDatabase) {
    suspend fun insert(items:AttendanceData)= db.getDao().insert(items)
    suspend fun delete(items: AttendanceData)= db.getDao().delete(items)
    suspend fun update(items:AttendanceData) = db.getDao().update(items)
    fun getAllData()= db.getDao().getAllData()
}