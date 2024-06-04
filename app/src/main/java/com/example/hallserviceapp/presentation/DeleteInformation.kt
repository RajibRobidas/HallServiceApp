package com.example.hallserviceapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

class UpdateStudentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UpdateStudentScreen()
                }
            }
        }
    }
}

@Composable
fun UpdateStudentScreen() {

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
                .fillMaxSize(),
                    //.background(lightBlue, shape = RoundedCornerShape(10.dp)),
                    horizontalAlignment = Alignment . CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Spacer(modifier = Modifier.height(30.dp)) // For spacing

            HeaderSectionAll("Delete Information")

            Spacer(modifier = Modifier.height(100.dp)) // For spacing

            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Delete Student",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .width(150.dp)
                        .background(Color.Gray, RoundedCornerShape(15.dp))
                        .padding(12.dp)
                        .clickable {
                            context.startActivity(
                                Intent(
                                    context,
                                    DeleteStudentActivity::class.java
                                )
                            )  // Change to the desired activity
                        }
                        .clip(RoundedCornerShape(8.dp)),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(50.dp)) // For spacing

            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Delete Offices",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .width(150.dp)
                        .background(Color.Gray, RoundedCornerShape(15.dp))
                        .padding(12.dp)
                        .clickable {
                            context.startActivity(
                                Intent(
                                    context,
                                    DeleteOfficesActivity::class.java
                                )
                            )  // Change to the desired activity
                        }
                        .clip(RoundedCornerShape(8.dp)),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(50.dp)) // For spacing

            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Delete Authority",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .width(150.dp)
                        .background(Color.Gray, RoundedCornerShape(15.dp))
                        .padding(12.dp)
                        .clickable {
                            context.startActivity(
                                Intent(
                                    context,
                                    DeleteAuthorityActivity::class.java
                                )
                            )  // Change to the desired activity
                        }
                        .clip(RoundedCornerShape(8.dp)),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UpdateStudentPreview() {
    HallServiceAppTheme {
        UpdateStudentScreen()
    }
}