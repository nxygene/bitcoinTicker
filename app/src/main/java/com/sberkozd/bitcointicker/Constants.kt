package com.sberkozd.bitcointicker

const val baseUrl = "https://api.coingecko.com/api/v3/"
const val coinList = "coins/markets"
const val dbVersion = 1
const val database_name = "Coin.db"


sealed class Event {
    object ShowLoading : Event()
    object HideLoading : Event()
    data class ShowToast(val text : String) : Event()

    sealed class Register : Event() {
        object ShowNoEmailError : Register()
        object ShowEmailEmptyToast : Register()
        object ShowPasswordEmptyToast : Register()
        object ShowPasswordTooShortToast : Register()
        object PromptRegister : Register()
        data class SuccessfulRegister(val email: String) : Register()
        object FailRegister : Register()
    }

    sealed class Login : Event() {
        object ShowEmailEmptyToast : Login()
        object ShowPasswordEmptyToast : Login()
        object PromptLogin : Login()
        data class SuccessfulLogin(val email: String) : Login()
        object FailedLogin : Login()
    }

    sealed class Home : Event() {
        object Logout : Home()
    }
}



