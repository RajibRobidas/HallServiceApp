package com.example.hallserviceapp

import android.content.Context
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



class ComplaintsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            HallServiceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ComplaintsScreen()
                }
            }
        }
    }
}

@Composable
fun ComplaintsScreen() {

    val lightBlue = Color(0xFF8FABE7) // Light blue color

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
                .padding(16.dp)

        ) {
            Headlineee("Complaints")
            WriteSection()
        }
    }
}
@Composable
fun WriteSection() {
    val yellow = Color(0xFFC5B685)
    var titleState by remember { mutableStateOf(TextFieldValue("Enter complaint title")) }
    var textState by remember { mutableStateOf(TextFieldValue("Type your complaint here")) }
    val context = LocalContext.current
    var isUploading by remember { mutableStateOf(false) }
    val lightBlue = Color(0xFF8FABE7) // Light blue color


    // UI arrangements for title and text input
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            //.background(lightBlue, shape = RoundedCornerShape(10.dp))
            .padding(4.dp)
    ) {
        InputTitle(titleState, "Enter complaint title") { titleState = it }
        Spacer(modifier = Modifier.height(8.dp))
        InputField(textState, "Write your complaint here") { textState = it }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                textState = TextFieldValue("")
                titleState = TextFieldValue("")
            }) {
                Text("Clear")
            }
            Button(onClick = {
                if (isUserLoggedIn()) {
                    isUploading = true  // Set the state to loading
                    submitComplaint(context, titleState.text, textState.text) { success ->
                        if (success) {
                            titleState = TextFieldValue("")
                            textState = TextFieldValue("")
                        }
                        isUploading = false  // Reset the loading state
                    }
                } else {
                    Toast.makeText(context, "Please log in to submit a complaint", Toast.LENGTH_LONG).show()
                }
            }) {
                Text("Submit")
            }

            if (isUploading) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = "Uploading... Please wait",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Gray
                    )
                }
            }

        }
    }
}

fun isUserLoggedIn(): Boolean {
    val currentUser = FirebaseAuth.getInstance().currentUser
    return currentUser != null
}

@Composable
fun InputTitle(
    state: TextFieldValue,
    placeholder: String,
    onValueChange: (TextFieldValue) -> Unit
) {
    // TextField UI modifications
    TextField(
        value = state,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(4.dp)
            .background(Color.White, shape = MaterialTheme.shapes.medium)
    )
}
@Composable
fun InputField(
    state: TextFieldValue,
    placeholder: String,
    onValueChange: (TextFieldValue) -> Unit
) {
    // TextField UI modifications
    TextField(
        value = state,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(4.dp)
            .background(Color.White, shape = MaterialTheme.shapes.medium)
    )
}


fun submitComplaint(context: Context, title: String, complaintText: String, onCompletion: (Boolean) -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid

    if (userId == null) {
        Toast.makeText(context, "Please log in to submit a complaint", Toast.LENGTH_LONG).show()
        onCompletion(false)
        return
    }

    val database = Firebase.database
    val complaintsRef = database.getReference("complaints")
    val complaintId = complaintsRef.push().key
    val complaint = Complaint(userId, title, complaintText) // Date is set in the constructor

    complaintsRef.child(complaintId!!).setValue(complaint)
        .addOnSuccessListener {
            Toast.makeText(context, "Complaint submitted successfully", Toast.LENGTH_SHORT).show()
            onCompletion(true)
        }
        .addOnFailureListener {
            Toast.makeText(context, "Failed to submit complaint", Toast.LENGTH_SHORT).show()
            onCompletion(false)
        }
}

data class Complaint(val userId: String, val title: String, val text: String, val date: String) {
    // Primary constructor
    constructor(userId: String, title: String, text: String) : this(
        userId,
        title,
        text,
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    )
}


@Preview(showBackground = true)
@Composable
fun ComplaintsScreenPreview() {
    HallServiceAppTheme {
        ComplaintsScreen()
    }
}
