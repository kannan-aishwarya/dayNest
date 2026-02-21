package com.example.planner.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.planner.model.GoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: GoalEntity)

    @Query("SELECT * FROM goals WHERE date = :date")
    fun getGoalsByDate(date: String): Flow<List<GoalEntity>>

    @Query("SELECT * FROM goals")
    fun getGoals(): Flow<List<GoalEntity>>


    @Query("DELETE FROM goals WHERE id = :id")
    suspend fun deleteGoal(id: Int)
}