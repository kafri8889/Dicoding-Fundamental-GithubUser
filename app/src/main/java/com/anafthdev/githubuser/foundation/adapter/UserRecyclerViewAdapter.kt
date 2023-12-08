package com.anafthdev.githubuser.foundation.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anafthdev.githubuser.data.model.User
import com.anafthdev.githubuser.databinding.UserItemBinding
import com.anafthdev.githubuser.foundation.common.OnItemClickListener
import com.bumptech.glide.Glide

class UserRecyclerViewAdapter: ListAdapter<User, UserRecyclerViewAdapter.UserViewHolder>(UserDiffCallback()) {

    private var listener: OnItemClickListener<User>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnItemClickListener(l: OnItemClickListener<User>) {
        listener = l
    }

    inner class UserViewHolder(private val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.username.text = user.login

            if (listener != null) {
                binding.root.setOnClickListener {
                    listener!!.onItemClick(user)
                }
            }

            Glide.with(binding.image.context)
                .load(user.avatarUrl)
                .placeholder(ColorDrawable(Color.LTGRAY))
                .into(binding.image)
        }
    }

    class UserDiffCallback: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.login == newItem.login
        }
    }

}
