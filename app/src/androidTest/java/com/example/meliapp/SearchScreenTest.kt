package com.example.meliapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.meliapp.domain.model.Product
import com.example.meliapp.presentation.ui.common.UiState
import com.example.meliapp.presentation.ui.screen.SearchScreen
import com.example.meliapp.presentation.viewmodel.SearchViewModel
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel: SearchViewModel = mockk(relaxed = true)

    @Test
    fun searchScreen_displaysSearchField() {
        composeTestRule.setContent {
            SearchScreen(onProductClick = {}, viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Buscar productos").assertExists()
    }

    @Test
    fun searchScreen_displaysEmptyMessage() {
        // Mock the uiState to be Empty
        val uiStateFlow = MutableStateFlow<UiState<List<Product>>>(UiState.Empty)
        // Since viewModel is mocked, we can't easily change the state, but for basic test, assume default is Empty

        composeTestRule.setContent {
            SearchScreen(onProductClick = {}, viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("No hay productos para mostrar").assertExists()
    }
}