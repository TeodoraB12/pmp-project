package com.example.project_homework1.ui.theme

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_homework1.DictionaryManager
import com.example.project_homework1.DictionaryManager.readDictionary


@Composable
fun DictionaryScreen(context: Context) {
    var dictionary by remember { mutableStateOf(readDictionary(context).toMutableMap()) }
    var searchText by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var newMacedonian by remember { mutableStateOf("") }
    var newEnglish by remember { mutableStateOf("") }


    LaunchedEffect("loadDictionary") {
        DictionaryManager.copyDictionaryToStorage(context)
        dictionary = DictionaryManager.readDictionary(context).toMutableMap()
    }
    Log.d("Dictionary", "Dictionary loaded: ${dictionary.keys}")

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Enter word to search") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            val searchQuery = searchText.trim().lowercase()

            Log.d("Dictionary", "Search for: $searchQuery")
            Log.d("Dictionary", "Dictionary entries: ${dictionary.keys}")

            val foundEntry = dictionary.entries.find { it.key.trim().lowercase() == searchQuery }

            if (foundEntry != null) {
                result = foundEntry.value
                Log.d("Dictionary", "Word found: ${foundEntry.key} -> ${foundEntry.value}")
            } else {
                result = "Word not found"
                Log.d("Dictionary", "Word not found in dictionary")
            }
        }) {
            Text("Search")
        }

        Text(
            text = result,
            modifier = Modifier.padding(top = 16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = newMacedonian,
            onValueChange = { newMacedonian = it },
            label = { Text("Enter Macedonian word") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = newEnglish,
            onValueChange = { newEnglish = it },
            label = { Text("Enter English translation") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )


        Button(onClick = {
            if (newMacedonian.isNotEmpty() && newEnglish.isNotEmpty()) {
                DictionaryManager.writeToDictionary(context, newMacedonian, newEnglish)


                val updatedDictionary = dictionary.toMutableMap()
                updatedDictionary[newMacedonian] = newEnglish
                updatedDictionary[newEnglish] = newMacedonian
                dictionary = updatedDictionary

                Log.d("Dictionary", "New word added: $newMacedonian - $newEnglish")
                Log.d("Dictionary", "Updated dictionary: ${dictionary.entries}")

                newMacedonian = ""
                newEnglish = ""
            }
        }, modifier = Modifier.padding(top = 8.dp)) {
            Text("Add to Dictionary")
        }
    }
    }