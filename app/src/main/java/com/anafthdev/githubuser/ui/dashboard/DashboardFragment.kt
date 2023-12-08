package com.anafthdev.githubuser.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anafthdev.githubuser.R
import com.anafthdev.githubuser.databinding.FragmentDashboardBinding
import com.anafthdev.githubuser.foundation.adapter.UserRecyclerViewAdapter
import com.anafthdev.githubuser.ui.detail.DetailFragment
import com.google.android.material.search.SearchView
import timber.log.Timber

class DashboardFragment: Fragment() {

    private lateinit var userRecyclerViewAdapter: UserRecyclerViewAdapter
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var binding: FragmentDashboardBinding

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ketika search view terbuka dan user menekan tombol back, tutup
        onBackPressedCallback = requireActivity().onBackPressedDispatcher.addCallback(this, false) {
            binding.searchView.hide()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentDashboardBinding.inflate(inflater, container, false).let { mBinding ->
            binding = mBinding
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            Timber.i("DashboardState updated: $state")

            updateUi(state)
            userRecyclerViewAdapter.submitList(state.users)
        }
    }

    private fun init() {
        userRecyclerViewAdapter = UserRecyclerViewAdapter().apply {
            setOnItemClickListener { user ->
                binding.root.findNavController().navigate(
                    R.id.action_dashboardFragment_to_detailFragment,
                    bundleOf(DetailFragment.EXTRA_USERNAME to user.login)
                )
            }
        }

        with(binding) {
            searchView.apply {
                setupWithSearchBar(searchBar)

                addTransitionListener { _, _, newState ->
                    onBackPressedCallback.isEnabled = newState == SearchView.TransitionState.SHOWN || newState ==SearchView.TransitionState.SHOWING
                }

                editText.setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()

                    viewModel.search(searchView.text.toString())

                    false
                }
            }

            recyclerViewUser.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = userRecyclerViewAdapter
            }
        }
    }

    private fun updateUi(state: DashboardState) {
        with(binding) {
            recyclerViewUser.visibility = if (state.isLoading || state.errorMsg.isNotBlank()) View.GONE else View.VISIBLE
            circularProgressIndicator.visibility = if (state.isLoading) View.VISIBLE else View.GONE

            tvError.visibility = if (state.errorMsg.isNotBlank()) View.VISIBLE else View.GONE
            tvError.text = state.errorMsg
        }
    }
}