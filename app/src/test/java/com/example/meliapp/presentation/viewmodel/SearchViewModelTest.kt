package com.example.meliapp.presentation.viewmodel

import app.cash.turbine.test
import com.example.meliapp.data.dto.ItemDto
import com.example.meliapp.data.repository.FakeProductRepository
import com.example.meliapp.domain.model.Product
import com.example.meliapp.domain.model.ProductSearchResult
import com.example.meliapp.domain.model.SearchSource
import com.example.meliapp.domain.usecase.SearchProductsUseCase
import com.example.meliapp.presentation.ui.common.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val fakeRepository = FakeProductRepository()
    private val searchProductsUseCase = SearchProductsUseCase(fakeRepository)
    private val viewModel = SearchViewModel(searchProductsUseCase)

    @Test
    fun `searchProducts with blank query sets Empty state`() = runTest(UnconfinedTestDispatcher()) {
        // When
        viewModel.searchProducts("")

        // Then
        viewModel.uiState.test {
            assertEquals(UiState.Empty, awaitItem())
        }
    }

    @Test
    fun `searchProducts with valid query and success sets Success state`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val itemDto = ItemDto("1", "Product 1", 100.0, "ARS", listOf("url"), "new", 10, "B", "S", "C", "Q", "P", "D1", "D2", "K", false)
        fakeRepository.addProduct(itemDto)
        val expectedProduct = Product("1", "Product 1", 100.0, "ARS", listOf("url"), "new", 10, "B", "S", "C", "Q", "P", "D1", "D2", "K", false)

        // When / Then
        viewModel.uiState.test {
            // Valor inicial
            val initialState = awaitItem()
            assertEquals(UiState.Empty, initialState)

            viewModel.searchProducts("Product")

            // Expect Success (Loading might be skipped due to Unconfined)
            val resultState = awaitItem()
            
            // If we caught Loading, await next for Success
            val finalState = if (resultState is UiState.Loading) awaitItem() else resultState

            val successState = finalState as UiState.Success<ProductSearchResult>
            assertEquals(listOf(expectedProduct), successState.data.products)
            assertEquals(SearchSource.REMOTE, successState.data.source)
        }
    }

    @Test
    fun `searchProducts with empty results sets Empty state`() = runTest(UnconfinedTestDispatcher()) {
        // Given - no products added

        // When / Then
        viewModel.uiState.test {
            // Valor inicial
            assertEquals(UiState.Empty, awaitItem())

            viewModel.searchProducts("query")

            // Expect Empty (Loading might be skipped)
            val resultState = awaitItem()
            val finalState = if (resultState is UiState.Loading) awaitItem() else resultState
            
            assertEquals(UiState.Empty, finalState)
        }
    }
}
