package com.anafthdev.githubuser.foundation.adapter

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.anafthdev.githubuser.R
import com.anafthdev.githubuser.ui.followers_following.FollowersFollowingFragment

class ProfilePagerAdapter(
    private val username: String,
    activity: FragmentActivity
): FragmentStateAdapter(activity) {

    private val labels = intArrayOf(
        R.string.followers,
        R.string.following,
    )

    override fun getItemCount(): Int = labels.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowersFollowingFragment.newInstance(username, FollowersFollowingFragment.TYPE_FOLLOWERS)
            1 -> FollowersFollowingFragment.newInstance(username, FollowersFollowingFragment.TYPE_FOLLOWING)
            else -> throw IndexOutOfBoundsException("Invalid fragment position: $position")
        }
    }

    @StringRes
    fun getLabel(position: Int): Int {
        return labels[position]
    }

}