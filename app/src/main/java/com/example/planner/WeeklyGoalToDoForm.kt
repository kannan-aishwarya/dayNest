package com.example.planner

import android.text.Layout
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planner.dao.GoalDatabase
import com.example.planner.dao.GoalRepository
import com.example.planner.reusable.GradientBackground
import com.example.planner.viewmodel.GoalViewModel
import com.example.planner.viewmodel.GoalViewModelFactory
import kotlinx.coroutines.launch

@Composable
public fun WeeklyGoalForm(selectedDate: String,
                          viewModel: GoalViewModel,
                          onSubmit : () -> Unit
                          ){

    val context = LocalContext.current

    val database = remember {
        GoalDatabase.getDatabase(context)
    }

    val repository = remember {
        GoalRepository(database.goalDao())
    }

    val viewModel: GoalViewModel = viewModel(
        factory = GoalViewModelFactory(repository)
    )

    var text by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                    Snackbar(
                        snackbarData = snackbarData,
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    )
                }
            }
        }
    ) { padding ->
        GradientBackground {
            Box(modifier = Modifier.fillMaxSize()) {
                Box (modifier = Modifier
                    .align(Alignment.Center)
                    .size(height = 250.dp, width = 330.dp)
                    .background(Color(0x33FFA726))
                ){
                    Text("Add new goal",
                        modifier = Modifier.padding(25.dp),
                        fontSize = 20.sp,
                        color = Color(0xFF4A2C2A)
                    )
                    OutlinedTextField(
                        value = text,
                        onValueChange = {text = it},
                        label = { Text("Goal")},
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 70.dp, start = 10.dp, end = 10.dp)
                    )
                    Button(onClick = {
                        if (text.isNotBlank()) {
                            viewModel.addGoal(selectedDate, text)

                            scope.launch {
                                snackbarHostState.showSnackbar("Goal saved ✅")
                                onSubmit()
                            }

                        }
                    },
                        modifier = Modifier.padding(top = 150.dp, start = 120.dp)
                    ) {
                        Text("Submit")
                    }
                }
            }
        }
    }

}