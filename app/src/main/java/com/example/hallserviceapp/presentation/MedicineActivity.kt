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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
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

class MedicineActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MedicineScreen()
                }
            }
        }
    }
}

@Composable
fun MedicineScreen() {
    val lightBlue = Color(0xFF8FABE7) // Light blue color
    val gray = Color(0xFF504B50)
    val green = Color(0xFF43B83A)
    val yellow = Color(0xFFC5B685)

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
            Headlineee("Medicine")

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
                //.padding(16.dp)
            ) {
                Text(
                    text = "Available medicine list:",
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
                    color = gray,
                    thickness = 3.dp,
                    modifier = Modifier
                        .width(170.dp)
                        .padding(8.dp)
                        .padding(horizontal = 10.dp)

                )
                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        //.background(Color.White, shape = RoundedCornerShape(10.dp))
                        .padding(16.dp)
                ) {
                    Text(text = "* First aid kits", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "* Napa Extra Tablet", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "* Histacin", fontSize = 24.sp)
                    // Add more medicines as needed
                }

                Spacer(modifier = Modifier.height(70.dp))

            }

        }
    }
}

@Composable
fun Headlineeee(headlinee : String) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            //Spacer(modifier = Modifier.width(35.dp)) // For spacing
            Text(
                text = headlinee,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier
                    //.background(Color.White, shape = RoundedCornerShape(10.dp))
                    .padding(10.dp)
                    .clip(RoundedCornerShape(8.dp)),
                textAlign = TextAlign.Center
            )
        }
        Image(
            painter = painterResource(id = R.drawable.headline),
            contentDescription = "Headline",
            modifier = Modifier
                .clickable {
                    context.startActivity(
                        Intent(
                            context,
                            UserActivity::class.java
                        )
                    )  // Change to the desired activity
                }
                .padding(vertical = 10.dp)
                //.padding(top = 4.dp)
                .size(50.dp)
                .padding(9.dp)
        )
    }
}
@Preview(showBackground = true)
@Composable
fun MedicineScreenPreview() {
    HallServiceAppTheme {
        MedicineScreen()
    }
}

