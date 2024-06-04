package com.example.hallserviceapp.presentation

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID
class UpdateOfficeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    UpdateOfficeScreen()
                }
            }
        }
    }
}

@Composable
fun UpdateOfficeScreen() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var officeName by remember { mutableStateOf("") }
    var designation by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val context = LocalContext.current
    val storageReference = FirebaseStorage.getInstance().reference
    val databaseReference = Firebase.database.reference
    val lightBlue = Color(0xFF8FABE7) // Light blue color
    var isLoading by remember { mutableStateOf(false) }  // Loading state
    var showDialog by remember { mutableStateOf(false) }

    Surface(
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            HeaderSectionAll("Add Office")

            Spacer(modifier = Modifier.size(16.dp))

            LazyColumn {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LoadImage { uri ->
                            imageUri = uri
                        }
                        Spacer(modifier = Modifier.width(40.dp))

                        ShowImage(imageUri)

                    }

                    OutlinedTextField(
                        value = officeName,
                        onValueChange = { officeName = it },
                        label = { Text("Name",
                            color = Color.White // Set label text color to white
                        ) },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        textStyle = TextStyle(color = Color.White) // Set text color to white
                    )
                    OutlinedTextField(
                        value = designation,
                        onValueChange = { designation = it },
                        label = { Text("Designation",
                            color = Color.White // Set label text color to white
                        ) },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        textStyle = TextStyle(color = Color.White) // Set text color to white
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email",
                            color = Color.White // Set label text color to white
                        ) },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        textStyle = TextStyle(color = Color.White) // Set text color to white
                    )
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text("Phone Number",
                            color = Color.White // Set label text color to white
                        ) },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        textStyle = TextStyle(color = Color.White) // Set text color to white
                    )

                    Spacer(modifier = Modifier.size(16.dp))
                    if (isLoading) {
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

                    Button(
                        onClick = {
                            if (officeName.isNotEmpty() && designation.isNotEmpty() &&
                                phoneNumber.isNotEmpty() && email.isNotEmpty() && imageUri != null
                            ) {
                                showDialog = true
                                isLoading = true
                                uploadOfficeToFirebase(
                                    context,
                                    imageUri,
                                    officeName,
                                    designation,
                                    phoneNumber,
                                    email,
                                    storageReference,
                                    databaseReference
                                )
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please fill all fields and select an image",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()
                    ) {
                        Text("Upload Office Information")
                    }
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                showDialog = false
                                isLoading = false

                            },
                            title = {
                                Text("Uploading")
                            },
                            text = {
                                Text("Uploading... Please wait")
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        showDialog = false
                                        isLoading = false
                                    }
                                ) {
                                    Text("Dismiss")
                                }
                            }
                        )
                    }

                }
            }
        }
    }
}

fun uploadOfficeToFirebase(
    context: Context,
    imageUri: Uri?,
    officeName: String,
    designation: String,
    phoneNumber: String,
    email: String,
    storageReference: StorageReference,
    databaseReference: DatabaseReference
) {
    imageUri?.let { uri ->
        val imageRef = storageReference.child("images/${UUID.randomUUID()}")
        imageRef.putFile(uri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()

                    val office = Offices(
                        officeName,
                        designation,
                        email,
                        phoneNumber,
                        imageUrl
                    )

                    databaseReference.child("offices").push().setValue(office)
                        .addOnSuccessListener {
                            Toast.makeText(
                                context,
                                "Office information uploaded successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                context,
                                "Failed to upload office information",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Failed to upload image",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}

data class Offices(
    val name: String,
    val designation: String,
    val email: String,
    val phoneNumber: String,
    val imageUrl: String // URL of the uploaded image
)

@Preview(showBackground = true)
@Composable
fun UpdateOfficePreview() {
    HallServiceAppTheme {
        UpdateOfficeScreen()
    }
}
