package com.sberkozd.bitcointicker.ui.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.sberkozd.bitcointicker.Event
import com.sberkozd.bitcointicker.Event.*
import com.sberkozd.bitcointicker.data.local.database.CoinEntity
import com.sberkozd.bitcointicker.data.models.FirestoreModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    state: SavedStateHandle,
    val repository: HomeRepository,
    val auth: FirebaseAuth,
    val firestore: FirebaseFirestore,
) : ViewModel() {

    private val _coinListResponse: MutableSharedFlow<List<CoinEntity>?> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val coinListResponse: SharedFlow<List<CoinEntity>?> = _coinListResponse

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    var firestorefavoriteCoinIds: Set<String>? = null

    private var isNewLogin = state.get<Boolean>("isNewLogin")

    init {
        if (isNewLogin == true) {
            getAllFavoriteCoinsFromFirestore()
        } else {
            getAllCoinsFromNetwork(firestorefavoriteCoinIds)
        }
    }

    fun getAllCoinsFromNetwork(favoriteCoinIds: Set<String>? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            eventChannel.send(ShowLoading)
            repository.getCoins(
                favoriteCoinIds,
                onSuccess = {
                    eventChannel.send(HideLoading)
                },
                onError = {
                    it?.let { eventChannel.send(ShowToast(it)) }
                    eventChannel.send(HideLoading)
                })
                .stateIn(viewModelScope)
                .collect {
                    _coinListResponse.tryEmit(it)
                    cancel()
                }
        }
    }

    fun getAllCoinsFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            eventChannel.send(ShowLoading)
            repository.getCoinsFromDB().collect {
                _coinListResponse.tryEmit(it)
                eventChannel.send(HideLoading)
                cancel()
            }
        }
    }

    fun logout() {
        auth.signOut()
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
            eventChannel.send(Home.Logout)
        }
    }

    fun favoriteClicked(coin: CoinEntity, isFavorited: Boolean) {

        val firestoreModel = FirestoreModel(coin.id)

        viewModelScope.launch(Dispatchers.IO) {
            eventChannel.send(ShowLoading)
            if (isFavorited) {
                firestore.collection("CoinModel")
                    .document(auth.currentUser?.email ?: "")
                    .update(firestoreModel.id!!, FieldValue.delete())
                    .addOnSuccessListener {
                        Log.e("Firestore", "Deleted!")
                    }.addOnFailureListener {
                        Log.e("Firestore", it.toString())
                    }
                repository.removeFavorite(coin.id)
            } else {
                val model = hashMapOf(firestoreModel.id to firestoreModel)
                firestore.collection("CoinModel")
                    .document(auth.currentUser?.email ?: "")
                    .set(model, SetOptions.merge())
                    .addOnSuccessListener {
                        Log.e("Firestore", "Added!")
                    }.addOnFailureListener {
                        Log.e("Firestore", it.toString())
                    }
                repository.setFavorite(coin.id)
            }

            getAllCoinsFromDb()
            cancel()
        }
    }

    private fun getAllFavoriteCoinsFromFirestore() {
        viewModelScope.launch(Dispatchers.IO) {
            eventChannel.send(ShowLoading)
            val db = FirebaseFirestore.getInstance()
            db.collection("CoinModel").document(auth.currentUser?.email ?: "")
                .get()
                .addOnSuccessListener { document ->
                    document.data?.let {
                        firestorefavoriteCoinIds = it.keys
                        getAllCoinsFromNetwork(firestorefavoriteCoinIds)
                    }
                }.addOnCompleteListener{
                    viewModelScope.launch(Dispatchers.IO) {
                        eventChannel.send(HideLoading)
                    }
                }
            cancel()
        }
    }

    fun onSearchKeywordChanged(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            eventChannel.send(ShowLoading)
            repository.searchCoins(keyword).collect {
                _coinListResponse.tryEmit(it)
                eventChannel.send(HideLoading)
            }
        }
    }


}