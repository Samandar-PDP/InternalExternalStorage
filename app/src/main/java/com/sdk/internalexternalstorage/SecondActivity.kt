package com.sdk.internalexternalstorage

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.sdk.internalexternalstorage.databinding.ActivitySecondBinding
import java.io.File

class SecondActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySecondBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnImage.setOnClickListener {
            takeImage()
        }
        binding.btnVideo.setOnClickListener {
            takeVideo()
        }
    }

    private fun takeImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val imageUri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val authorities = "${applicationContext.packageName}.fileprovider"
            FileProvider.getUriForFile(this, authorities, getImageFilePath())
        } else {
            Uri.fromFile(getImageFilePath())
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, 0)
    }

    private fun getImageFilePath(): File {
        val folder = File("sdcard/My_Media")
        if (!folder.exists()) {
            folder.mkdir()
        }
        return File(folder, "my_image${System.currentTimeMillis()}.jpg")
    }

    private fun takeVideo() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        val imageUri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val authorities = "${applicationContext.packageName}.fileprovider"
            FileProvider.getUriForFile(this, authorities, getVideoFilePath())
        } else {
            Uri.fromFile(getVideoFilePath())
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, 0)
    }

    private fun getVideoFilePath(): File {
        val folder = File("sdcard/My_Media")
        if (!folder.exists()) {
            folder.mkdir()
        }
        return File(folder, "my_video${System.currentTimeMillis()}.mp4")
    }
}