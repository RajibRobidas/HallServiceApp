package com.example.hallserviceapp

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme


class AdminActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                AdminScreen()
            }
        }
    }
}

@Composable
fun AdminScreen() {

    val lightBlue = Color(0xFF8FABE7) // Light blue color

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
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
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                HeaderSectionAd()
                Spacer(modifier = Modifier.height(30.dp))
                LazyColumn {
                    //item {  }

                    item { ChangeOptionT("Complains/Notice") }
                    item { Spacer(modifier = Modifier.height(30.dp)) }
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            ChangeOption("Read Complaints", R.drawable.complainttt,"ReadComplaintsActivity")
                        }
                    }
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .padding(horizontal = 10.dp)

                            ){

                                ChangeOption("Upload Notice", R.drawable.notice,"UploadNoticeActivity")
                                //Spacer(modifier = Modifier.height(30.dp))
                                ChangeOption("Delete Notice", R.drawable.delete_notice,"DeleteNoticeActivity")
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(30.dp)) }


                    item { ChangeOptionT("Food") }
                    item { Spacer(modifier = Modifier.height(30.dp)) }
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .padding(horizontal = 10.dp)

                            ){
                                ChangeOption("ADD Dining Food", R.drawable.dyningfood,"AddDiningActivity")
                                //Spacer(modifier = Modifier.height(30.dp))
                                ChangeOption("ADD Canteen Food", R.drawable.foodd,"AddCanteenActivity")
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .padding(horizontal = 10.dp)

                            ){
                                ChangeOption("Delete Dining Food", R.drawable.dyningfood,"DeleteDiningActivity")
                                //Spacer(modifier = Modifier.height(30.dp))
                                ChangeOption("Delete Canteen Food", R.drawable.foodd,"DeleteCanteenActivity")
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(30.dp)) }


                    item { ChangeOptionT("Student/Authority") }
                    item { Spacer(modifier = Modifier.height(30.dp)) }
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .padding(horizontal = 10.dp)

                            ) {
                                ChangeOption("Add Student", R.drawable.student_p,"StudentActivity")
                                //Spacer(modifier = Modifier.height(30.dp))
                                ChangeOption("Add Authority", R.drawable.authoriy_p,"UpdateAuthorityActivity")
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .padding(horizontal = 10.dp)

                            ) {
                                ChangeOption("Add Office", R.drawable.office_worker,"UpdateOfficeActivity")
                                //Spacer(modifier = Modifier.height(30.dp))
                                ChangeOption("Delete Information", R.drawable.icon_account_circle,"DeleteInformation")
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(30.dp)) }

                    item { ChangeOptionT("User") }
                    item { Spacer(modifier = Modifier.height(30.dp)) }
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .padding(horizontal = 10.dp)
                        ) {
                            ChangeOption("Add User", R.drawable.placeholder_a,"AddUserActivity")
                            //Spacer(modifier = Modifier.height(30.dp))
                            ChangeOption("Delete User", R.drawable.placeholder,"DeleteUser")
                        }
                    }
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            ChangeOption("Reset Password", R.drawable.resetpassword,"SignUpActivity")
                        }
                    }
                    item { Spacer(modifier = Modifier.height(30.dp)) }
                }
            }
        }

    }
}

@Composable
fun HeaderSectionAd() {
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
                text = "Admin Section",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .background(Color.White, shape = MaterialTheme.shapes.medium)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = "Headline",
            modifier = Modifier
                .clickable {context.startActivity(Intent(context,FrontAdminActivity::class.java))  // Change to the desired activity
                }
                .padding(vertical = 10.dp)
                //.padding(top = 4.dp)
                .size(50.dp)
                .padding(9.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Right
        ) {
            //Spacer(modifier = Modifier.width(35.dp)) // For spacing
            Text(
                text = "User",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable {
                        context.startActivity(
                            Intent(
                                context,
                                UserActivity::class.java
                            )
                        )  // Change to the desired activity
                    }
            )
        }
    }
}
@Composable
fun ChangeOptionT(text: String) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = text, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun ChangeOption(text: String, iconResId: Int, actName : String) {

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(140.dp)
            .height(140.dp)
            .padding(10.dp)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = "Sports",
            modifier = Modifier
                .clickable {
                    val intent = when (actName) {

                        "ReadComplaintsActivity" -> Intent(context, ReadComplaintsActivity::class.java)
                        "UploadNoticeActivity" -> Intent(context, UploadNoticeActivity::class.java)
                        "DeleteNoticeActivity" -> Intent(context, DeleteNoticeActivity::class.java)
                        "StudentActivity" -> Intent(context, StudentActivity::class.java)
                        "AddUserActivity" -> Intent(context, AddUserActivity::class.java)
                        "DeleteInformation" -> Intent(context, UpdateStudentActivity::class.java)
                        "UpdateOfficeActivity" -> Intent(context, UpdateOfficeActivity::class.java)
                        "UpdateAuthorityActivity" -> Intent(context, UpdateAuthorityActivity::class.java)
                        "SignUpActivity" -> Intent(context, SignUpActivity::class.java)
                        "DeleteUser" -> Intent(context, RemoveUserActivity::class.java)
                        "AddDiningActivity" -> Intent(context, AddDiningActivity::class.java)
                        "DeleteDiningActivity" -> Intent(context, DeleteDiningActivity::class.java)
                        "AddCanteenActivity" -> Intent(context, AddCanteenActivity::class.java)
                        "DeleteCanteenActivity" -> Intent(context, DeleteCanteenActivity::class.java)


                        else -> return@clickable
                    }
                    context.startActivity(intent)
                }
                .width(70.dp)
                .height(60.dp)
        )
        Text(
            text = text,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp),
            color = Color.White
        )
    }

}

@Preview(showBackground = true)
@Composable
fun AdminScreenPreview() {
    HallServiceAppTheme {
        AdminScreen()
    }
}

