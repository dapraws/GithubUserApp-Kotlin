package com.example.githubuserapp_kotlin.detail

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubuserapp_kotlin.R
import com.example.githubuserapp_kotlin.adapter.DetailAdapter
import com.example.githubuserapp_kotlin.adapter.UserAdapter
import com.example.githubuserapp_kotlin.data.local.DbModule
import com.example.githubuserapp_kotlin.data.model.ResponseDetailUser
import com.example.githubuserapp_kotlin.data.model.ResponseUserGithub
import com.example.githubuserapp_kotlin.databinding.ActivityDetailBinding
import com.example.githubuserapp_kotlin.detail.follow.FollowFragment
import com.example.githubuserapp_kotlin.favorite.FavoriteActivity
import com.example.githubuserapp_kotlin.utils.Result
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModel.Factory(DbModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val item = intent.getParcelableExtra<ResponseUserGithub.Item>("item")
        val username = item?.login ?: ""

        viewModel.resultDetailUser.observe(this) {
            when (it) {
                is Result.Success<*> -> {
                    val user = it.data as ResponseDetailUser
                    binding.ivProfileDetail.load(user.avatar_url) {
                        transformations(CircleCropTransformation())
                    }

                    binding.tvUsername.text = user.login
                    binding.tvName.text = user.name
                    binding.tvFollowers.text = "${user.followers} followers"
                    binding.tvFollowing.text = "${user.following} following"
                }

                is Result.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }

                else -> {}
            }
        }
        viewModel.getDetailUser(username)

        val fragments = mutableListOf<Fragment>(
            FollowFragment.newInstance(FollowFragment.FOLLOWERS),
            FollowFragment.newInstance(FollowFragment.FOLLOWING)
        )
        val titleFragments = mutableListOf(
            getString(R.string.followers), getString(R.string.following),
        )
        val adapter = DetailAdapter(this, fragments)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, posisi ->
            tab.text = titleFragments[posisi]
        }.attach()

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    viewModel.getFollowers(username)
                } else {
                    viewModel.getFollowing(username)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewModel.getFollowers(username)

        viewModel.resultSuccessFavorite.observe(this) {
            binding.btnFavorite.changeIconColor(R.color.red)
        }

        viewModel.resultDeleteFavorite.observe(this) {
            binding.btnFavorite.changeIconColor(R.color.white)
        }

        binding.btnFavorite.setOnClickListener {
            viewModel.setFavorite(item)
        }

        viewModel.findFavorite(item?.id ?: 0) {
            binding.btnFavorite.changeIconColor(R.color.red)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }

            R.id.share -> {
                shareContent()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareContent() {
        val item = intent.getParcelableExtra<ResponseUserGithub.Item>("item")
        val profileUrl = item?.html_url ?: ""

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Check this GitHub User: $profileUrl"
        )
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }
}

fun FloatingActionButton.changeIconColor(@ColorRes color: Int) {
    imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
}