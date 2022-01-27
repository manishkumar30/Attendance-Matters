package com.example.attendancetracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AttendanceViewModelFactory(private val repository: AttendanceRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AttendanceViewModel(repository) as T
    }
}