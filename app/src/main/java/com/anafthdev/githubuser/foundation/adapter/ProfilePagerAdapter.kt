package com.anafthdev.githubuser.foundation.adapter

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.anafthdev.githubuser.R
import com.anafthdev.githubuser.ui.followers_following.FollowersFollowingFragment
import com.anafthdev.githubuser.ui.repositories.RepositoriesFragment

class ProfilePagerAdapter(
    private val username: String,
    activity: FragmentActivity
): FragmentStateAdapter(activity) {

    private val labels = intArrayOf(
        R.string.followers,
        R.string.following,
        R.string.repositories,
    )

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowersFollowingFragment.newInstance(username, FollowersFollowingFragment.TYPE_FOLLOWERS)
            1 -> FollowersFollowingFragment.newInstance(username, FollowersFollowingFragment.TYPE_FOLLOWING)
            2 -> RepositoriesFragment()
            else -> throw IndexOutOfBoundsException("Invalid fragment position: $position")
        }
    }

    @StringRes
    fun getLabel(position: Int): Int {
        return labels[position]
    }

}