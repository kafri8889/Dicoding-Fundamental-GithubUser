package com.anafthdev.githubuser.ui.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anafthdev.githubuser.databinding.FragmentDetailBinding
import com.anafthdev.githubuser.foundation.adapter.ProfilePagerAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailFragment: Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var profilePagerAdapter: ProfilePagerAdapter

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentDetailBinding.inflate(inflater, container, false).let { mBinding ->
            binding = mBinding
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            Timber.i("DetailState updated: $state")

            updateUI(state)
        }
    }

    private fun init() {
        arguments?.getString(EXTRA_USERNAME)?.let { username ->
            viewModel.getDetail(username)

            profilePagerAdapter = ProfilePagerAdapter(username, requireActivity())

            with(binding) {
                viewPager.adapter = profilePagerAdapter

                TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
                    tab.text = requireContext().getText(profilePagerAdapter.getLabel(pos))
                }.attach()
            }
        }

    }

    private fun updateUI(state: DetailState) {
        with(binding) {
            mainContent.visibility = if (state.isLoading || state.errorMsg.isNotBlank()) View.GONE else View.VISIBLE
            circularProgressIndicator.visibility = if (state.isLoading) View.VISIBLE else View.GONE

            tvBio.visibility = if (state.user?.bio.isNullOrBlank()) View.GONE else View.VISIBLE
            tvBio.text = state.user?.bio ?: ""

            tvName.visibility = if (state.user?.name.isNullOrBlank()) View.GONE else View.VISIBLE
            tvName.text = state.user?.name ?: ""

            tvUsername.text = state.user?.login?.let { "@$it" } ?: ""
            tvFollowersCount.text = state.user?.followers?.toString() ?: "0"
            tvFollowingCount.text = state.user?.following?.toString() ?: "0"

            tvError.visibility = if (state.errorMsg.isNotBlank()) View.VISIBLE else View.GONE
            tvError.text = state.errorMsg

            Glide.with(requireContext())
                .load(state.user?.avatarUrl)
                .placeholder(ColorDrawable(Color.LTGRAY))
                .into(image)
        }
    }

    companion object {
        const val EXTRA_USERNAME = "username"
    }
}