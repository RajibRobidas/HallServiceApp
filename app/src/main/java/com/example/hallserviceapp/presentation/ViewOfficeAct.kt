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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.hallserviceapp.R
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OfficeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OfficeScreen()
                }
            }
        }
    }
}

@Composable
fun OfficeScreen() {
    val lightBlue = Color(0xFF8FABE7) // Light blue color

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
                .padding(16.dp)
        ) {
            HeaderSectionOffices()
            // SearchSection()
            OfficeInformationSection()
        }
    }

}

@Composable
fun HeaderSectionOffices() {
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
                text = "Office Information",
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
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

@Composable
fun OfficeInformationSection() {
    val database = FirebaseDatabase.getInstance().getReference("offices")
    var officeList by remember { mutableStateOf(listOf<Office>()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                isLoading = false
                officeList = snapshot.children.mapNotNull { it.getValue(Office::class.java) }
            }

            override fun onCancelled(error: DatabaseError) {
                isLoading = false
                isError = true
            }
        })
    }

    if (isLoading) {
        //CircularProgressIndicator()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
            Text(
                text = "Loading... Please wait",
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray
            )
        }
    } else if (isError) {
        Text("Error loading office information.")
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(officeList) { office ->
                OfficeItem(office)
            }
        }
    }
}
@Composable
fun OfficeItem(office: Office) {
    val gree = Color(0xFF36A2D8)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter: Painter = rememberImagePainter(office.imageUrl)

            Image(
                painter = painter,
                contentDescription = "Office Image",
                modifier = Modifier
                    .size(80.dp)
                    .padding(start = 8.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Name: ${office.name}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3D82D5)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Designation: ${office.designation}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Email: ${office.email}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = gree
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Phone Number: ${office.phoneNumber}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = gree
                )
            }
        }
        Divider( // Add a divider line between student items
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}


data class Office(
    val id: String = "",
    val designation: String = "",
    val email: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val phoneNumber: String = ""
)

@Preview(showBackground = true)
@Composable
fun OfficeScreenPreview() {
    HallServiceAppTheme {
        OfficeScreen()
    }
}
