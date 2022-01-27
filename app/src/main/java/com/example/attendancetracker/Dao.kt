package com.example.attendancetracker

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {
    @Insert
    suspend fun insert(item:AttendanceData)

    @Delete
    suspend fun delete(item:AttendanceData)

    @Update
    suspend fun update(item: AttendanceData)

    @Query("select * from Attendance")
     fun getAllData():LiveData<List<AttendanceData>>
}