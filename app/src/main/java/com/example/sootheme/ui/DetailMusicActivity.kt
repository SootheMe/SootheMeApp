package com.example.sootheme.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.sootheme.databinding.ActivityDetailMusicBinding
import com.example.sootheme.model.DetailMusicViewModel
import com.example.sootheme.network.ViewModelFactory

class DetailMusicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMusicBinding
    private val viewModel: DetailMusicViewModel by viewModels {
        ViewModelFactory.getInstance(context = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(DetailMusicActivity.EXTRA_ID, 0)

        viewModel.detailMusic(id)
        //loadingState(true)
        if (viewModel.error.value == true) {
            //loadingState(false)
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.getDetailStory().observe(this) { story ->
                if (story != null) {
                    //loadingState(false)
                    binding.apply {
                        binding.tvTitleMusic.text = story.title
                        Glide.with(this@DetailMusicActivity)
                            .load(story.cover)
                            .into(imgCover)
                    }
                } else {
                    Log.e("DetailStoryActivity", "Data tidak ditemukan")
                    //loadingState(false)
                }
            }
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_COVER = "extra_cover"
        const val EXTRA_CONTENT = "extra_content"
    }
}