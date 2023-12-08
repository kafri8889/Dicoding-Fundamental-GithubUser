package com.anafthdev.githubuser.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anafthdev.githubuser.databinding.FragmentDetailBinding

class DetailFragment: Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentDetailBinding.inflate(inflater, container, false).let { mBinding ->
            binding = mBinding
            binding.root
        }
    }

}