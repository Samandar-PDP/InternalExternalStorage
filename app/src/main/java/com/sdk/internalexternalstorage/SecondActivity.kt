package com.sdk.internalexternalstorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sdk.internalexternalstorage.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySecondBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)




    }
}