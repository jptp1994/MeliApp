package com.example.meliapp.presentation.viewmodel

import app.cash.turbine.test
import com.example.meliapp.data.dto.ItemDto
import com.example.meliapp.data.repository.FakeProductRepository
import com.example.meliapp.domain.model.Product
import com.example.meliapp.domain.usecase.SearchProductsUseCase
import com.example.meliapp.presentation.ui.common.UiState
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchViewModelTest {

    private val fakeRepository = FakeProductRepository()
    private val searchProductsUseCase = SearchProductsUseCase(fakeRepository)
    private val viewModel = SearchViewModel(searchProductsUseCase)

    @Test
    fun `searchProducts with blank query sets Empty state`() = runTest {
        // When
        viewModel.searchProducts("")

        // Then
        viewModel.uiState.test {
            assertEquals(UiState.Empty, awaitItem())
        }
    }

    @Test
    fun `searchProducts with valid query and success sets Success state`() = runTest {
        // Given
        val itemDto = ItemDto("1", "Product 1", 100.0, "ARS", "url", "new", 10)
        fakeRepository.addProduct(itemDto)
        val expectedProduct = Product("1", "Product 1", 100.0, "ARS", "url", "new", 10)

        // When
        viewModel.searchProducts("Product")

        // Then
        viewModel.uiState.test {
            assertEquals(UiState.Loading, awaitItem())
            assertEquals(UiState.Success(listOf(expectedProduct)), awaitItem())
        }
    }

    @Test
    fun `searchProducts with empty results sets Empty state`() = runTest {
        // Given - no products added

        // When
        viewModel.searchProducts("query")

        // Then
        viewModel.uiState.test {
            assertEquals(UiState.Loading, awaitItem())
            assertEquals(UiState.Empty, awaitItem())
        }
    }
}