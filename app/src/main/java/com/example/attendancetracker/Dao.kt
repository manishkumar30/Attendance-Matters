package com.example.attendancetracker

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item:AttendanceData)

    @Delete
    suspend fun delete(item:AttendanceData)

    @Query("select * from Attendance")
    suspend fun getAllData():LiveData<List<AttendanceData>>
}