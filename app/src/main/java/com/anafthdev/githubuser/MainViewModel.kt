package com.anafthdev.githubuser

import androidx.lifecycle.viewModelScope
import com.anafthdev.githubuser.data.repository.UserPreferenceRepository
import com.anafthdev.githubuser.foundation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferenceRepository: UserPreferenceRepository
): BaseViewModel<MainState>(MainState()) {

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

}