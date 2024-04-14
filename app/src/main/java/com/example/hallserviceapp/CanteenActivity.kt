package com.example.hallserviceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme

class CanteenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CanteenScreen()
                }
            }
        }
    }
}

@Composable
fun CanteenScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Add the background image
        Image(
            painter = painterResource(id = R.drawable.bgpic4), // Replace with your image resource
            contentDescription = null, // Content description can be null for decorative images
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds // Scale the image to fill the bounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
                //.background(Color(0xFF8FABE7)), // Custom background color
            //.padding(8.dp),
            //horizontalAlignment = Alignment.CenterHorizontally
            verticalArrangement = Arrangement.Top,

            ) {
            Headlineee("Canteen") // You can reuse the HeaderSection from DiningActivity or create a new one

            MealSection("Breakfast", "8:00 am", "Khichuri - Egg - Dal")
            MealSection("Lunch", "12:30 pm", "Chicken, Dal, Fish, Egg, Vegetable, Murighanto")
            MealSection("Dinner", "8:00 pm", "Chicken, Dal, Fish, Egg, Vegetable, Murighanto")
            SpecialMealSection("Special Meal", "10:00 pm", "Chicken-Khichuri")
            SpecialSection("Every Friday - Special biryani")

        }
    }
}
@Composable
fun SpecialMealSection(specialMealName: String, time: String, description: String) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "$specialMealName: $time",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.DarkGray
        )
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun CanteenScreenPreview() {
    CanteenScreen()
}
