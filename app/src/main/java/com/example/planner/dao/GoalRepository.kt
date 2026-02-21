package com.example.planner.dao

import com.example.planner.model.GoalEntity
import kotlinx.coroutines.flow.Flow

class GoalRepository (private val dao: GoalDao){

    suspend fun addGoal(date: String, goal: String) {
        dao.insertGoal(
            GoalEntity(date = date, goal = goal)
        )
    }

    fun getGoalsByDate(date: String): Flow<List<GoalEntity>> {
        return dao.getGoalsByDate(date)
    }

    fun getGoals(): Flow<List<GoalEntity>> {
        return dao.getGoals()
    }

    suspend fun deleteGoal(id: Int) {
        dao.deleteGoal(id)
    }
}