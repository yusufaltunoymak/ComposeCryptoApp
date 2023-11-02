package com.example.composecryptoapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.composecryptoapp.model.Crypto
import com.example.composecryptoapp.repository.CryptoRepository
import com.example.composecryptoapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repository : CryptoRepository
): ViewModel() {
    suspend fun getCrypto(id: String): Resource<Crypto> {
        return repository.getCrypto(id)
    }
}