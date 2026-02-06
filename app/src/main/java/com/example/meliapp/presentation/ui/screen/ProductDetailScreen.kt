package com.example.meliapp.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.ui.graphics.Color
import com.example.meliapp.domain.model.ProductDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.app_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.generic_error_button_back),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (uiState) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
                is UiState.Success -> {
                    val productDetail = (uiState as UiState.Success<ProductDetail>).data
                    ProductDetailContent(
                        productDetail = productDetail,
                        onFavoriteClick = { viewModel.toggleFavorite() }
                    )
                }
                is UiState.Error -> {
                    val message = (uiState as UiState.Error).message
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = message, color = MaterialTheme.colorScheme.error)
                    }
                }
                is UiState.Empty -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = stringResource(R.string.product_not_found))
                    }
                }
            }
        }
    }
}

@Composable
fun ProductDetailContent(
    productDetail: ProductDetail,
    onFavoriteClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.dp_16)
    ) {
        item {
            Text(
                text = "${if (productDetail.condition == "new") stringResource(R.string.product_condition_new) else stringResource(R.string.product_condition_used)}  |  ${productDetail.soldQuantity} vendidos",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = Dimens.dp_8)
            )
        }

        item {
            Text(
                text = productDetail.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = Dimens.dp_16)
            )
        }

        item {
            if (productDetail.pictures.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.dp_300)
                        .padding(vertical = Dimens.dp_16),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = productDetail.pictures.first().replace("http://", "https://"),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                    IconButton(
                        onClick = onFavoriteClick,
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                         Icon(
                             imageVector = if (productDetail.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                             contentDescription = if (productDetail.isFavorite) "Remove from favorites" else "Add to favorites",
                             tint = if (productDetail.isFavorite) Color.Red else Color.Gray
                         )
                    }
                }
            }
        }

        item {
            Text(
                text = "$ ${productDetail.price.toBigDecimal().toPlainString()}",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = Dimens.dp_24)
            )
        }

        item {
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp_48),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(Dimens.dp_6)
            ) {
                Text(
                    text = stringResource(R.string.product_buy_now),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.height(Dimens.dp_24))
        }

        item {
            productDetail.warranty?.let {
                Text(
                    text = stringResource(R.string.product_warranty_title),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = Dimens.dp_8)
                )
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = Dimens.dp_24)
                )
            }
        }

        if (productDetail.attributes.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.product_main_features),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = Dimens.dp_16)
                )
            }
            items(productDetail.attributes.take(10)) { attribute ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(Dimens.dp_8)
                ) {
                    Text(
                        text = attribute.name,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = attribute.value,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
