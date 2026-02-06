package com.example.meliapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meliapp.domain.model.ProductSearchResult
import com.example.meliapp.domain.model.SearchSource
import com.example.meliapp.domain.usecase.SearchProductsUseCase
import com.example.meliapp.presentation.ui.common.UiState
import com.example.meliapp.utils.Constants.tag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

import com.example.meliapp.domain.model.Product
import com.example.meliapp.domain.usecase.GetFavoriteProductsUseCase
import com.example.meliapp.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val getFavoriteProductsUseCase: GetFavoriteProductsUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<ProductSearchResult>>(UiState.Empty)
    val uiState: StateFlow<UiState<ProductSearchResult>> = _uiState

    private val _isFavoriteFilterActive = MutableStateFlow(false)
    val isFavoriteFilterActive: StateFlow<Boolean> = _isFavoriteFilterActive.asStateFlow()

    private var searchJob: Job? = null
    private var favoritesJob: Job? = null
    private var lastSearchResult: ProductSearchResult? = null

    /**
     * input: query (String)
     * output: Unit
     * utility: Initiates the product search process, updating the UI state to Loading, then Success or Error based on the result.
     */
    fun searchProducts(query: String) {
        if (query.isBlank()) {
            _uiState.value = UiState.Empty
            return
        }

        // Disable favorite filter when searching
        if (_isFavoriteFilterActive.value) {
            _isFavoriteFilterActive.value = false
            stopFavoritesObservation()
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val result = searchProductsUseCase(query)
                lastSearchResult = result
                _uiState.value =
                    if (result.products.isEmpty()) UiState.Empty else UiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unexpected error")
            }
        }
    }

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            toggleFavoriteUseCase(product)
            // Update local state if not in favorite filter mode (favorites flow updates automatically)
            if (!_isFavoriteFilterActive.value) {
                val currentState = _uiState.value
                if (currentState is UiState.Success) {
                    val updatedProducts = currentState.data.products.map {
                        if (it.id == product.id) it.copy(isFavorite = !it.isFavorite) else it
                    }
                    val updatedResult = currentState.data.copy(products = updatedProducts)
                    lastSearchResult = updatedResult
                    _uiState.value = UiState.Success(updatedResult)
                }
            }
        }
    }

    fun toggleFavoriteFilter() {
        val isActive = !_isFavoriteFilterActive.value
        _isFavoriteFilterActive.value = isActive
        
        if (isActive) {
            startFavoritesObservation()
        } else {
            stopFavoritesObservation()
            // Restore last search result
            _uiState.value = lastSearchResult?.let { UiState.Success(it) } ?: UiState.Empty
        }
    }

    private fun startFavoritesObservation() {
        favoritesJob?.cancel()
        favoritesJob = viewModelScope.launch {
            getFavoriteProductsUseCase().collectLatest { favorites ->
                val result = ProductSearchResult(
                    products = favorites,
                    source = SearchSource.CACHE
                )
                _uiState.value = if (favorites.isEmpty()) UiState.Empty else UiState.Success(result)
            }
        }
    }

    private fun stopFavoritesObservation() {
        favoritesJob?.cancel()
    }
}
