package com.example.hallserviceapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class NoticeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NoticeScreen()
                }
            }

        }
    }
}

@Composable
fun NoticeScreen() {

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
            Headlineee("Notice")
            //SearchSection()
            NoticeSection()
        }
    }

}

data class NoticeNN(
    val id: String = "",
    val title: String = "",
    val date: String = "",
    val text: String = "",
    val pdfUri: String = "" // Assuming notices have a PDF URL
)

@Composable
fun NoticeSection() {
    val database = FirebaseDatabase.getInstance().getReference("notices")
    var noticeList by remember { mutableStateOf(listOf<NoticeNN>()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        database.orderByChild("date").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                isLoading = false
                noticeList = snapshot.children.mapNotNull { it.getValue(NoticeNN::class.java)?.copy(id = it.key ?: "") }
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
        Text("Error loading notices.")
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(noticeList) { notice ->
                NoticeItem(notice)
            }
        }
    }
}

@Composable
fun NoticeItem(notice: NoticeNN) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Date: ${notice.date}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Title: ${notice.title}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Description: ${notice.text}", style = MaterialTheme.typography.bodyMedium)
            // Add more details or actions for each notice item here
            //Text(text = "File: ${notice.pdfUri}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            if (notice.pdfUri.isNotEmpty()) { // Check if PDF URI is not empty
                val context = LocalContext.current
                Button(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(notice.pdfUri)
                    context.startActivity(intent)
                }) {
                    Text(text = "View PDF")
                }

                Spacer(modifier = Modifier.height(4.dp))

                Button(onClick = {
                    // Define a file path to save the PDF
                    val destinationPath = context.getExternalFilesDir(null)?.absolutePath + "/DownloadedFile.pdf"
                    // Download the file
                    downloadFile(notice.pdfUri, destinationPath)
                }) {
                    Text(text = "Download PDF")
                }
            } else {
                //Text(text = "PDF URL is empty", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

fun downloadFile(fileUrl: String, destinationFilePath: String) {
    try {
        val url = URL(fileUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.connect()

        val inputStream: InputStream = connection.inputStream
        val fileOutputStream = FileOutputStream(File(destinationFilePath))

        val buffer = ByteArray(1024)
        var len1: Int
        while (inputStream.read(buffer).also { len1 = it } != -1) {
            fileOutputStream.write(buffer, 0, len1)
        }

        fileOutputStream.close()
        inputStream.close()
    } catch (e: Exception) {
        e.printStackTrace() // Log the exception for debugging purposes
    }
}

@Preview(showBackground = true)
@Composable
fun NoticePreview() {
    HallServiceAppTheme {
        NoticeScreen()
    }
}