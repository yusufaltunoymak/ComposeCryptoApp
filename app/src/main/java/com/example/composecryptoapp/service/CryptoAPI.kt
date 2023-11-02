package com.example.composecryptoapp.service

import com.example.composecryptoapp.model.Crypto
import com.example.composecryptoapp.model.CryptoList
import retrofit2.http.GET

interface CryptoAPI {
    @GET("cryptolist.json")
    suspend fun getCryptoList(): CryptoList

    @GET("crypto.json")
    suspend fun getCrypto(): Crypto

}