package com.example.hallserviceapp

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
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
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme

class FoodActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FoodScreen()
                }
            }
        }
    }
}

@Composable
fun FoodScreen() {
    val lightBlue = Color(0xFF8FABE7) // Light blue color
    val gray = Color(0xFFA5960D)
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
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .padding(horizontal = 18.dp)
            ) {
                Headlineee("Food")
            }
            val lightGoldB = Color(0xFFE1E6E5)
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp)
                ) {
                    // Your other UI elements go here
                    Spacer(modifier = Modifier.height(25.dp)) // For spacing

                    Divider( // Add a divider line between student items
                        color = Color.White,
                        thickness = 10.dp,
                        modifier = Modifier
                            .padding(8.dp)
                            .padding(horizontal = 10.dp)
                            .background(Color.White, shape = RoundedCornerShape(10.dp))

                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable {
                                context.startActivity(
                                    Intent(
                                        context,
                                        ReadDiningActivity::class.java
                                    )
                                )  // Change to the desired activity
                            }
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.dyningfood),
                                contentDescription = "Dining",
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(8.dp)
                            )
                            Text(
                                text = "Enter Dining Food",
                                fontSize = 15.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier//.padding(start = 16.dp)

                                    .padding(start = 12.dp)
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable {
                                    context.startActivity(
                                        Intent(
                                            context,
                                            ReadCanteenActivity::class.java
                                        )
                                    )  // Change to the desired activity
                                }

                        ){
                            Image(
                                painter = painterResource(id = R.drawable.foodd),
                                contentDescription = "Dining",
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(8.dp)
                            )
                            Text(
                                text = "Enter Canteen Food",
                                fontSize = 15.sp,
                                color = Color.White,
                                textAlign = TextAlign.Justify,
                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(45.dp)) // For spacing

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                            //.background(lightGoldB, shape = RoundedCornerShape(10.dp))
                            .clickable {
                                context.startActivity(
                                    Intent(
                                        context,
                                        DiningActivity::class.java
                                    )
                                )  // Change to the desired activity
                            }
                            .padding(horizontal = 12.dp)
                            .background(lightGoldB, shape = RoundedCornerShape(10.dp))

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.dyningfood),
                            contentDescription = "Dining",
                            modifier = Modifier

                                .size(80.dp)
                                .padding(10.dp)

                        )
                        //Spacer(modifier = Modifier.width(12.dp)) // For spacing
                        Text(
                            text = "Dining",
                            fontSize = 34.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier//.padding(start = 16.dp)
                                .fillMaxWidth()
                        )
                    }

                    //Spacer(modifier = Modifier.height(20.dp)) // For spacing

                    // Add more Rows or other components as needed
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 8.dp)
                            //.background(lightGoldB, shape = RoundedCornerShape(10.dp))
                            .clickable {
                                context.startActivity(
                                    Intent(
                                        context,
                                        CanteenActivity::class.java
                                    )
                                )  // Change to the desired activity
                            }
                            .padding(horizontal = 12.dp)
                            .background(lightGoldB, shape = RoundedCornerShape(10.dp))

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.foodd),
                            contentDescription = "Canteen",
                            modifier = Modifier

                                .size(80.dp)
                                .padding(10.dp)
                        )
                        Text(
                            text = "Canteen",
                            fontSize = 34.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier//.padding(start = 16.dp)
                                .fillMaxWidth()
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 8.dp)
                            //.background(lightGoldB, shape = RoundedCornerShape(10.dp))
                            .clickable {
                                context.startActivity(
                                    Intent(
                                        context,
                                        ShopActivity::class.java
                                    )
                                )  // Change to the desired activity
                            }
                            .padding(horizontal = 12.dp)
                            .background(lightGoldB, shape = RoundedCornerShape(10.dp))


                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.shop_food),
                            contentDescription = "Shop",
                            modifier = Modifier

                                .size(80.dp)
                                .padding(10.dp)

                        )
                        Text(
                            text = "Food Shop",
                            fontSize = 34.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier//.padding(start = 16.dp)
                                .fillMaxWidth()
                        )
                    }

                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun FoodScreenPreview() {
    FoodScreen()
}
