package com.example.hallserviceapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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


class NoticeTextActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NoticeTextScreen()
                }
            }
        }
    }
}

@Composable
fun NoticeTextScreen() {
    val context = LocalContext.current
    var titleState by remember { mutableStateOf(TextFieldValue()) }
    var textState by remember { mutableStateOf(TextFieldValue()) }
    var isUploading by remember { mutableStateOf(false) }
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

            HeaderSectionAl("TextNotice")
            Spacer(modifier = Modifier.height(20.dp))

            InputTitleNT(titleState, "Enter complaint title") { titleState = it }
            Spacer(modifier = Modifier.height(15.dp))
            InputFieldNT(textState, "Write your complaint here") { textState = it }
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

                Button(
                    onClick = {
                        if (titleState.text.isNotBlank() && textState.text.isNotBlank()) {
                            isUploading = true
                            submitNotice(context, titleState.text, textState.text) { success ->
                                if (success) {
                                    titleState = TextFieldValue("")
                                    textState = TextFieldValue("")
                                }
                                isUploading = false
                            }
                        } else {
                            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_LONG)
                                .show()
                        }
                    },
                    //modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit Notice")
                }
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
@Composable
fun InputTitleNT(
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
fun InputFieldNT(
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


@Composable
fun HeaderSectionAl(headlinee : String) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            //Spacer(modifier = Modifier.width(35.dp)) // For spacing
            Text(
                text = headlinee,
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
                    .padding(10.dp)
                    .clip(RoundedCornerShape(8.dp)),
                textAlign = TextAlign.Center
            )
        }
        Image(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = "arrow",
            modifier = Modifier
                .clickable {
                    context.startActivity(
                        Intent(
                            context,
                            UploadNoticeActivity::class.java
                        )
                    )  // Change to the desired activity
                }
                .padding(vertical = 10.dp)
                //.padding(top = 4.dp)
                .size(50.dp)
                .padding(9.dp)
        )
    }
}

data class Notice(val userId: String, val title: String, val text: String, val date: String) {
    constructor(userId: String, title: String, text: String) : this(
        userId,
        title,
        text,
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    )
}

fun submitNotice(context: Context, title: String, noticeText: String, onCompletion: (Boolean) -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid ?: return onCompletion(false).also {
        Toast.makeText(context, "Please log in to submit a notice", Toast.LENGTH_LONG).show()
    }

    val database = Firebase.database
    val noticesRef = database.getReference("notices")
    val noticeId = noticesRef.push().key ?: return
    val notice = Notice(userId, title, noticeText)

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

@Preview(showBackground = true)
@Composable
fun NoticeTextScreenPreview() {
    HallServiceAppTheme {
        NoticeTextScreen()    }
}
