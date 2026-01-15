package com.example.meliapp.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.meliapp.R
import com.example.meliapp.domain.model.Product
import com.example.meliapp.presentation.ui.common.UiState
import com.example.meliapp.presentation.ui.theme.Dimens
import com.example.meliapp.presentation.viewmodel.SearchViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(FlowPreview::class)
@Composable
fun SearchScreen(
    onProductClick: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var query by remember { mutableStateOf("") }

    LaunchedEffect(query) {
        snapshotFlow { query }
            .debounce(300)
            .distinctUntilChanged()
            .collect { viewModel.searchProducts(it) }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text(stringResource(R.string.search_products_hint)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.PaddingMedium)
        )

        when (uiState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                val products = (uiState as UiState.Success<List<Product>>).data
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(products) { product ->
                        ProductItem(product = product, onClick = { onProductClick(product.id) })
                    }
                }
            }
            is UiState.Error -> {
                val message = (uiState as UiState.Error).message
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = message)
                }
            }
            is UiState.Empty -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = stringResource(R.string.no_products_message))
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.PaddingSmall),
        onClick = onClick
    ) {
        Row(modifier = Modifier.padding(Dimens.CardPadding)) {
            AsyncImage(
                model = product.thumbnail.replace("http://", "https://"),
                contentDescription = null,
                modifier = Modifier.size(Dimens.ImageSize)
            )
            Column(modifier = Modifier.padding(start = Dimens.PaddingMedium)) {
                Text(text = product.title, style = MaterialTheme.typography.titleMedium)
                Text(text = "${product.currency} ${product.price}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "${stringResource(R.string.condition_label)} ${product.condition}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}