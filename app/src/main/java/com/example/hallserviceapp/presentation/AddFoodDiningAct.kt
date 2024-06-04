package com.example.hallserviceapp.presentation

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.hallserviceapp.R
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class AddDiningActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    AddDiningScreen()
                }
            }
        }
    }
}

@Composable
fun AddDiningScreen() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var addDiningName by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    val context = LocalContext.current
    val storageReference = FirebaseStorage.getInstance().reference
    val databaseReference = Firebase.database.reference
    val lightBlue = Color(0xFF8FABE7) // Light blue color 0xFF8FABE7
    var showDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }  // Loading state


    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bgpic4), // Replace with your image resource
            contentDescription = null, // Content description can be null for decorative images
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds // Scale the image to fill the bounds
        )
        // Add the background image

        Column(
            modifier = Modifier
                .fillMaxSize()
                //.background(lightBlue)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            HeaderSectionAll("Add Dining Food")

            Spacer(modifier = Modifier.size(40.dp))

            LazyColumn {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LoadImageAD { uri ->
                            imageUri = uri
                        }
                        Spacer(modifier = Modifier.width(40.dp))

                        ShowImageAD(imageUri)

                    }

                    // Show selected image

                    OutlinedTextField(
                        value = addDiningName,
                        onValueChange = { addDiningName = it },
                        label = { Text("Food Name",
                            color = Color.White // Set label text color to white
                        ) },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        textStyle = TextStyle(color = Color.White) // Set text color to white
                    )
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("Price",
                            color = Color.White // Set label text color to white
                        ) },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        textStyle = TextStyle(color = Color.White) // Set text color to white
                    )
                    OutlinedTextField(
                        value = time,
                        onValueChange = { time = it },
                        label = { Text("Time",
                            color = Color.White // Set label text color to white
                        ) },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        textStyle = TextStyle(color = Color.White) // Set text color to white
                    )

                    date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

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
                            // Validate authority information
                            if (addDiningName.isNotEmpty() && price.isNotEmpty() &&
                                time.isNotEmpty() && imageUri != null
                            ) {
                                showDialog = true
                                isLoading = true

                                addDiningToFirebase(
                                    context,
                                    imageUri,
                                    addDiningName,
                                    price,
                                    time,
                                    date,
                                    storageReference,
                                    databaseReference
                                )
                                //isLoading = false
                                //showDialog = false

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
                        Text("Upload Food Information")
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

@Composable
fun LoadImageAD(
    onImageSelected: (Uri) -> Unit
) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            onImageSelected(it)
        }
    }

    Box(
        modifier = Modifier
            .size(80.dp)
            .clickable {
                launcher.launch("image/*")
            }
            .background(Color(0xFFFBF9FC), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center

    ) {

        Image(
            imageVector = Icons.Default.Add,
            contentDescription = "Plus Icon",
            modifier = Modifier
                .size(60.dp)
        )
    }
}


@Composable
fun ShowImageAD(imageUri: Uri?) {
    if (imageUri != null) {
        // Show the selected image
        Image(
            painter = rememberImagePainter(imageUri),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .padding(vertical = 8.dp)
                .clip(shape = RoundedCornerShape(8.dp))
        )
    }
}

fun addDiningToFirebase(
    context: Context,
    imageUri: Uri?,
    addDiningName: String,
    price: String,
    time: String,
    date: String,
    storageReference: StorageReference,
    databaseReference: DatabaseReference
) {
    imageUri?.let { uri ->
        val imageRef = storageReference.child("images/${UUID.randomUUID()}")
        imageRef.putFile(uri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()

                    // Create Authority object
                    val diningFoods = DiningFood(
                        addDiningName,
                        price,
                        time,
                        date,
                        imageUrl
                    )

                    // Push Authority object to Firebase Database
                    databaseReference.child("DiningFoods").push().setValue(diningFoods)
                        .addOnSuccessListener {
                            Toast.makeText(
                                context,
                                "Food information uploaded successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                context,
                                "Failed to upload Food information",
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
data class DiningFood(
    val addDiningName: String = "",
    val price: String = "",
    val time: String = "",
    val date: String = "",
    val imageUrl: String = ""
)
@Preview(showBackground = true)
@Composable
fun AddDiningScreenPreview() {
    HallServiceAppTheme {
        AddDiningScreen()
    }
}
