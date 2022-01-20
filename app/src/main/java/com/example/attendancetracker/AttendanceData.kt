package com.example.attendancetracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Attendance")
data class AttendanceData (

    @ColumnInfo(name = "subjectName")
    var subjectName : String,

    @ColumnInfo(name = "totalPresent")
    var totalPresent : Int,

    @ColumnInfo(name = "totalClasses")
    var totalClasses : Int,
    )
{
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
}