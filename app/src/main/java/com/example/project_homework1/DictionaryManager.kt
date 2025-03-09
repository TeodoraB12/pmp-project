package com.example.project_homework1
import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream

object DictionaryManager {


    fun copyDictionaryToStorage(context: Context) {
        val file = File(context.filesDir, "dictionary.txt")
        if (!file.exists()) {
            val inputStream = context.assets.open("dictionary.txt")
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()
        }
    }


    fun readDictionary(context: Context): MutableMap<String, String> {
        val file = File(context.filesDir, "dictionary.txt")
        val dictionary = mutableMapOf<String, String>()

        if (file.exists()) {
            file.forEachLine { line ->
                val parts = line.split(" - ")
                if (parts.size == 2) {
                    dictionary[parts[0].trim()] = parts[1].trim()
                    dictionary[parts[1].trim()] = parts[0].trim()
                }
            }
        }
        Log.d("Dictionary", "Dictionary content: ${dictionary.entries}")
        return dictionary
    }


    fun writeToDictionary(context: Context, macedonian: String, english: String) {
        val file = File(context.filesDir, "dictionary.txt")
        file.appendText("$macedonian - $english\n")
        Log.d("Dictionary", "Word written to file: $macedonian - $english")
    }

    fun getWordsFromFile(context: Context): List<String> {
        val file = File(context.filesDir, "dictionary.txt")

        return if (file.exists()) {
            file.readLines()
        } else {
            emptyList()
        }
    }
}


