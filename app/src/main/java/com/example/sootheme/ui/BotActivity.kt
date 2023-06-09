package com.example.sootheme.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sootheme.MainActivity
import com.example.sootheme.R
import com.example.sootheme.databinding.ActivityBotBinding
import com.example.sootheme.databinding.ActivityLoginBinding

class BotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBotBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}