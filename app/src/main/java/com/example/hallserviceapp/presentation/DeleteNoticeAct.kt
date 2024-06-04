package com.example.hallserviceapp.presentation

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.example.hallserviceapp.R
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DeleteNoticeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                DeleteNoticeContent()
            }
        }
    }
}

@Composable
fun DeleteNoticeContent() {
    val lightBlue = Color(0xFF8FABE7) // Light blue color
    val database = FirebaseDatabase.getInstance().getReference("notices")
    var noticeList by remember { mutableStateOf(listOf<NoticeDN>()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        database.orderByChild("date").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                isLoading = false
                noticeList = snapshot.children.mapNotNull { it.getValue(NoticeDN::class.java)?.copy(id = it.key ?: "") }
            }

            override fun onCancelled(error: DatabaseError) {
                isLoading = false
                isError = true
            }
        })
    }
    val notices = noticeList

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
                //.background(lightBlue, shape = RoundedCornerShape(10.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            HeaderSectionAll("Delete Notice")
            Spacer(modifier = Modifier.height(16.dp))
            if (isLoading) {
                CircularProgressIndicator()
            } else if (isError) {
                Text("Error loading complaints.")
            } else {
                if (notices.isNotEmpty()) {
                    LazyColumn {
                        items(notices) { notice ->
                            DeleteNoticeItem(notice) { noticestId ->
                                database.child(noticestId).removeValue()
                            }
                        }
                    }
                } else {
                    Text(text = "No notices found", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

@Composable
fun DeleteNoticeItem(notice: NoticeDN, onDelete: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Date: ${notice.date}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Title: ${notice.title}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Text: ${notice.text}", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "File: ${notice.pdfUri}", style = MaterialTheme.typography.bodyMedium)
            if (notice.pdfUri.isNotEmpty()) {
                val context = LocalContext.current
                Button(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(notice.pdfUri)
                    intent.type = "application/pdf" // Specify the MIME type
                    val chooser = Intent.createChooser(intent, "Open PDF")

                    // Verify that the intent will resolve to an activity
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(chooser)
                    } else {
                        // Handle the case where no activity can handle the intent
                        Toast.makeText(context, "No application found to open PDF", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text(text = "View PDF")
                }

            }
            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = { onDelete(notice.id) }
            ) {
                Text(text = "Delete")
            }
        }
    }
}

data class NoticeDN(
    val id: String = "",
    val userid: String = "",
    val title: String = "",
    val date: String = "",
    val text: String = "",
    val pdfUri: String = ""
)

@Preview(showBackground = true)
@Composable
fun DeleteNoticePreview() {
    HallServiceAppTheme {
        DeleteNoticeContent()
    }
}
