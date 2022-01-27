package com.example.attendancetracker

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AttendanceViewModel(private val repository: AttendanceRepository):ViewModel() {

    fun insert(items:AttendanceData)= CoroutineScope(Dispatchers.Main).launch {
        repository.insert(items)
    }
    fun delete(items: AttendanceData)= CoroutineScope(Dispatchers.Main).launch {
        repository.delete(items)
    }
    fun update(items: AttendanceData)= CoroutineScope(Dispatchers.Main).launch {
        repository.update(items)
    }
    fun getAllData()= repository.getAllData()
}
