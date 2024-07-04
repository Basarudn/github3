package com.example.githubuser.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.databinding.ActivityDetailUserBinding

import com.google.android.material.tabs.TabLayoutMediator


class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        const val EXSTRA_USERNAME = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXSTRA_USERNAME)

        supportActionBar?.elevation = 0f

        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)

        if (username !=null) {
            detailViewModel.getUserDetail(username)
        }
        detailViewModel.detailUser.observe(this) { userDetail ->
            userDetail?.let {
                setDetailuser(it)
            }
        }
        detailViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
        if (username !=null) {
            val sectionsPagerAdapter = SectionPagerAdapter(this)
            sectionsPagerAdapter.username = username
            binding.viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Followers"
                    1 -> "Following"
                    else -> null
                }
            }.attach()
        }
    }
    private fun setDetailuser(userDetail : DetailUserResponse) {
        binding.apply {
            Glide.with(this@DetailUserActivity)
                .load(userDetail)
                .circleCrop()
                .into(imgProfile)
            name.text = userDetail.name
            username.text = userDetail.login
            binding.tvFollowers.text = "${userDetail.followers.toString()} followers"
            binding.tvFollowing.text = "${userDetail.following.toString()} following"
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}
