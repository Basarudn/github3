package com.example.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class SectionPagerAdapter(activity: AppCompatActivity,) : FragmentStateAdapter(activity) {

    var username: String = ""

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowerFragment.newInstance(username)
            1 -> FollowingFragment.newInstance(username)
            else -> throw IllegalStateException("invalid postion")
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

}
