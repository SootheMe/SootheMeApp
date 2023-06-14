package com.example.sootheme.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sootheme.R
import com.example.sootheme.adapter.StoryAdapter
import com.example.sootheme.data.StoryData
import com.example.sootheme.databinding.ActivityStoryBinding
import com.example.sootheme.model.StoryViewModel
import com.example.sootheme.network.ViewModelFactory

@Suppress("DEPRECATION")
class StoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryBinding
    private lateinit var adapter: StoryAdapter
    private val viewModel: StoryViewModel by viewModels {
        ViewModelFactory.getInstance(context = this)
    }
    private val recyclerView: RecyclerView
        get() = binding.rvStory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = StoryAdapter()
        adapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: StoryData) {
                Intent(this@StoryActivity, DetailStoryActivity::class.java).also {
                    it.putExtra(DetailStoryActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailStoryActivity.EXTRA_TITLE, data.title)
                    it.putExtra(DetailStoryActivity.EXTRA_AUTHOR, data.author)
                    it.putExtra(DetailStoryActivity.EXTRA_COVER, data.cover)
                    it.putExtra(DetailStoryActivity.EXTRA_DESCRIPTION, data.description)
                    it.putExtra(DetailStoryActivity.EXTRA_CONTENT, data.content)
                    startActivity(it)
                }
            }
        })

        val backButton: ImageView = findViewById(R.id.bck_button)

        backButton.setOnClickListener {
            onBackPressed()
        }

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this@StoryActivity)
            recyclerView.adapter = adapter

            viewModel.getStoryTime()
            viewModel.user.observe(this@StoryActivity) { story ->
                if (story != null) {
                    adapter.setUser(story)
                }
            }
        }
    }
}