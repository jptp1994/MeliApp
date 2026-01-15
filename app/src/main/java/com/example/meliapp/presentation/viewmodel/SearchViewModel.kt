package com.example.meliapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meliapp.data.exception.NetworkException
import com.example.meliapp.domain.usecase.SearchProductsUseCase
import com.example.meliapp.presentation.ui.common.UiState
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
        MutableStateFlow<UiState<List<com.example.meliapp.domain.model.Product>>>(UiState.Empty)
    val uiState: StateFlow<UiState<List<com.example.meliapp.domain.model.Product>>> = _uiState

    fun searchProducts(query: String) {
        if (query.isBlank()) {
            _uiState.value = UiState.Empty
            return
        }

        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val products = searchProductsUseCase(query)
                _uiState.value = if (products.isEmpty()) UiState.Empty else UiState.Success(products)
            } catch (e: NetworkException) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Unexpected error")
            }
        }
    }
}
