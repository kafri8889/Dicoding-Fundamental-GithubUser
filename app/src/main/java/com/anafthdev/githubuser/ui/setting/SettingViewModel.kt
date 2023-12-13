package com.anafthdev.githubuser.ui.setting

import androidx.lifecycle.viewModelScope
import com.anafthdev.githubuser.data.repository.UserPreferenceRepository
import com.anafthdev.githubuser.foundation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userPreferenceRepository: UserPreferenceRepository
): BaseViewModel<SettingState>(SettingState()) {

    init {
        viewModelScope.launch {
            userPreferenceRepository.getUserPreference.collectLatest { pref ->
                updateState {
                    copy(
                        isDarkTheme = pref.isDarkTheme
                    )
                }
            }
        }
    }

    fun setDarkTheme(isDarkTheme: Boolean) = viewModelScope.launch {
        userPreferenceRepository.setIsDarkTheme(isDarkTheme)

        updateState {
            copy(
                isDarkTheme = isDarkTheme
            )
        }
    }

}