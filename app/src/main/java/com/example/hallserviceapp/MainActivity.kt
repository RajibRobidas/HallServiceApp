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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                MainScreen()
            }
        }
        // Write a message to the database

    }
}

@Composable
fun MainScreen() {

    var isShowingFirstImage by remember { mutableStateOf(true) }
    LaunchedEffect(key1 = true) {
        // Wait for 0.5 seconds before showing the second image
        delay(2500)
        isShowingFirstImage = false
    }
    if (isShowingFirstImage) {
        ColumnWithBackgroundImage(
            backgroundImageRes = R.drawable.bgpic2,
            contentPadding = 1.dp
        ) {
            // Your content goes here
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Syed Mujtaba Ali",
                    color = Color.White,
                    fontSize = 30.sp,  // Adjusted for better fit
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    //.background(color = Color(51, 165, 125, 255),shape = MaterialTheme.shapes.medium),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Hall",
                    color = Color.White,
                    fontSize = 34.sp,  // Adjusted for better fit
                    modifier = Modifier
                        //.padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    //.background(color = Color(51, 165, 125, 255),shape = MaterialTheme.shapes.medium),
                    textAlign = TextAlign.Center
                )
            }
        }
        /*
        Image(
            painter = painterResource(id = R.drawable.back_pic2),
            contentDescription = "Image",
            modifier = Modifier.fillMaxSize()
        )

         */
        // Display the first image with a delay
    } else {

        Scaffold(

            topBar = { AppBar() },
            content = { padding ->
                MainContent(padding)
            }
            // Remove the floatingActionButton if not needed
        )
    }

}

@Composable
fun ColumnWithBackgroundImage(
    backgroundImageRes: Int,
    contentPadding: Dp = 1.dp,
    content: @Composable () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        //val constraints = maxWidth.toFloat() to maxHeight.toFloat()
        val backgroundPainter = painterResource(id = backgroundImageRes)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = backgroundPainter,
                contentDescription = null,
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .fillMaxSize()
            )
        }

        // Add content on top of the background image
        Box(
            modifier = Modifier.fillMaxSize().padding(contentPadding),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    val lightblueTo = Color(0xFF4FF5CD) // Light blue color
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .background(lightblueTo) // Set the Topbackground color here
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                //.background(lightblueTo) // Set the Topbackground color here

        ){
            Text(
                text = "Hall App",
                color = Color(0xFF302B2B),
                fontSize = 20.sp,  // Adjusted for better fit
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .padding(start = 14.dp)
                    .width(270.dp)
            )
            Spacer(modifier = Modifier.width(25.dp))

            Text(
                text = "Admin",
                color = Color(0xFF727070),
                fontSize = 15.sp,  // Adjusted for better fit
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    //.padding(end = 5.dp)
                    .clickable {
                        context.startActivity(Intent(context, FrontAdminActivity::class.java))
                    },
            )
        }
    }

}

@Composable
fun MainContent(padding: PaddingValues) {
    // Define a light blue color
    val lightBlue = Color(0xFF8FABE7) // Light blue color

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Add the background image
        Image(
            painter = painterResource(id = R.drawable.bgppic6), // Replace with your image resource
            contentDescription = null, // Content description can be null for decorative images
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds // Scale the image to fill the bounds
        )

        // Content layout
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //CircularImageView(imageRes = R.drawable.logo_var_3, size = 120.dp)
            Image(
                painter = painterResource(id = R.drawable.logo_var_3),
                contentDescription = "Image",
                modifier = Modifier
                    .size(width = 120.dp, height = 120.dp)
                    .padding(2.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            CircularImageView(imageRes = R.drawable.hall_pic, size = 290.dp, height = 145.dp)
            HallTitle()
            Spacer(modifier = Modifier.height(40.dp))
            EnterButton()
        }
    }
}


@Composable
fun CircularImageView(imageRes: Int, size: Dp, height: Dp = size) {
    Card(
        shape = CircleShape,
        modifier = Modifier
            .size(width = size, height = height)
            .padding(2.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Image",
            modifier = Modifier.fillMaxSize()
        )
    }
    //Spacer(modifier = Modifier.height(30.dp))
}

@Composable
fun HallTitle() {
    Text(
        text = "SUST Hall 3",
        color = Color(0xFF49A7D3),
        fontSize = 30.sp,  // Adjusted for better fit
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun EnterButton() {
    val context = LocalContext.current
    Button(
        onClick = {
            context.startActivity(Intent(context, LoginActivity::class.java))  // Change to the desired activity
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF04CFB2)),
        modifier = Modifier
            .fillMaxWidth()
            .height(67.dp)
            .padding(vertical = 16.dp)
            //.background(Color(0xFF04CFB2), shape = RoundedCornerShape(15.dp))

    ) {
        Text(text = "Enter")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HallServiceAppTheme {
        MainScreen()
    }
}
