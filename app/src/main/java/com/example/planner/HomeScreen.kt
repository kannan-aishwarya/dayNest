package com.example.planner

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.planner.reusable.GradientBackground
import kotlinx.coroutines.delay

@Composable
public fun HomeScreen(navController: NavController) {
    val customFont = FontFamily(Font(R.font.syne))
    LaunchedEffect(Unit) {
        delay(1000L)
        navController.navigate("weeklyhome") {
            popUpTo("weeklyhome") {inclusive = true}
        }
    }
    GradientBackground(){
        Box (modifier = Modifier.fillMaxSize()){
            Text("DAYNEST", modifier = Modifier.padding(top = 80.dp, start = 140.dp),
                fontFamily = customFont,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background,
                fontStyle = FontStyle.Italic)
            Image(
                painter = painterResource(R.drawable.homeimage),
                contentDescription = "Header Image",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 100.dp)
                    .size(1550.dp)

            )
        }
    }
}