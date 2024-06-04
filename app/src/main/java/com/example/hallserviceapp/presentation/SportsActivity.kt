package com.example.hallserviceapp.presentation

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hallserviceapp.R
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme

class SportsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SportsScreen()
                }
            }
        }
    }
}
@Composable
fun SportsScreen() {
    val lightBlue = Color(0xFF8FABE7) // Light blue color
    val gray = Color(0xFFFFFFFF)
    val green = Color(0xFF43B83A)
    val yellow = Color(0xFF504B50)

    val context = LocalContext.current


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
                .fillMaxSize()
                //.background(lightBlue)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            Headlineee("Sports")

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(gray, shape = RoundedCornerShape(10.dp))
                //.padding(16.dp)
            ) {
                Text(
                    text = "Available item list",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    textAlign = TextAlign.Right
                )
                Divider( // Add a divider line between student items
                    color = Color.Black,
                    thickness = 3.dp,
                    modifier = Modifier
                        .padding(8.dp)
                        .padding(horizontal = 10.dp)

                )
                Text(
                    text = "Room no : 101",
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .padding(horizontal = 18.dp)
                        //.align(Alignment.CenterHorizontally)
                        //.background(gray)
                )
                Divider( // Add a divider line between student items
                    color = yellow,
                    thickness = 3.dp,
                    modifier = Modifier
                        .width(170.dp)
                        .padding(8.dp)
                        .padding(horizontal = 10.dp)

                )
                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        //.background(gray, shape = RoundedCornerShape(10.dp))
                        .padding(horizontal = 20.dp)
                ) {
                    Text(text = "* Football", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "* Cricket ball and stamp", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "* Dart", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "* Handball", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "* Racquetbal", fontSize = 24.sp)
                    // Add more items as needed
                }
                Spacer(modifier = Modifier.height(70.dp))

            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun SportsScreenPreview() {
    HallServiceAppTheme {
        SportsScreen()
    }
}
