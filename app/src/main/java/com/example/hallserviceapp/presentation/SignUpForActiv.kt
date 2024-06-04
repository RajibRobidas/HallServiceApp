package com.example.hallserviceapp.presentation

import android.content.Intent
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.hallserviceapp.R
import com.example.hallserviceapp.data.repository.BdAppsApiRepositoryImpl
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme
import com.example.hallserviceapp.viewmodel.ApiViewModel
import com.example.hallserviceapp.viewmodel.ApiViewModelFactory

class SignUpForActiv : ComponentActivity() {

    private lateinit var viewModel: ApiViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val repository = BdAppsApiRepositoryImpl()
        val factory = ApiViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ApiViewModel::class.java]

        setContent {
            HallServiceAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    FSignUpScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun FSignUpScreen(viewModel: ApiViewModel) {
    val requestOTPResponse = viewModel.requestOTPResponse.collectAsState().value
    val verifyOTPResponse = viewModel.verifyOTPResponse.collectAsState().value

    val context = LocalContext.current
    val imageResource = R.drawable.icon_account_circle
    var showDialog by remember { mutableStateOf(false) }

    var phoneNo by remember { mutableStateOf("") }
    var phoneNoError by remember { mutableStateOf<String?>(null) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val createUserState by remember { mutableStateOf<UserState?>(null) }


    var otp by remember { mutableStateOf("") }
    var otpError by remember { mutableStateOf<String?>(null) }


    phoneNoError = when (requestOTPResponse?.statusDetail) {
        "user already registered" -> "User is Already registered"
        else -> null
    }

    var requestOtpState by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.bgpic5),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = null,
                    modifier = Modifier.size(160.dp)
                )
                Spacer(modifier = Modifier.height(40.dp))


                if (requestOtpState) {
                    OutlinedTextField(
                        value = otp,
                        onValueChange = { otp = it },
                        label = { Text("OTP", color = Color.White) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(color = Color.White),
                        isError = otpError != null,
                        supportingText = { Text(text = otpError.orEmpty()) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            /*TODO: Verify the otp first and then create user */
                            // Call function to add user using Firebase Authentication
                            viewModel.verifyOTP(otp)

                            otpError =
                                if (verifyOTPResponse?.statusDetail != "Success") "Invalid OTP" else null
                            if (otpError == null) {
                                createUserWithEmailAndPassword(
                                    email,
                                    password
                                ) { isSuccess, errorMessage ->
                                    if (isSuccess) {
                                        Toast.makeText(
                                            context,
                                            "User added successfully!",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        showDialog = true

                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Failed to add user: $errorMessage",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                    ) {
                        Text(text = "Verify")
                    }

                } else {
                    OutlinedTextField(
                        value = phoneNo,
                        onValueChange = { phoneNo = it },
                        label = { Text("Phone No", color = Color.White) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(color = Color.White),
                        isError = phoneNoError != null,
                        supportingText = { Text(text = phoneNoError.orEmpty()) }
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email", color = Color.White) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        textStyle = TextStyle(color = Color.White)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password", color = Color.White) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        textStyle = TextStyle(color = Color.White)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            phoneNoError = viewModel.validatePhoneNo(phoneNo)
                            if (phoneNoError == null) {
                                viewModel.requestOTP(phoneNo)
                            }

                            if (requestOTPResponse?.statusDetail == "Success") {
                                requestOtpState = true
                            }
                        },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text("Sign Up")
                    }
                }


                // Display user creation state
                createUserState?.let { userState ->
                    when (userState) {
                        is UserState.Success -> {
                            Text("User added successfully!")
                        }

                        is UserState.Error -> {
                            Text("Failed to add user: ${userState.errorMessage}")
                        }
                    }
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text(text = "Sign Up Successful") },
                        text = { Text("You have successfully signed up. Click OK to proceed to the login page.") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showDialog = false
                                    context.startActivity(
                                        Intent(
                                            context,
                                            FrontAdminActivity::class.java
                                        )
                                    )
                                }
                            ) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FSignUpScreenPreview() {
    HallServiceAppTheme {
//        FSignUpScreen()
    }
}


