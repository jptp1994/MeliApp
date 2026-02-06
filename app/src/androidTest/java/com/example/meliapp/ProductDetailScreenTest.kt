package com.example.meliapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.meliapp.domain.model.Attribute
import com.example.meliapp.domain.model.ProductDetail
import com.example.meliapp.presentation.ui.common.UiState
import com.example.meliapp.presentation.ui.screen.ProductDetailScreen
import com.example.meliapp.presentation.viewmodel.ProductDetailViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class ProductDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel: ProductDetailViewModel = mockk(relaxed = true)
    private val uiStateFlow = MutableStateFlow<UiState<ProductDetail>>(UiState.Loading)

    @Test
    fun productDetailScreen_displaysLoading() {
        uiStateFlow.value = UiState.Loading
        every { viewModel.uiState } returns uiStateFlow

        composeTestRule.setContent {
            ProductDetailScreen(onBackClick = {}, viewModel = viewModel)
        }

        // Verify "Comprar ahora" button is NOT visible during loading
        composeTestRule.onNodeWithText("Comprar ahora").assertDoesNotExist()
    }

    @Test
    fun productDetailScreen_displaysProductInfo() {
        val product = ProductDetail(
            id = "1",
            title = "Producto Detalle Test",
            price = 1500.0,
            currency = "ARS",
            pictures = listOf("http://imagen.com/img.jpg"),
            condition = "new",
            availableQuantity = 5,
            soldQuantity = 100,
            warranty = "Garantía 1 año",
            attributes = listOf(
                Attribute("Marca", "TestBrand"),
                Attribute("Modelo", "TestModel")
            )
        )

        uiStateFlow.value = UiState.Success(product)
        every { viewModel.uiState } returns uiStateFlow

        composeTestRule.setContent {
            ProductDetailScreen(onBackClick = {}, viewModel = viewModel)
        }

        // Check Title
        composeTestRule.onNodeWithText("Producto Detalle Test").assertIsDisplayed()
        
        // Check Price (formatted as "$ 1500.0" roughly, exact string depends on locale but code uses toPlainString)
        // Code: text = "$ ${productDetail.price.toBigDecimal().toPlainString()}" -> "$ 1500" or "$ 1500.0"
        composeTestRule.onNodeWithText("$ 1500.0").assertIsDisplayed()

        // Check Warranty
        composeTestRule.onNodeWithText("Garantía").assertIsDisplayed()
        composeTestRule.onNodeWithText("Garantía 1 año").assertIsDisplayed()

        // Check Attributes
        composeTestRule.onNodeWithText("Marca").assertIsDisplayed()
        composeTestRule.onNodeWithText("TestBrand").assertIsDisplayed()
        
        // Check Button
        composeTestRule.onNodeWithText("Comprar ahora").assertIsDisplayed()

        // Check Favorite Icon
        composeTestRule.onNodeWithContentDescription("Add to favorites").assertIsDisplayed()
    }

    @Test
    fun productDetailScreen_displaysFavoriteIcon_checked() {
        val product = ProductDetail(
            id = "1",
            title = "Producto Favorito",
            price = 1500.0,
            currency = "ARS",
            pictures = listOf("http://imagen.com/img.jpg"),
            condition = "new",
            availableQuantity = 5,
            soldQuantity = 100,
            warranty = "Garantía",
            attributes = emptyList(),
            isFavorite = true
        )

        uiStateFlow.value = UiState.Success(product)
        every { viewModel.uiState } returns uiStateFlow

        composeTestRule.setContent {
            ProductDetailScreen(onBackClick = {}, viewModel = viewModel)
        }

        composeTestRule.onNodeWithContentDescription("Remove from favorites").assertIsDisplayed()
    }

    @Test
    fun productDetailScreen_displaysError() {
        val errorMessage = "Error al cargar producto"
        uiStateFlow.value = UiState.Error(errorMessage)
        every { viewModel.uiState } returns uiStateFlow

        composeTestRule.setContent {
            ProductDetailScreen(onBackClick = {}, viewModel = viewModel)
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }
    
    @Test
    fun productDetailScreen_displaysEmpty() {
        uiStateFlow.value = UiState.Empty
        every { viewModel.uiState } returns uiStateFlow

        composeTestRule.setContent {
            ProductDetailScreen(onBackClick = {}, viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Producto no encontrado").assertIsDisplayed()
    }
}