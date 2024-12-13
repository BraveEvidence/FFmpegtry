package com.codingwithnobody.myandroidapp

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var filePath: File? = null

    private val newVideoPicker =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            lifecycleScope.launch {
                val originalPath = uri?.let { getFileFromUri(it) }
                originalPath?.let { convertVideoToGrayscale(it, "file://$filePath") }
            }

        }

    companion object {
        init {
            System.loadLibrary("myandroidapp") // Replace "native-lib" with your C++ library name
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            filePath = createExternalFile()
        }

        findViewById<Button>(R.id.btn).setOnClickListener {
            newVideoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
        }
    }

    private suspend fun createExternalFile(): File? {
        return try {
            withContext(Dispatchers.IO) {
                val fileName = "${System.currentTimeMillis()}MediaFileTrans.mp4"
                val file = File(externalCacheDir, fileName)
                check(!(file.exists() && !file.delete())) { "Could not delete the previous transformer output file" }
                check(file.createNewFile()) { "Could not create the transformer output file" }
                file
            }
        } catch (e: IOException) {
            Toast.makeText(this, "Could not create the transformer output file: ${e.message}", Toast.LENGTH_SHORT).show()
            null
        }
    }

    suspend fun getFileFromUri(uri: Uri): String? = withContext(Dispatchers.IO) {
        val contentResolver = contentResolver

        try {
            // Check if the URI is a file scheme
            if ("file" == uri.scheme) {
                return@withContext uri.path
            }

            // Handle content:// URIs
            contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                val fileName = cursor.getString(nameIndex)

                val tempFile = File(cacheDir, fileName)

                contentResolver.openInputStream(uri)?.use { inputStream ->
                    FileOutputStream(tempFile).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                return@withContext tempFile.absolutePath
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext null
    }







    private external fun convertVideoToGrayscale(inputPath: String, outputPath: String)

}