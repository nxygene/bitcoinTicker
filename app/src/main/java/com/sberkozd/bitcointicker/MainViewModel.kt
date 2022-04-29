package com.sberkozd.bitcointicker

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _progress: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val progress: StateFlow<Boolean>
        get() = _progress

    fun showLoading() {
        _progress.value = true
    }

    fun hideLoading() {
        _progress.value = false
    }

}