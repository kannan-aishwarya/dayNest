package com.example.planner.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.planner.model.GoalEntity

@Database (
    entities = [GoalEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GoalDatabase : RoomDatabase(){

    abstract fun goalDao(): GoalDao

    companion object {
        @Volatile
        private var INSTANCE: GoalDatabase? = null

        fun getDatabase(context: android.content.Context): GoalDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                    GoalDatabase::class.java,
                    "goal_db"
                ).build().also { INSTANCE = it }
            }
    }
}