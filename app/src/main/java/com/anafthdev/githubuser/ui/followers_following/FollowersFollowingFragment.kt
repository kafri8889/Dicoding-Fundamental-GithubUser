package com.anafthdev.githubuser.ui.followers_following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.anafthdev.githubuser.databinding.FragmentFollowersFollowingBinding
import com.anafthdev.githubuser.foundation.adapter.UserRecyclerViewAdapter
import timber.log.Timber

class FollowersFollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowersFollowingBinding
    private lateinit var userRecyclerViewAdapter: UserRecyclerViewAdapter

    private val viewModel: FollowersFollowingViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentFollowersFollowingBinding.inflate(inflater, container, false).let { mBinding ->
            binding = mBinding
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            Timber.i("FollowersFollowingState updated: $state")

            updateUi(state)
            userRecyclerViewAdapter.submitList(state.users)
        }
    }

    private fun init() {
        viewModel.getFollowersOrFollowing(
            arguments?.getString(EXTRA_USERNAME),
            arguments?.getString(EXTRA_TYPE)
        )

        userRecyclerViewAdapter = UserRecyclerViewAdapter()

        with(binding) {
            userRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = userRecyclerViewAdapter
            }
        }
    }

    private fun updateUi(state: FollowersFollowingState) {
        with(binding) {
            circularProgressIndicator.visibility = if (state.isLoading) View.VISIBLE else View.GONE
            userRecyclerView.visibility = if (state.isLoading) View.GONE else View.VISIBLE
        }
    }

    companion object {
        const val EXTRA_TYPE = "type"
        const val EXTRA_USERNAME = "username"

        const val TYPE_FOLLOWERS = "followers"
        const val TYPE_FOLLOWING = "following"

        fun newInstance(username: String, type: String): Fragment {
            return FollowersFollowingFragment().apply {
                arguments = bundleOf(
                    EXTRA_USERNAME to username,
                    EXTRA_TYPE to type
                )
            }
        }
    }
}