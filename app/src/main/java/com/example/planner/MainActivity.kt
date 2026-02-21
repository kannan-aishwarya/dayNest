package com.example.planner

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.planner.dao.GoalDao
import com.example.planner.dao.GoalDatabase
import com.example.planner.dao.GoalRepository
import com.example.planner.reusable.GradientBackground
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.viewmodel.GoalViewModel
import com.example.planner.viewmodel.GoalViewModelFactory

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = Room.databaseBuilder(
            applicationContext,
            GoalDatabase::class.java,
            "goal_db"
        ).build()

        val dao = database.goalDao()
        val repository = GoalRepository(dao)
        val factory = GoalViewModelFactory(repository)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "home") {
                composable("home") {HomeScreen(navController)}
                composable("gradient") {GradientBackground {  }}
                composable("weeklyhome") { backStackEntry ->

                    val viewModel: GoalViewModel = viewModel(factory = factory)

                    CalendarScreen(navController, viewModel)
                }

                composable(
                    route = "weeklyform/{date}",
                    arguments = listOf(navArgument("date") {
                        type = NavType.StringType
                    })
                ) { backStackEntry ->

                    val date = backStackEntry.arguments?.getString("date") ?: ""

                    val viewModel: GoalViewModel = viewModel(factory = factory)

                    WeeklyGoalForm(
                        selectedDate = date,
                        viewModel = viewModel,
                        onSubmit = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}