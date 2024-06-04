package com.example.hallserviceapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hallserviceapp.R
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme
import com.google.firebase.auth.FirebaseAuth

class AddUserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddUserContent()
                }
            }
        }
    }
}

@Composable
fun AddUserContent() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var createUserState by remember { mutableStateOf<UserState?>(null) }
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
            modifier = Modifier.fillMaxSize()
                //.background(lightBlue)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current
            HeaderSectionAll("Add User")

            Spacer(modifier = Modifier.height(150.dp))

            Spacer(modifier = Modifier.height(16.dp))

            // TextFields for email and password input
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Call function to add user using Firebase Authentication
                    createUserWithEmailAndPassword(email, password) { isSuccess, errorMessage ->
                        if (isSuccess) {
                            Toast.makeText(context, "User added successfully!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(
                                context,
                                "Failed to add user: $errorMessage",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text("Add User")
            }

            // Display user creation state
            createUserState?.let { userState ->
                when (userState) {
                    is UserState.Success -> {
                        Text("User added successfully!")
                    }

                    is UserState.Error -> {
                        Text("Failed to add user: ${userState.errorMessage}")
                    }
                }
            }
        }
    }
}

fun createUserWithEmailAndPassword(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
    val auth = FirebaseAuth.getInstance()
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onComplete(true, null) // User added successfully
            } else {
                val errorMessage = task.exception?.message ?: "Unknown error"
                onComplete(false, errorMessage) // Error occurred while adding user
            }
        }
}

sealed class UserState {
    object Success : UserState()
    data class Error(val errorMessage: String) : UserState()
}


@Preview(showBackground = true)
@Composable
fun AddUserPreview() {
    HallServiceAppTheme {
        AddUserContent()
    }
}
