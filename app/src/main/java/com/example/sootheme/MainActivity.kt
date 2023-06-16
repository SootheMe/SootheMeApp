package com.example.sootheme

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.sootheme.databinding.ActivityMainBinding
import com.example.sootheme.model.MainViewModel
import com.example.sootheme.network.ViewModelFactory
import com.example.sootheme.ui.BotActivity
import com.example.sootheme.ui.MusicActivity
import com.example.sootheme.ui.StoryActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Calendar

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(context = this)
    }
    private lateinit var clockView: TextView
    private lateinit var dateView: TextView
    private val handler = Handler()
    private val updateTimeRunnable = object : Runnable {
        override fun run() {
            updateTime()
            handler.postDelayed(this, 1000) // Update time every 1 second
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clockView = findViewById(R.id.clock_view)
        dateView = findViewById(R.id.date_view)

        viewModel.getUserToken().observe(this) { token ->
            if (token != null) {
                Log.e("MainActivity", "token: $token")
                viewModel.getUser(token)
            }
        }

        viewModel.user.observe(this) { user ->
            if (user != null) {
                viewModel.saveUserName(user)
            }
        }

        viewModel.getUserName().observe(this) { userName ->
            if (userName != null) {
                binding.helloUser.text = "Hello, $userName!"
            }
        }

        binding.tvBot.setOnClickListener {
            val intent = Intent(this, BotActivity::class.java)
            startActivity(intent)
        }

        binding.tvStory.setOnClickListener {
            val intent = Intent(this, StoryActivity::class.java)
            startActivity(intent)
        }

        binding.tvMusic.setOnClickListener {
            val intent = Intent(this, MusicActivity::class.java)
            startActivity(intent)
        }

        binding.tvConsultation.setOnClickListener {
            viewModel.userLogoutApp()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        startUpdatingTime()
    }

    override fun onPause() {
        super.onPause()
        stopUpdatingTime()
    }

    private fun startUpdatingTime() {
        updateTime()
        handler.postDelayed(updateTimeRunnable, 1000) // Start updating time
    }

    private fun stopUpdatingTime() {
        handler.removeCallbacks(updateTimeRunnable) // Stop updating time
    }

    private fun updateTime() {
        val calendar = Calendar.getInstance()

        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
        val dateString = dateFormat.format(calendar.time)
        dateView.text = dateString

        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = sdf.format(Date())
        clockView.text = currentTime
    }
}