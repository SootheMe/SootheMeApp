package com.example.sootheme.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.sootheme.databinding.ActivityDetailStoryBinding
import com.example.sootheme.model.DetailStoryViewModel
import com.example.sootheme.network.ViewModelFactory

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    private val viewModel: DetailStoryViewModel by viewModels {
        ViewModelFactory.getInstance(context = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(EXTRA_ID, 0)

        viewModel.detailStory(id)
        //loadingState(true)
        if (viewModel.error.value == true) {
            //loadingState(false)
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.getDetailStory().observe(this) { story ->
                if (story != null) {
                    //loadingState(false)
                    binding.apply {
                        binding.tvName.text = story.title
                        binding.tvAuthor.text = story.author
                        binding.tvDesc.text = story.description
                        Glide.with(this@DetailStoryActivity)
                            .load(story.cover)
                            .into(ivImageStory)
                    }
                } else {
                    Log.e("DetailStoryActivity", "Data tidak ditemukan")
                    //loadingState(false)
                }
            }
        }

        binding.actionShare.setOnClickListener {
            val pdfUrl = intent.getStringExtra(EXTRA_CONTENT)
            if (pdfUrl != null) {
                openPDFWithThirdPartyApp(pdfUrl)
            }
        }

    }

    private fun openPDFWithThirdPartyApp(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(url), "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY

        // Verify if there is an app available to handle the intent
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Open PDF with"))
        } else {
            // No app available to handle the intent
            Toast.makeText(this, "No PDF viewer app found", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_COVER = "extra_cover"
        const val EXTRA_AUTHOR = "extra_author"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_CONTENT = "extra_content"
    }
}