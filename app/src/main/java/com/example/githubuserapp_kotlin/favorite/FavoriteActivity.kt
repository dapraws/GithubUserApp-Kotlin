package com.example.githubuserapp_kotlin.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp_kotlin.adapter.FavoriteAdapter
import com.example.githubuserapp_kotlin.adapter.UserAdapter
import com.example.githubuserapp_kotlin.data.local.DbModule
import com.example.githubuserapp_kotlin.databinding.ActivityFavoriteBinding
import com.example.githubuserapp_kotlin.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val adapter by lazy {
        FavoriteAdapter(
            data = mutableListOf(),
            listener = { user ->
                Intent(this, DetailActivity::class.java).apply {
                    putExtra("item", user)
                    startActivity(this)
                }
            },
            deleteListener = { user ->
                viewModel.deleteFavorite(user)
            }
        )
    }

    private val viewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModel.Factory(DbModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = adapter

        viewModel.getUserFavorite().observe(this) {
            adapter.setData(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}