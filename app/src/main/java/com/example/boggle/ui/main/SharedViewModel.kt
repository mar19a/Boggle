package com.example.boggle.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel(){
    val newGameClick = MutableLiveData<Boolean>()
    var typedText = MutableLiveData<String>()
}
