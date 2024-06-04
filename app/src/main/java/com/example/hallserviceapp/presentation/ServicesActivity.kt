package com.example.hallserviceapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hallserviceapp.R
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme

class ServicesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ServicesScreen()
                }
            }
        }
    }
}

@Composable
fun ServicesScreen() {
    val lightBlue = Color(0xFF8FABE7) // Light blue color
    val green = Color(0xFF43B83A)

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
                //.background(lightBlue, shape = RoundedCornerShape(10.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            Headlineee("Services")
            ServicesList()
        }
    }
}

@Composable
fun ServicesList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ServiceItem(
            icon = Icons.Default.Construction,
            serviceName = "Room Cleaning",
            roomNumber = "Room no: 101"
        )
        ServiceItem(
            icon = Icons.Default.Construction,
            serviceName = "Glass Repairing",
            roomNumber = "Room no: 102"
        )
        ServiceItem(
            icon = Icons.Default.Construction,
            serviceName = "Light Repairing",
            roomNumber = "Room no: 103"
        )
        ServiceItem(
            icon = Icons.Default.Construction,
            serviceName = "Drilling",
            roomNumber = "Room no: 104"
        )
        ServiceItem(
            icon = Icons.Default.Construction,
            serviceName = "Wash Room Cleaning",
            roomNumber = "Room no: 105"
        )
    }
}

@Composable
fun ServiceItem(icon: ImageVector, serviceName: String, roomNumber: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp, 80.dp)
                .padding(end = 16.dp)
        )

        Column {
            Text(text = serviceName, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            //Text(text = roomNumber, fontSize = 16.sp)
        }
    }
    Spacer(modifier = Modifier.height(20.dp)) // For spacing

}

@Preview(showBackground = true)
@Composable
fun ServicesScreenPreview() {
    HallServiceAppTheme {
        ServicesScreen()
    }
}
