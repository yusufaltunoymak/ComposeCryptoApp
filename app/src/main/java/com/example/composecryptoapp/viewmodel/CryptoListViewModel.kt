package com.example.composecryptoapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composecryptoapp.model.CryptoListItem
import com.example.composecryptoapp.repository.CryptoRepository
import com.example.composecryptoapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    var cryptoList = mutableStateOf<List<CryptoListItem>>(listOf())
    var errorMessage = mutableStateOf("")
    val isLoading = mutableStateOf(false)

    private var initialCryptoList = listOf<CryptoListItem>() //downloaded data search
    private var isSearchStarting = true

    init {
        loadCryptos() //download app first time opening
    }

    fun searchCryptoList(query : String) {
        val listToSearch = if(isSearchStarting){
            cryptoList.value
        }
        else {
            initialCryptoList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()) {
                cryptoList.value = initialCryptoList
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.currency!!.contains(other = query.trim(),ignoreCase = true)
            }
            if(isSearchStarting) {
                initialCryptoList = cryptoList.value
                isSearchStarting = false
            }
            cryptoList.value = results
        }
    }

    fun loadCryptos() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCryptoList()
            when(result) {
                is Resource.Success -> {
                    val cryptoItems = result.data!!.mapIndexed { index, cryptoListItem ->
                        CryptoListItem(cryptoListItem.currency,cryptoListItem.price)

                    }
                    cryptoList.value += cryptoItems
                    errorMessage.value = ""
                    isLoading.value = false
                }
                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false

                }

                else -> {}
            }
        }
    }

}