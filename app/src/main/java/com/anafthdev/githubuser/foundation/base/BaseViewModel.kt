package com.anafthdev.githubuser.foundation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Kelas dasar untuk view model
 */
abstract class BaseViewModel<STATE>(
    private val defaultState: STATE
): ViewModel() {

    private val _state = MutableLiveData(defaultState)
    val state: LiveData<STATE> = _state

    protected fun updateState(newState: STATE.() -> STATE) {
        _state.value = newState(state.value ?: defaultState)
    }

}