package com.example.hallserviceapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.hallserviceapp.R
import com.example.hallserviceapp.ui.theme.HallServiceAppTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ReadDiningActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HallServiceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ReadDiningScreen()
                }
            }
        }
    }
}

@Composable
fun ReadDiningScreen() {
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
            Headlineee("Food For Today")
            ReadDiningSection()
        }
    }
}

@Composable
fun ReadDiningSection() {
    val database = FirebaseDatabase.getInstance().getReference("DiningFoods")
    var readDiningList by remember { mutableStateOf(listOf<DiningFoods>()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                isLoading = false
                readDiningList = snapshot.children.mapNotNull { it.getValue(DiningFoods::class.java) }
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
        Text("Error loading Food information.")
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(readDiningList) { diningFood ->
                DiningFoodItem(diningFood)
            }
        }
    }
}
@Composable
fun DiningFoodItem(diningFood: DiningFoods) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            val lightGold = Color(0xFFC4BFB0)
            val lightGoldB = Color(0xFFEBE6C1)

            val painter: Painter = rememberImagePainter(diningFood.imageUrl)
            Column(modifier = Modifier.padding(16.dp)
                    .background(lightGold, shape = RoundedCornerShape(10.dp))
                .padding(12.dp),
            ) {

                Text(text = "Date:  ${diningFood.date}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .background(lightGoldB, shape = RoundedCornerShape(10.dp))
                        .padding(6.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "Time:  ${diningFood.time}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .background(lightGoldB, shape = RoundedCornerShape(10.dp))
                        .padding(6.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "FoodName:  ${diningFood.addDiningName}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .background(lightGoldB, shape = RoundedCornerShape(10.dp))
                        .padding(6.dp)

                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "price:  ${diningFood.price}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .background(lightGoldB, shape = RoundedCornerShape(10.dp))
                        .padding(6.dp)

                )
                Spacer(modifier = Modifier.height(4.dp))

            }
            //Spacer(modifier = Modifier.width(14.dp))

            Image(
                painter = painter,
                contentDescription = "Food Image",
                modifier = Modifier
                    .size(140.dp)
                    .padding(20.dp)
            )

        }
    }
}

data class DiningFoods(
    val id: String = "",
    val addDiningName: String = "",
    val price: String = "",
    val date: String = "",
    val time: String = "",
    val imageUrl: String = ""
)
@Preview(showBackground = true)
@Composable
fun ReadDiningScreenPreview() {
    HallServiceAppTheme {
        ReadDiningScreen()
    }
}
