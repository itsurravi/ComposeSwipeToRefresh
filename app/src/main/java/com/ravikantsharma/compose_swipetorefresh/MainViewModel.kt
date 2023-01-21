package com.ravikantsharma.compose_swipetorefresh

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _listItems = MutableStateFlow<List<SwipeListItems>>(emptyList())
    val listItems = _listItems.asStateFlow()

    private var lastCount = 10

    init {
        loadStuff()
    }


    fun loadStuff() {
        viewModelScope.launch {
            _isLoading.value = true

            val data = async {
                val list = mutableListOf<SwipeListItems>()
                for (i in 0..lastCount) {
                    list.add(SwipeListItems(i, "Item $i"))
                }
                lastCount += 10
                list
            }
            _listItems.value = data.await()
            delay(2000L)

            _isLoading.value = false
        }
    }
}