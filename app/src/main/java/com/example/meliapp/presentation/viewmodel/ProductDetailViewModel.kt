package com.example.meliapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meliapp.data.exception.NetworkException
import com.example.meliapp.domain.usecase.GetProductDetailUseCase
import com.example.meliapp.presentation.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductDetailUseCase: GetProductDetailUseCase
) : ViewModel() {

    private val itemId: String = checkNotNull(savedStateHandle["itemId"])

    private val _uiState = MutableStateFlow<UiState<com.example.meliapp.domain.model.ProductDetail>>(UiState.Loading)
    val uiState: StateFlow<UiState<com.example.meliapp.domain.model.ProductDetail>> = _uiState

    init {
        loadProductDetail()
    }

    private fun loadProductDetail() {
        viewModelScope.launch {
            try {
                val productDetail = getProductDetailUseCase(itemId)
                _uiState.value = UiState.Success(productDetail)
            } catch (e: NetworkException) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Unexpected error")
            }
        }
    }
}
