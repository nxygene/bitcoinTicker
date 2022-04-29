package com.sberkozd.bitcointicker.ui.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.sberkozd.bitcointicker.Event
import com.sberkozd.bitcointicker.Event.Register
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val auth: FirebaseAuth,
) :
    ViewModel() {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun onRegisterButtonClicked(
        email: String,
        password: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            var isValid = true

            if (email.isBlank()) {
                isValid = false
                eventChannel.send(Register.ShowEmailEmptyToast)
            } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches().not()) {
                isValid = false
                eventChannel.send(Register.ShowNoEmailError)
            }

            if (password.isBlank()) {
                isValid = false
                eventChannel.send(Register.ShowPasswordEmptyToast)
            } else if (password.length < 6) {
                isValid = false
                eventChannel.send(Register.ShowPasswordTooShortToast)
            }

            if (isValid) {
                eventChannel.send(Register.PromptRegister)
            }
        }
    }

    fun register(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                auth.currentUser?.email?.let {
                    viewModelScope.launch {
                        eventChannel.send(Register.SuccessfulRegister(it))
                    }
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    eventChannel.send(Register.FailRegister)
                }
            }
    }
}