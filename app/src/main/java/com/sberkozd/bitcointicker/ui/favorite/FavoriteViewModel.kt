package com.sberkozd.bitcointicker.ui.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.sberkozd.bitcointicker.Event
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val repository: FavoriteRepository,
    val firestore: FirebaseFirestore,
    val auth: FirebaseAuth,
) : ViewModel() {

    var fetchedDataFromFirestore: Boolean? = null

    private val _favoriteListResponse: MutableSharedFlow<List<CoinEntity>?> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val favoriteListResponse: SharedFlow<List<CoinEntity>?> = _favoriteListResponse

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    init {
        getAllFavoriteCoinsFromFirestore()
    }

    private fun getAllFavoriteCoinsFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            eventChannel.send(Event.ShowLoading)
            repository.getFavoriteCoins().collect {
                _favoriteListResponse.tryEmit(it.filter { it.isFavorite == 1 })
                eventChannel.send(Event.HideLoading)
                cancel()
            }
        }
    }

    fun favoriteClicked(coin: CoinEntity, isFavorited: Boolean) {

        val firestoreModel = FirestoreModel(coin.id)

        viewModelScope.launch(Dispatchers.IO) {
            eventChannel.send(Event.ShowLoading)
            if (isFavorited) {
                firestore.collection("CoinModel")
                    .document(auth.currentUser?.email ?: "")
                    .update(firestoreModel.id!!, FieldValue.delete())
                    .addOnSuccessListener {
                        viewModelScope.launch(Dispatchers.IO) {
                            repository.removeFavorite(coin.id)
                            getAllFavoriteCoinsFromDb()
                        }
                        Log.e("Firestore", "Deleted!")
                    }.addOnFailureListener {
                        Log.e("Firestore", it.toString())
                    }
            } else {
                val model = hashMapOf(firestoreModel.id to firestoreModel)
                firestore.collection("CoinModel")
                    .document(auth.currentUser?.email ?: "")
                    .set(model, SetOptions.merge())
                    .addOnSuccessListener {
                        viewModelScope.launch(Dispatchers.IO) {
                            repository.setFavorite(coin.id)
                            getAllFavoriteCoinsFromDb()
                        }
                        Log.e("Firestore", "Added!")
                    }.addOnFailureListener {
                        Log.e("Firestore", it.toString())
                    }

            }
        }
    }

    private fun getFavoriteCoins(coinIdList: MutableSet<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            eventChannel.send(Event.ShowLoading)
            repository.getCoinsFromDB().collect {
                _favoriteListResponse.tryEmit(it.filter { it.id in coinIdList })
                eventChannel.send(Event.HideLoading)
                this.cancel()
            }
        }
    }

    fun getAllFavoriteCoinsFromFirestore() {
        viewModelScope.launch(Dispatchers.IO) {
            eventChannel.send(Event.ShowLoading)
            val db = FirebaseFirestore.getInstance()
            db.collection("CoinModel").document(auth.currentUser?.email ?: "")
                .get()
                .addOnSuccessListener { document ->
                    fetchedDataFromFirestore = true
                    document.data?.let {
                        getFavoriteCoins(it.keys)
                    }
                }
        }
    }
}
