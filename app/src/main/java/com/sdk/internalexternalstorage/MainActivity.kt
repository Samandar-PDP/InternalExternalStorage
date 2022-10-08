package com.sdk.internalexternalstorage

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.sdk.internalexternalstorage.databinding.ActivityMainBinding
import java.io.*

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        internalStorage()
        externalStorage()

    }

    private fun internalStorage() {
        binding.btnSaveInt.setOnClickListener {
            try {
                val fileOutputStream = openFileOutput("myIntFile.txt", Context.MODE_PRIVATE)
                val outputStreamWriter = OutputStreamWriter(fileOutputStream)
                outputStreamWriter.write(binding.editInternal.text.toString().trim())
                outputStreamWriter.close()
                binding.editInternal.text.clear()
                toast("Data saved")
            } catch (e: Exception) {
                e.printStackTrace()
                toast(e.message.toString())
            }
        }
        binding.btnReadInt.setOnClickListener {
            try {
                val fileInputStream = openFileInput("myIntFile.txt")
                val inputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder = StringBuilder()
                for (file in bufferedReader.readLines()) {
                    stringBuilder.append(file)
                }
                fileInputStream.close()
                inputStreamReader.close()
                binding.editInternal.setText(stringBuilder)
                toast("Data retrieved")
            } catch (e: Exception) {
                e.printStackTrace()
                toast(e.message.toString())
            }
        }
    }

    private fun externalStorage() {
        if (hasPermissionsGranted()) {
            binding.btnSaveExt.isVisible = true
            binding.btnReadExt.isVisible = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                0
            )
        }
        binding.btnSaveExt.setOnClickListener {
            if(isExternalStorageWritable()) {
                val directory = File(Environment.getExternalStorageDirectory().toString() + "/MyData")
                directory.mkdir()

                val file = File(Environment.getExternalStorageDirectory(), "myExtData.txt")
                val fileOutputStream = FileOutputStream(file)
                fileOutputStream.write(
                    binding.editExternal.text.toString().toByteArray()
                )
                fileOutputStream.close()
                toast("Data saved")
                binding.editExternal.text.clear()
            }
        }
        binding.btnReadExt.setOnClickListener {
            try {
                val directory =
                    File(Environment.getExternalStorageDirectory().toString() + "/MyData")
                directory.mkdir()
                val file = File(Environment.getExternalStorageDirectory(), "myExtData.txt")
                val fileInputStream = FileInputStream(file)
                val inputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder = StringBuilder()
                stringBuilder.append(bufferedReader.readLine())
                fileInputStream.close()
                inputStreamReader.close()
                binding.editExternal.setText(stringBuilder.toString())
                toast("Data retrieved")
            } catch (e: Exception) {
                e.printStackTrace()
                toast(e.message.toString())
            }
        }
    }

    private fun hasPermissionsGranted(): Boolean =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            binding.btnReadExt.isVisible = true
            binding.btnSaveExt.isVisible = true
        }
    }

    private fun isExternalStorageWritable(): Boolean =
        Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
}