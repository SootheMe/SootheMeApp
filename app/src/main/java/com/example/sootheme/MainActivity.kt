package com.example.sootheme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.example.sootheme.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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