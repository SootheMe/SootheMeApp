package com.example.sootheme.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sootheme.R
import com.example.sootheme.adapter.MusicAdapter
import com.example.sootheme.data.MusicData
import com.example.sootheme.databinding.ActivityMusicBinding
import com.example.sootheme.model.MusicViewModel
import com.example.sootheme.network.ViewModelFactory

class MusicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMusicBinding
    private lateinit var adapter: MusicAdapter
    private val viewModel: MusicViewModel by viewModels {
        ViewModelFactory.getInstance(context = this)
    }
    private val recyclerView: RecyclerView
        get() = binding.rvMusic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MusicAdapter()
        adapter.setOnItemClickCallback(object : MusicAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MusicData) {
                Intent(this@MusicActivity, DetailMusicActivity::class.java).also {
                    it.putExtra(DetailStoryActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailStoryActivity.EXTRA_TITLE, data.title)
                    it.putExtra(DetailStoryActivity.EXTRA_COVER, data.cover)
                    it.putExtra(DetailStoryActivity.EXTRA_CONTENT, data.content)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this@MusicActivity)
            recyclerView.adapter = adapter

            viewModel.getMusicTime()
            viewModel.user.observe(this@MusicActivity) { music ->
                if (music != null) {
                    adapter.setUser(music)
                }
            }
        }
    }
}