package com.example.homework13_revised

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterPageViewModel : ViewModel() {
    private val _parsedJsonData = MutableLiveData<List<ListContainer>>()
    val parsedJsonData: LiveData<List<ListContainer>> get() = _parsedJsonData

    fun setParsedJsonData(data: List<ListContainer>) {
        _parsedJsonData.value = data
    }
}