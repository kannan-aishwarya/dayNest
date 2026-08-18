package com.example.planner

import androidx.compose.material3.Icon
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.planner.reusable.GradientBackground
import com.example.planner.viewmodel.GoalViewModel
import java.time.Instant
import java.time.format.TextStyle
import java.util.Locale
import java.time.LocalDate
import java.time.ZoneId
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.time.DayOfWeek
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    navController: NavController,
    viewModel: GoalViewModel
) {

    val goals by viewModel.goals.collectAsState(initial = emptyList())

    val today = LocalDate.now()

    var currentWeek by remember {
        mutableStateOf(LocalDate.now().with(DayOfWeek.SUNDAY ))
    }

    val weekDates = (0..6).map {
        currentWeek.plusDays(it.toLong())
    }

    val monthName = currentWeek.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val year = currentWeek.year

    GradientBackground {

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            // 🔹 Title
            Text(
                text = "Weekly Planner",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF4A2C2A)
            )

            Spacer(modifier = Modifier.height(30.dp))
            Text (
                text = "$monthName $year",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF4A2C2A),
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )


            // 🔹 Days Row
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                IconButton(onClick = {
                    currentWeek = currentWeek.minusWeeks(1)
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Previous Week")
                }
                Spacer(modifier = Modifier.width(6.dp))

                weekDates.forEach { date ->

                    val dateString = date.toString()
                    val hasGoal = goals.any { it.date == dateString }
                    val isToday = date == today

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                navController.navigate("weeklyform/$dateString")
                            }
                    )
                    {

                        Text(
                            text = date.dayOfWeek.name.take(3),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        hasGoal -> Color(0xFF4CAF50)
                                        else -> Color.Transparent
                                    }
                                )
                                .border(
                                    width = if (isToday) 2.dp else 0.dp,
                                    color = if (isToday) Color.Blue else Color.Transparent,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = date.dayOfMonth.toString(),
                                color = if (hasGoal) Color.White else Color.Black
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(6.dp))
                IconButton(onClick = {
                    currentWeek = currentWeek.plusWeeks(1)
                }) {
                    Icon(imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Next Week")
                }
            }
        }
    }
}

