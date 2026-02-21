package com.example.planner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planner.dao.GoalRepository
import com.example.planner.model.GoalEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GoalViewModel (
    private val repository: GoalRepository
) : ViewModel() {

    private val _goals = MutableStateFlow<List<GoalEntity>>(emptyList())
    val goals: StateFlow<List<GoalEntity>> = _goals

    fun getGoalsByDate(date: String) {
        viewModelScope.launch {
            repository.getGoalsByDate(date).collect {
                _goals.value = it
            }
        }
    }

    init {
        viewModelScope.launch {
            repository.getGoals().collect {
                _goals.value = it
            }
        }
    }



    fun addGoal(date: String, goal: String) {
        viewModelScope.launch {
            repository.addGoal(date,goal)
        }
    }

    fun deleteGoal(id: Int) {
        viewModelScope.launch {
            repository.deleteGoal(id)
        }
    }
}