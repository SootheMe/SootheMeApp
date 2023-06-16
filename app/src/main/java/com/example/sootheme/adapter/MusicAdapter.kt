package com.example.sootheme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.sootheme.data.MusicData
import com.example.sootheme.databinding.MusicRowBinding

class MusicAdapter : RecyclerView.Adapter<MusicAdapter.UserViewHolder>() {

    private val list = ArrayList<MusicData>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setUser(users: ArrayList<MusicData>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: MusicRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(music: MusicData) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(music)
            }
            binding.apply {
                Glide.with(itemView.context)
                    .load(music.cover)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imgItemPhoto)
                tvItemName.text = music.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = MusicRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: MusicData)
    }
}