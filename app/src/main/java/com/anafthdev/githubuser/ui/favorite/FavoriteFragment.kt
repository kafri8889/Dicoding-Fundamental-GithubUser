package com.anafthdev.githubuser.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anafthdev.githubuser.R
import com.anafthdev.githubuser.databinding.FragmentFavoriteBinding
import com.anafthdev.githubuser.foundation.adapter.UserRecyclerViewAdapter
import com.anafthdev.githubuser.ui.detail.DetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment: Fragment() {

    private lateinit var userRecyclerViewAdapter: UserRecyclerViewAdapter
    private lateinit var binding: FragmentFavoriteBinding

    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentFavoriteBinding.inflate(inflater, container, false).let { mBinding ->
            binding = mBinding
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        viewModel.state.observe(viewLifecycleOwner, ::updateUi)
    }

    private fun init() {
        userRecyclerViewAdapter = UserRecyclerViewAdapter().apply {
            setOnItemClickListener { user ->
                binding.root.findNavController().navigate(
                    R.id.action_favoriteFragment_to_detailFragment,
                    bundleOf(DetailFragment.EXTRA_USERNAME to user.login)
                )
            }
        }

        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = userRecyclerViewAdapter
            }
        }
    }

    private fun updateUi(state: FavoriteState) {
        userRecyclerViewAdapter.submitList(state.users)

        with(binding) {
            tvNoFavUsers.visibility = if (state.users.isEmpty()) View.VISIBLE else View.GONE
            recyclerView.visibility = if (state.users.isEmpty()) View.GONE else View.VISIBLE
        }
    }
}