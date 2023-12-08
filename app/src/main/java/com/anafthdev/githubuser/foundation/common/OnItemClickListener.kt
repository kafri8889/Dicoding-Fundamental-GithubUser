package com.anafthdev.githubuser.foundation.common

/**
 * Listener untuk digunakan di recycler adapter
 */
fun interface OnItemClickListener<T> {
    fun onItemClick(item: T)
}