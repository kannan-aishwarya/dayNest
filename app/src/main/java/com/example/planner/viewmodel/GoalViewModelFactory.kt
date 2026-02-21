package com.example.planner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.planner.dao.GoalRepository

class GoalViewModelFactory (
    private val repository: GoalRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GoalViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}