package com.example.project_homework1
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchScreen()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var tagQuery by remember { mutableStateOf(TextFieldValue("")) }
    var tags by remember { mutableStateOf(mutableListOf("AndroidFP", "Deitel", "Google", "iPhoneFP", "JavaFP", "JavaHTP")) }
    var currentTagIndex by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE0B2))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Favorite Twitter Searches", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Enter Twitter search query here") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))


        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = tagQuery,
                onValueChange = { tagQuery = it },
                label = { Text("Tag your query") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(colors = ButtonDefaults.buttonColors(containerColor = Gray),onClick = {
                if (tagQuery.text.isNotEmpty()) {
                    if (currentTagIndex == -1) {
                        tags = tags.toMutableList().apply { add(tagQuery.text) }
                    } else {
                        tags = tags.toMutableList().apply { this[currentTagIndex] = tagQuery.text }
                    }
                    tagQuery = TextFieldValue("")
                    currentTagIndex = -1
                }
            }){
                Text(if (currentTagIndex == -1) "Save" else "Update")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Tagged Searches", fontSize = 18.sp, color = DarkGray)
        Spacer(modifier = Modifier.height(8.dp))


        LazyColumn(modifier = Modifier.fillMaxHeight(0.7f)) {
            items(tags.size) { index ->
                Item(
                    tag = tags[index],
                    onEdit = {
                        currentTagIndex = index
                        tagQuery = TextFieldValue(tags[index])
                        tags = tags.toMutableList().apply { this[currentTagIndex] = tagQuery.text }
                    },
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = {tags = emptyList<String>().toMutableList()}, modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Gray)) {
            Text("Clear Tags")
        }
    }
}
@Composable
fun Item(tag: String, onEdit: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Button(
            onClick = {},
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = Gray),
        ) {
            Text(tag)
        }
        Spacer(modifier = Modifier.width(8.dp))


        Button(
            onClick = onEdit,
            colors = ButtonDefaults.buttonColors(containerColor = Gray)
        ) {
            Text("Edit")
        }
    }
}