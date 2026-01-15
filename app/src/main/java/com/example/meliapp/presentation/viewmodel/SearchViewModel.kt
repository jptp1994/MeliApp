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

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<ProductSearchResult>>(UiState.Empty)
    val uiState: StateFlow<UiState<ProductSearchResult>> = _uiState

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

        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val result = searchProductsUseCase(query)
                // android.util.Log.d(tag, "Search success: ${result.products.size} items found from ${result.source}")
                _uiState.value =
                    if (result.products.isEmpty()) UiState.Empty else UiState.Success(result)
            } catch (e: Exception) {
                // android.util.Log.e(tag, "Search unexpected error: ${e.message}")
                _uiState.value = UiState.Error(e.message ?: "Unexpected error")
            }
        }
    }
}
