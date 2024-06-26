package com.example.githubuserapp_kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubuserapp_kotlin.data.model.ResponseUserGithub
import com.example.githubuserapp_kotlin.databinding.ItemUserBinding

class UserAdapter(
    private val data: MutableList<ResponseUserGithub.Item> = mutableListOf(),
    private val listener: (ResponseUserGithub.Item) -> Unit
) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    fun setData(data: MutableList<ResponseUserGithub.Item>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val v: ItemUserBinding) : RecyclerView.ViewHolder(v.root) {
        fun bind(item: ResponseUserGithub.Item) {
            v.ivItemProfile.load(item.avatar_url) {
                transformations(CircleCropTransformation())
            }

            v.tvItemUsername.text = item.login

            v.tvHtmlItemUrl.text = item.html_url
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

    override fun getItemCount(): Int = data.size
}