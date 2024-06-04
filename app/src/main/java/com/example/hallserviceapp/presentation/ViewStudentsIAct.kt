package com.example.hallserviceapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.hallserviceapp.R
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class StudentsInformationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StudentsInformationScreen()
                }
            }
        }
    }
}

@Composable
fun StudentsInformationScreen() {
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
                .padding(16.dp)
        ) {
            Headlineee("Student Information")
            StudentsInformationSection()
        }
    }
}

@Composable
fun StudentsInformationSection() {
    val database = FirebaseDatabase.getInstance().getReference("students")
    var studentList by remember { mutableStateOf(listOf<Student>()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                isLoading = false
                studentList = snapshot.children.mapNotNull { it.getValue(Student::class.java) }
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
        Text("Error loading student information.")
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(studentList) { student ->
                StudentItem(student)
            }
        }
    }
}
@Composable
fun StudentItem(student: Student) {
    val gree = Color(0xFF36A2D8)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            //.background(Color.White) ,// Set a white background color for the card
        //elevation = 4.dp // Add elevation for a shadow effect
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val painter: Painter = rememberImagePainter(student.imageUrl)

                Image(
                    painter = painter,
                    contentDescription = "Student Image",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape) // Clip the image to a circle shape
                )
                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = student.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3D82D5)

                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Registration: ${student.registrationNumber}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Department: ${student.department}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = gree
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Hometown: ${student.hometown}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = gree
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Phone: ${student.phoneNumber}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = gree
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Room No: ...",
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
}

@Composable
fun StudentItem2(student: Student) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //ShowImages(authority.imageUrl)

            val painter: Painter = rememberImagePainter(student.imageUrl)

            Image(
                painter = painter,
                contentDescription = "Student Image",
                modifier = Modifier
                    .size(80.dp)
                    .padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Name:  ${student.name}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Registration Number:  ${student.registrationNumber}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Department:  ${student.department}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Hometown:  ${student.hometown}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Phone Number:  ${student.phoneNumber}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Add more details or actions for each student item here

            }
        }
    }
}

data class Student(
    val id : String = "",
    val department: String = "",
    val hometown: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val registrationNumber: String = ""
)

@Preview(showBackground = true)
@Composable
fun StudentsInformationScreenPreview() {
    HallServiceAppTheme {
        StudentsInformationScreen()
    }
}
