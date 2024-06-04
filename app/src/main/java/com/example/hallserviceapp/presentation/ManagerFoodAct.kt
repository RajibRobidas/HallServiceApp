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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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


class DynamicFoodActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                DynamicFoodScreen()
            }
        }
    }
}

@Composable
fun DynamicFoodScreen() {

    val lightBlue = Color(0xFF8FABE7) // Light blue color

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                LazyColumn {

                    item { HeaderSectionAdM() }
                    item { Spacer(modifier = Modifier.height(30.dp)) }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                            //horizontalArrangement = Arrangement.Center // Center the items
                        ) {
                            ChangeOptionM(
                                "ADD Dining Food",
                                R.drawable.dyningfood,
                                "AddDiningActivity"
                            )
                            ChangeOptionM(
                                "Delete Dining Food",
                                R.drawable.dyningfood,
                                "DeleteDiningActivity"
                            )
                        }

                    }
                    item { Spacer(modifier = Modifier.height(30.dp)) }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            //horizontalArrangement = Arrangement.Center // Center the items
                        ) {
                            ChangeOptionM(
                                "ADD Canteen Food",
                                R.drawable.foodd,
                                "AddCanteenActivity"
                            )
                            ChangeOptionM(
                                "Delete Canteen Food",
                                R.drawable.foodd,
                                "DeleteCanteenActivity"
                            )
                        }

                    }
                    item { Spacer(modifier = Modifier.height(30.dp)) }

                }
            }
        }
    }
}
@Composable
fun ChangeOptionM(text: String, iconResId: Int, actName : String) {

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(140.dp)
            .height(140.dp)
            .padding(10.dp)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = "Sports",
            modifier = Modifier
                .clickable {
                    val intent = when (actName) {

                        "AddDiningActivity" -> Intent(context, AddDiningActivity::class.java)
                        "DeleteDiningActivity" -> Intent(context, DeleteDiningActivity::class.java)
                        "AddCanteenActivity" -> Intent(context, AddCanteenActivity::class.java)
                        "DeleteCanteenActivity" -> Intent(context, DeleteCanteenActivity::class.java)

                        else -> return@clickable
                    }
                    context.startActivity(intent)
                }
                .width(70.dp)
                .height(60.dp)
        )
        Text(
            text = text,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp),
            color = Color.White
        )
    }

}

@Composable
fun HeaderSectionAdM() {
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
                text = "Food Section",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .background(Color.White, shape = MaterialTheme.shapes.medium)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DynamicFoodScreenPreview() {
    HallServiceAppTheme {
        DynamicFoodScreen()
    }
}

