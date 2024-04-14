package com.example.hallserviceapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoticeFileTextActivity : ComponentActivity() {
    private lateinit var selectPdfContract: ActivityResultLauncher<String>
    private var pdfUri by mutableStateOf<Uri?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectPdfContract = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                pdfUri = uri
            } else {
                Toast.makeText(this, "File selection was cancelled", Toast.LENGTH_SHORT).show()
            }
        }

        setContent {
            HallServiceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NoticeFileText(pdfUri, { selectPdfFile() }, { newUri -> pdfUri = newUri })
                }
            }
        }
    }

    private fun selectPdfFile() {
        selectPdfContract.launch("application/pdf")
    }
}

@Composable
fun NoticeFileText(
    pdfUri: Uri?,
    onSelectPdf: () -> Unit,
    onPdfUriChange: (Uri?) -> Unit
) {
    val lightBlue = Color(0xFF8FABE7) // Light blue color
    val context = LocalContext.current

    var titleState by remember { mutableStateOf(TextFieldValue()) }
    var isUploading by remember { mutableStateOf(false) }
    val gray = Color(0xFFE7E3E7)

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
            HeaderSectionNFT()

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Gray)
                    .border(2.dp, Color.Black)
                    .clickable(onClick = onSelectPdf)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Select File",
                    tint = Color.White,
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Select PDF File",
                color = Color.Black,
                fontSize = 25.sp,
                modifier = Modifier
                    .background(gray, shape = RoundedCornerShape(18.dp))
                    .padding(10.dp)
                    .clip(RoundedCornerShape(8.dp)),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextField(
                value = titleState,
                onValueChange = { titleState = it },
                label = { Text("Enter Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (titleState.text.isNotBlank() && pdfUri != null) {
                        isUploading = true
                        uploadPdfFile(context, titleState.text, pdfUri!!) { success ->
                            if (success) {
                                titleState = TextFieldValue("")
                                onPdfUriChange(null) // Reset pdfUri after upload
                            }
                            isUploading = false
                        }
                    } else {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit Notice")
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

fun uploadPdfFile(context: Context, title: String, pdfUri: Uri, onCompletion: (Boolean) -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid

    val database = Firebase.database
    val noticesRef = database.getReference("notices")
    val noticeId = noticesRef.push().key ?: return

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val currentDate = dateFormat.format(Date())

    val notice = NoticeF(userId.orEmpty(), title, pdfUri.toString(), currentDate)

    noticesRef.child(noticeId).setValue(notice)
        .addOnSuccessListener {
            Toast.makeText(context, "Notice submitted successfully", Toast.LENGTH_SHORT).show()
            onCompletion(true)
        }
        .addOnFailureListener {
            Toast.makeText(context, "Failed to submit notice", Toast.LENGTH_SHORT).show()
            onCompletion(false)
        }
}
data class NoticeF(
    val userId: String,
    val title: String,
    val pdfUri: String,
    val date: String
)

@Composable
fun HeaderSectionNFT() {
    val gray = Color(0xFFBA6FBD)
    val yellow = Color(0xFF40E48A)
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = "headline",
            modifier = Modifier
                .clickable {
                    context.startActivity(
                        Intent(
                            context,
                            UploadNoticeActivity::class.java
                        )
                    )
                }
                .padding(end = 10.dp)
                .size(width = 100.dp, height = 40.dp)
        )

        Text(
            text = "FileNotice",
            color = Color.Black,
            fontSize = 25.sp,
            modifier = Modifier
                .background(yellow, shape = RoundedCornerShape(10.dp))
                .padding(10.dp)
                .clip(RoundedCornerShape(8.dp)),
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true)
@Composable
fun NoticeFileTextPreview() {
    HallServiceAppTheme {
        // Provide dummy Uri? and functions for the preview
        NoticeFileText(
            pdfUri = null, // Dummy Uri, you can put null for the preview
            onSelectPdf = {}, // Dummy function for selecting PDF
            onPdfUriChange = {} // Dummy function for handling Uri change
        )
    }
}

