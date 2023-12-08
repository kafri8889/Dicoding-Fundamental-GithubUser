package com.anafthdev.githubuser.foundation.extension

import android.content.Context
import android.widget.Toast

fun Context.toast(any: Any?, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, any.toString(), length).show()
}
