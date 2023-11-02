package com.example.composecryptoapp.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.composecryptoapp.model.Crypto
import com.example.composecryptoapp.util.Resource
import com.example.composecryptoapp.viewmodel.CryptoDetailViewModel

@Composable
fun CryptoDetailScreen(
    id : String,
    price : String,
    navController: NavController,
    viewModel: CryptoDetailViewModel = hiltViewModel()
){
    val cryptoItem = produceState<Resource<Crypto>>(initialValue = Resource.Loading()) {
        value = viewModel.getCrypto(id)
    }.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (cryptoItem) {
                is Resource.Success -> {
                    val selectedCrypto = cryptoItem.data!![0]
                    Log.d("CryptoDetailScreen", "Selected Crypto: ${selectedCrypto.name}, Logo URL: ${selectedCrypto.logoUrl}")
                    Text(
                        text = selectedCrypto.name,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )

                    Image(
                        painter = rememberImagePainter(data = selectedCrypto.logoUrl),
                        contentDescription = selectedCrypto.name,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(200.dp, 200.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                    )
                    Text(
                        text = price,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                }

                is Resource.Error -> {
                    Text(text = cryptoItem.message!!)
                }

                is Resource.Loading -> {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

