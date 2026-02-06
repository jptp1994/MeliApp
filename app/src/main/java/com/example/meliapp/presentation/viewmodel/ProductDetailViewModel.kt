package com.example.meliapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meliapp.domain.model.Product
import com.example.meliapp.domain.usecase.GetProductDetailUseCase
import com.example.meliapp.domain.usecase.ToggleFavoriteUseCase
import com.example.meliapp.presentation.ui.common.UiState
import com.example.meliapp.utils.Constants.tag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val itemId: String = checkNotNull(savedStateHandle["itemId"])

    private val _uiState = MutableStateFlow<UiState<com.example.meliapp.domain.model.ProductDetail>>(UiState.Loading)
    val uiState: StateFlow<UiState<com.example.meliapp.domain.model.ProductDetail>> = _uiState

    init {
        loadProductDetail()
    }

    /**
     * input: None (uses itemId from SavedStateHandle)
     * output: Unit
     * utility: Loads the details for the product ID provided in the navigation arguments, updating the UI state accordingly.
     */
    private fun loadProductDetail() {
        viewModelScope.launch {
            try {
                val productDetail = getProductDetailUseCase(itemId)
                android.util.Log.d(tag, "Detail success for $itemId")
                _uiState.value = UiState.Success(productDetail)
            } catch (e: Exception) {
                android.util.Log.e(tag, "Detail unexpected error: ${e.message}")
                _uiState.value = UiState.Error(e.message ?: "Unexpected error")
            }
        }
    }

    fun toggleFavorite() {
        val currentState = _uiState.value
        if (currentState is UiState.Success) {
            val detail = currentState.data
            viewModelScope.launch {
                val product = Product(
                    id = detail.id,
                    title = detail.title,
                    price = detail.price,
                    currency = detail.currency,
                    images = detail.pictures,
                    condition = detail.condition,
                    availableQuantity = detail.availableQuantity,
                    brand = "",
                    status = "",
                    category = "",
                    quality = "",
                    priority = "",
                    createdAt = "",
                    lastUpdated = "",
                    keywords = "",
                    hasVariations = false,
                    isFavorite = detail.isFavorite
                )
                toggleFavoriteUseCase(product)
                _uiState.value = UiState.Success(detail.copy(isFavorite = !detail.isFavorite))
            }
        }
    }
}
