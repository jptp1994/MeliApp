package com.example.meliapp.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.meliapp.R
import com.example.meliapp.domain.model.Product
import com.example.meliapp.domain.model.ProductSearchResult
import com.example.meliapp.domain.model.SearchSource
import com.example.meliapp.presentation.ui.common.UiState
import com.example.meliapp.presentation.ui.theme.Dimens
import com.example.meliapp.presentation.viewmodel.SearchViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun SearchScreen(
    onProductClick: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        snapshotFlow { query }
            .debounce(500)
            .distinctUntilChanged()
            .collect {
                if (it.isNotEmpty()) {
                    viewModel.searchProducts(it)
                }
            }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.app_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.dp_16),
                elevation = CardDefaults.cardElevation(defaultElevation = Dimens.dp_4),
                shape = RoundedCornerShape(Dimens.dp_24),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                TextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = { Text(stringResource(R.string.search_products_hint)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.surface
                    ),
                    singleLine = true
                )
            }

            when (uiState) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
                is UiState.Success -> {
                    val result = (uiState as UiState.Success<ProductSearchResult>).data
                    val products = result.products
                    if (products.isEmpty()) {
                        EmptyStateMessage(stringResource(R.string.no_products_message))
                    } else {
                        if (result.source == SearchSource.CACHE) {
                            LastSearchHeader()
                        }
                        LazyColumn(
                            contentPadding = PaddingValues(
                                horizontal = Dimens.dp_16,
                                vertical = Dimens.dp_8
                            ),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(products) { product ->
                                ProductItem(
                                    product = product,
                                    onClick = { onProductClick(product.id) },
                                    onFavoriteClick = { viewModel.toggleFavorite(product) }
                                )
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    val message = (uiState as UiState.Error).message
                    ErrorStateMessage(message)
                }
                is UiState.Empty -> {
                    EmptyStateMessage(stringResource(R.string.empty_search_message))
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.dp_8)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.dp_2),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(Dimens.dp_16)
    ) {
        Box {
            Column(modifier = Modifier.padding(Dimens.dp_16)) {
                // Gallery (Carousel)
                if (product.images.isNotEmpty()) {
                    androidx.compose.foundation.lazy.LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimens.dp_200),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Dimens.dp_8)
                    ) {
                        items(product.images) { imageUrl ->
                            AsyncImage(
                                model = imageUrl.replace("http://", "https://"),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(Dimens.dp_200)
                                    .clip(RoundedCornerShape(Dimens.dp_8)),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                } else {
                    Box(modifier = Modifier.height(Dimens.dp_200).fillMaxWidth().background(MaterialTheme.colorScheme.secondaryContainer))
                }

                Spacer(modifier = Modifier.height(Dimens.dp_16))

                // Title (H1)
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(Dimens.dp_8))

                // Badges
                androidx.compose.foundation.layout.Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (product.brand.isNotEmpty()) {
                        Badge(
                            text = product.brand,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.width(Dimens.dp_8))
                    }

                    val statusColor = if (product.status == "active") androidx.compose.ui.graphics.Color(0xFF4CAF50) else MaterialTheme.colorScheme.error
                    Badge(
                        text = product.status,
                        containerColor = statusColor.copy(alpha = 0.1f),
                        contentColor = statusColor
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.dp_16))

                // Info Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(Dimens.dp_8),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(Dimens.dp_12)) {
                        InfoRow(label = stringResource(R.string.label_category), value = product.category)
                        InfoRow(label = stringResource(R.string.label_quality), value = product.quality)
                        InfoRow(label = stringResource(R.string.label_priority), value = product.priority)
                        InfoRow(label = stringResource(R.string.label_created), value = product.createdAt)
                        InfoRow(label = stringResource(R.string.label_updated), value = product.lastUpdated)
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.dp_16))

                // Keywords Chips
                if (product.keywords.isNotEmpty()) {
                    val keywordsList = product.keywords.split(",").map { it.trim() }
                    androidx.compose.foundation.lazy.LazyRow(
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Dimens.dp_8)
                    ) {
                        items(keywordsList) { keyword ->
                            androidx.compose.material3.SuggestionChip(
                                onClick = { },
                                label = { Text(keyword) }
                            )
                        }
                    }
                }
            }
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.align(Alignment.TopEnd).padding(Dimens.dp_8)
            ) {
                Icon(
                    imageVector = if (product.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (product.isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = if (product.isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}

@Composable
fun LastSearchHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.dp_16, vertical = Dimens.dp_8)
    ) {
        Text(
            text = stringResource(R.string.last_search_from_cache),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun Badge(text: String, containerColor: androidx.compose.ui.graphics.Color, contentColor: androidx.compose.ui.graphics.Color) {
    Box(
        modifier = Modifier
            .background(containerColor, RoundedCornerShape(Dimens.dp_4))
            .padding(horizontal = Dimens.dp_8, vertical = Dimens.dp_4)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = contentColor
        )
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = Dimens.dp_4),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun EmptyStateMessage(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun ErrorStateMessage(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.generic_error_title),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
