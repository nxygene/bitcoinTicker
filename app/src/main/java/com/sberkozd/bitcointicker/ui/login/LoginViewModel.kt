package com.sberkozd.bitcointicker.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.sberkozd.bitcointicker.Event
import com.sberkozd.bitcointicker.Event.Login
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth,
) : ViewModel() {

    var toastString = MutableLiveData<String>()

    private val eventChannel = Channel<Event>(Channel.BUFFERED)

    val eventsFlow = eventChannel.receiveAsFlow()

    fun onLoginButtonClicked(
        email: String,
        password: String,
    ) {

        when {
            email.isBlank() -> {
                viewModelScope.launch {
                    eventChannel.send(Login.ShowEmailEmptyToast)
                }
            }
            password.isBlank() -> {
                viewModelScope.launch {
                    eventChannel.send(Login.ShowPasswordEmptyToast)
                }
            }
            else -> {
                viewModelScope.launch(Dispatchers.IO) {
                    eventChannel.send(Login.PromptLogin)
                }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            eventChannel.send(Event.ShowLoading)
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    auth.currentUser?.email?.let {
                        viewModelScope.launch {
                            eventChannel.send(Login.SuccessfulLogin(it))
                            eventChannel.send(Event.HideLoading)
                        }
                    }
                }.addOnFailureListener {
                    viewModelScope.launch {
                        eventChannel.send(Login.FailedLogin)
                        eventChannel.send(Event.HideLoading)
                    }
                }
        }
    }


}