package com.example.meliapp.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.meliapp.domain.model.Attribute
import com.example.meliapp.domain.model.ProductDetail
import com.example.meliapp.presentation.ui.common.UiState
import com.example.meliapp.presentation.viewmodel.ProductDetailViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is UiState.Success -> {
            val productDetail = (uiState as UiState.Success<ProductDetail>).data
            ProductDetailContent(productDetail = productDetail)
        }
        is UiState.Error -> {
            val message = (uiState as UiState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = message)
            }
        }
        is UiState.Empty -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Producto no encontrado")
            }
        }
    }
}

@Composable
fun ProductDetailContent(productDetail: ProductDetail) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            if (productDetail.pictures.isNotEmpty()) {
                AsyncImage(
                    model = productDetail.pictures.first().replace("http://", "https://"),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
            }
            Text(
                text = productDetail.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "${productDetail.currency} ${productDetail.price}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Condición: ${productDetail.condition}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Disponible: ${productDetail.availableQuantity}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Vendidos: ${productDetail.soldQuantity}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            productDetail.warranty?.let {
                Text(
                    text = "Garantía: $it",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }

        if (productDetail.attributes.isNotEmpty()) {
            item {
                Text(
                    text = "Atributos",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(productDetail.attributes) { attribute ->
                AttributeItem(attribute = attribute)
            }
        }
    }
}

@Composable
fun AttributeItem(attribute: Attribute) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "${attribute.name}:",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = attribute.value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
