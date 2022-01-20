package com.example.attendancetracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [AttendanceData::class], version=1)
abstract class AttendanceDatabase :RoomDatabase(){

    abstract fun getDao() : Dao

    companion object {
        @Volatile
        private var instance: AttendanceDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance=it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AttendanceDatabase::class.java,
                "Attendance.db"
            ).build()
    }
}