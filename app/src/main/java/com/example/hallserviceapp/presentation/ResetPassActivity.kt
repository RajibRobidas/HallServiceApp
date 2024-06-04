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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hallserviceapp.R
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                SignUpScreen()
            }
        }
    }
}

@Composable
fun SignUpScreen() {
    // State variables to hold user input
    var email by remember { mutableStateOf("") }

    // Replace with your drawable resource
    val imageResource = R.drawable.icon_account_circle
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
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
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    //.background(lightBlue)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val context = LocalContext.current
                Spacer(modifier = Modifier.height(15.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = imageResource),
                        contentDescription = "User Icon",
                        modifier = Modifier.size(150.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Want to reset Password?",
                        color = Color.White,
                        fontSize = 25.sp,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth()
                            .height(50.dp),
                        //.background(color = Color(138, 103, 168, 255)),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
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
                    Button(
                        onClick = {
                            if (email == "") {
                                Toast.makeText(context, "Please Enter Email", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                val auth = FirebaseAuth.getInstance()
                                auth.sendPasswordResetEmail(email)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(
                                                context,
                                                "Password reset email sent",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Failed to send reset email",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                            }
                            // Handle password reset
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Reset Password")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    HallServiceAppTheme {
        SignUpScreen()
    }
}
