package com.example.composecryptoapp.repository

import com.example.composecryptoapp.model.Crypto
import com.example.composecryptoapp.model.CryptoList
import com.example.composecryptoapp.service.CryptoAPI
import com.example.composecryptoapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CryptoRepository @Inject constructor(
    private val api : CryptoAPI
) {
    suspend fun getCryptoList() : Resource<CryptoList>{
        val response = try {
            api.getCryptoList()
        }
        catch (e:Exception) {
            return Resource.Error("Error.")
        }
        return Resource.Success(response)
    }

    suspend fun getCrypto() : Resource<Crypto> {
        val response = try {
            api.getCrypto()
        }
        catch (e:Exception){
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }
}