package com.example.githubuserapp_kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubuserapp_kotlin.data.model.ResponseUserGithub
import com.example.githubuserapp_kotlin.databinding.FavoriteItemUserBinding

class FavoriteAdapter(
    private val data: MutableList<ResponseUserGithub.Item> = mutableListOf(),
    private val listener: (ResponseUserGithub.Item) -> Unit,
    private val deleteListener: (ResponseUserGithub.Item) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    fun setData(data: MutableList<ResponseUserGithub.Item>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class FavoriteViewHolder(private val binding: FavoriteItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: ResponseUserGithub.Item,
            listener: (ResponseUserGithub.Item) -> Unit,
            deleteListener: (ResponseUserGithub.Item) -> Unit
        ) {
            binding.ivItemProfile.load(item.avatar_url) {
                transformations(CircleCropTransformation())
            }
            binding.tvItemUsername.text = item.login
            binding.tvHtmlItemUrl.text = item.html_url

            binding.btnDelete.setOnClickListener {
                deleteListener(item)
            }

            binding.root.setOnClickListener {
                listener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder(
            FavoriteItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, listener, deleteListener)
    }

    override fun getItemCount(): Int = data.size
}