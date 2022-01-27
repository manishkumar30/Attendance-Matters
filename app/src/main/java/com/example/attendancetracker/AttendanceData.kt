package com.example.attendancetracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Attendance")
data class AttendanceData (
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,

    @ColumnInfo(name = "subjectName")
    var subjectName : String,

    @ColumnInfo(name = "totalPresent")
    var totalPresent : Int,

    @ColumnInfo(name = "totalAbsent")
    var totalAbsent : Int,

    @ColumnInfo(name = "cutoff")
    var cutoff:Float,

    @ColumnInfo(name = "lectures")
    var n:Int
    )
