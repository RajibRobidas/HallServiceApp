package com.example.hallserviceapp.presentation

import android.app.ProgressDialog
import android.content.Context
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hallserviceapp.R
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class RemoveUserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DeleteUserContent() // Pass the context to DeleteUserContent
                }
            }
        }
    }
}

@Composable
fun DeleteUserContent() { // Receive context as a parameter
    val lightBlue = Color(0xFF8FABE7) // Light blue color
    val emailState = remember { mutableStateOf(TextFieldValue()) }
    var showingProgressDialog by remember { mutableStateOf(false) }
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
            modifier = Modifier.fillMaxSize()
                //.background(lightBlue)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderSectionAll("Remove User")

            Spacer(modifier = Modifier.height(170.dp))

            // Input field to enter user's email
            TextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                label = { Text("Enter user's email") },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            val context = LocalContext.current

            // Button to remove user
            Button(
                onClick = {
                    val email = emailState.value.text
                    showingProgressDialog = false
                    removeUserByEmail(context, email) { // Pass context to removeUserByEmail
                        showingProgressDialog = false
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Text("Remove User")
            }
        }
    }

    if (showingProgressDialog) {
        ProgressDialog(context).apply {
            setMessage("Removing user...")
            setCancelable(false)
        }.show()
    }
}

private fun removeUserByEmail(context: Context, email: String, onComplete: () -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    // Check if the user is authenticated
    if (user != null) {
        // Check if the provided email matches the current user's email
        if (user.email == email) {
            // User is attempting to remove themselves. Provide an error message or handle accordingly.
            // You can't remove yourself programmatically due to security reasons.
            // This is just an example, handle the scenario appropriately based on your application logic.
            Toast.makeText(context, "You cannot remove yourself", Toast.LENGTH_SHORT).show()
        } else {
            // User is an admin or authorized to remove users
            auth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result?.signInMethods ?: emptyList<String>()
                    if (result.isNotEmpty()) {
                        // The user exists, proceed to delete
                        auth.signInWithEmailAndPassword(email, "aDummyPassword") // Use any dummy password
                            .addOnCompleteListener { signInTask ->
                                if (signInTask.isSuccessful) {
                                    // Successfully signed in, delete the user
                                    val currentUser = auth.currentUser
                                    currentUser?.delete()?.addOnCompleteListener { deleteTask ->
                                        if (deleteTask.isSuccessful) {
                                            // User deleted successfully
                                            onComplete.invoke()
                                            Toast.makeText(context, "User removed successfully", Toast.LENGTH_SHORT).show()
                                        } else {
                                            // Failed to delete user
                                            Toast.makeText(context, "Failed to remove user", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                } else {
                                    // Failed to sign in with email and dummy password
                                    Toast.makeText(context, "Failed to sign in", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                    else {
                        // User does not exist with the provided email
                        if (task.exception is FirebaseAuthInvalidUserException) {
                            // User does not exist with the provided email
                            Toast.makeText(context, "User does not exist", Toast.LENGTH_SHORT).show()
                        } else {
                            // Other errors
                            Toast.makeText(context, "Failed to fetch user information", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else {
                    // Task to fetch sign-in methods failed
                    Toast.makeText(context, "Failed to fetch sign-in methods", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    else {
        // User is not authenticated
        Toast.makeText(context, "User is not authenticated", Toast.LENGTH_SHORT).show()
    }
}


@Preview(showBackground = true)
@Composable
fun DeleteUserPreview() {
    HallServiceAppTheme {
        DeleteUserContent()
    }
}
