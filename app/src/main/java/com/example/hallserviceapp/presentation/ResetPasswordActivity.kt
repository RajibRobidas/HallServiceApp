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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hallserviceapp.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ResetPasswordScreen()
        }
    }
}

@Composable
fun ResetPasswordScreen() {
    val auth = FirebaseAuth.getInstance()
    var email by remember { mutableStateOf("") }
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        val lightBlue = Color(0xFF8FABE7) // Light blue color
        val context = LocalContext.current

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Add the background image
            Image(
                painter = painterResource(id = R.drawable.bgpic5), // Replace with your image resource
                contentDescription = null, // Content description can be null for decorative images
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds // Scale the image to fill the bounds
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    //.background(lightBlue)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Spacer(modifier = Modifier.height(40.dp))

                Image(
                    painter = painterResource(id = R.drawable.icon_account_circle),
                    contentDescription = null,
                    modifier = Modifier.size(130.dp)
                )
                Spacer(modifier = Modifier.height(40.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Enter your email",
                        color = Color.White // Set label text color to white
                    ) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.White) // Set text color to white

                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = oldPassword,
                    onValueChange = { oldPassword = it },
                    label = { Text("Enter old password",
                        color = Color.White // Set label text color to white
                    ) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.White) // Set text color to white

                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("Enter new password",
                        color = Color.White // Set label text color to white
                    ) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.White) // Set text color to white

                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Re-type new password",
                        color = Color.White // Set label text color to white
                    ) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.White) // Set text color to white

                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if(email == "" || oldPassword == "" || newPassword == "" || confirmPassword == ""){
                            Toast.makeText(context, "Please fill up all ", Toast.LENGTH_SHORT).show()

                        }else{
                            val user = auth.currentUser
                            if (user != null && user.email != null) {
                                val credential = EmailAuthProvider.getCredential(user.email!!, oldPassword)
                                user.reauthenticate(credential)
                                    .addOnCompleteListener { reauthTask ->
                                        if (reauthTask.isSuccessful) {
                                            if (newPassword == confirmPassword) {
                                                user.updatePassword(newPassword)
                                                    .addOnCompleteListener { updateTask ->
                                                        if (updateTask.isSuccessful) {
                                                            Toast.makeText(
                                                                context,
                                                                "Password updated successfully",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        } else {
                                                            Toast.makeText(
                                                                context,
                                                                "Failed to update password",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                    }
                                            } else {
                                                Toast.makeText(context, "New passwords do not match", Toast.LENGTH_SHORT).show()
                                            }
                                        } else {
                                            Toast.makeText(context, "Re-Authentication failed", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            } else {
                                Toast.makeText(context, "User not found or email not set", Toast.LENGTH_SHORT).show()
                            }
                        }

                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Update Password")
                }

                Spacer(modifier = Modifier.height(20.dp))

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ResetPasswordScreenPreview() {
    ResetPasswordScreen()
}
