package com.honeyauto.chatGPTIP

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OverViewModel(): ViewModel() {
    val db = Firebase.firestore

    private var _categori = MutableLiveData<List<String>>()
    val categori: LiveData<List<String>>
        get() = _categori

    private var _categoriWord = MutableLiveData<String>()
    val categoriWord: LiveData<String>
        get() = _categoriWord

    init {
        getDB()
    }

    fun getDB() {
        val test = mutableListOf<String>()

        db.collection("catagori")
            .get()
            .addOnSuccessListener {result ->
                for(document in result) {
                    test.add(document.id)
                    test.add(document.data.toString())
                }
            }
            .addOnFailureListener{exception ->
                Log.d("testDB","error getting documents", exception)
            }
        _categori.value = test
    }

    fun getCategori(string: String) {
        _categoriWord.value = string
    }
}