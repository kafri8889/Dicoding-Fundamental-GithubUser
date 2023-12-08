package com.anafthdev.githubuser.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.anafthdev.githubuser.databinding.FragmentDashboardBinding
import com.anafthdev.githubuser.foundation.adapter.UserRecyclerViewAdapter
import timber.log.Timber

class DashboardFragment: Fragment() {

    private lateinit var userRecyclerViewAdapter: UserRecyclerViewAdapter
    private lateinit var binding: FragmentDashboardBinding

    private val viewModel: DashboardViewModel by viewModels()

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

            showLoading(state.isLoading)
            userRecyclerViewAdapter.submitList(state.users)
        }
    }

    private fun init() {
        userRecyclerViewAdapter = UserRecyclerViewAdapter()

        with(binding) {
            searchView.apply {
                setupWithSearchBar(searchBar)

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

    private fun showLoading(show: Boolean) {
        with(binding) {
            recyclerViewUser.visibility = if (show) View.GONE else View.VISIBLE
            circularProgressIndicator.visibility = if (show) View.VISIBLE else View.GONE
        }
    }
}