package com.example.meliapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.meliapp.domain.model.Product
import com.example.meliapp.domain.model.ProductSearchResult
import com.example.meliapp.domain.model.SearchSource
import com.example.meliapp.presentation.ui.common.UiState
import com.example.meliapp.presentation.ui.screen.SearchScreen
import com.example.meliapp.presentation.viewmodel.SearchViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel: SearchViewModel = mockk(relaxed = true)
    private val uiStateFlow = MutableStateFlow<UiState<ProductSearchResult>>(UiState.Empty)

    @Test
    fun searchScreen_displaysSearchField_and_EmptyState() {
        every { viewModel.uiState } returns uiStateFlow

        composeTestRule.setContent {
            SearchScreen(onProductClick = {}, viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Buscar productos").assertIsDisplayed()
        composeTestRule.onNodeWithText("Empieza a buscar tus productos favoritos").assertIsDisplayed()
    }

    @Test
    fun searchScreen_displaysLoading() {
        uiStateFlow.value = UiState.Loading
        every { viewModel.uiState } returns uiStateFlow

        composeTestRule.setContent {
            SearchScreen(onProductClick = {}, viewModel = viewModel)
        }

        // We can't easily find the progress indicator without a test tag, 
        // but we can verify the empty message is gone.
        composeTestRule.onNodeWithText("Empieza a buscar tus productos favoritos").assertDoesNotExist()
    }

    @Test
    fun searchScreen_displaysError() {
        val errorMessage = "Error de conexión"
        uiStateFlow.value = UiState.Error(errorMessage)
        every { viewModel.uiState } returns uiStateFlow

        composeTestRule.setContent {
            SearchScreen(onProductClick = {}, viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("¡Ups! Algo salió mal").assertIsDisplayed()
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun searchScreen_displaysProductList() {
        val products = listOf(
            Product(
                id = "1",
                title = "Producto de prueba",
                price = 1000.0,
                currency = "ARS",
                images = listOf("http://imagen.com/img.jpg"),
                condition = "new",
                availableQuantity = 10,
                brand = "Marca Test",
                status = "active",
                category = "MLA123",
                quality = "gold",
                priority = "high",
                createdAt = "2023-01-01",
                lastUpdated = "2023-01-02",
                keywords = "test",
                hasVariations = false
            )
        )
        val result = ProductSearchResult(products = products, source = SearchSource.REMOTE)
        uiStateFlow.value = UiState.Success(result)
        every { viewModel.uiState } returns uiStateFlow

        composeTestRule.setContent {
            SearchScreen(onProductClick = {}, viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Producto de prueba").assertIsDisplayed()
        composeTestRule.onNodeWithText("Marca Test").assertIsDisplayed()
    }

    @Test
    fun searchScreen_displaysNoProductsMessage_whenListIsEmpty() {
        val result = ProductSearchResult(products = emptyList(), source = SearchSource.REMOTE)
        uiStateFlow.value = UiState.Success(result)
        every { viewModel.uiState } returns uiStateFlow

        composeTestRule.setContent {
            SearchScreen(onProductClick = {}, viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("No hay productos para mostrar").assertIsDisplayed()
    }
}