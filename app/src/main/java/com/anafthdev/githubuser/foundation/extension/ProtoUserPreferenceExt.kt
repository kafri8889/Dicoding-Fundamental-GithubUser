package com.anafthdev.githubuser.foundation.extension

import com.anafthdev.githubuser.ProtoUserPreference
import com.anafthdev.githubuser.data.model.UserPreference

fun ProtoUserPreference.toUserPreference(): UserPreference {
    return UserPreference(
        isDarkTheme = isDarkTheme,
        isDynamicColor = isDynamicColor,
    )
}
