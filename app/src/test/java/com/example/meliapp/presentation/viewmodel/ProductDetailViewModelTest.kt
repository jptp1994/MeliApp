package com.example.meliapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.meliapp.data.dto.ItemDetailDto
import com.example.meliapp.data.repository.FakeProductRepository
import com.example.meliapp.domain.model.ProductDetail
import com.example.meliapp.domain.usecase.GetProductDetailUseCase
import com.example.meliapp.presentation.ui.common.UiState
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest {

    private val fakeRepository = FakeProductRepository()
    private val getProductDetailUseCase = GetProductDetailUseCase(fakeRepository)

    @Before
    fun setup() {
        mockkStatic(android.util.Log::class)
        every { android.util.Log.d(any(), any()) } returns 0
        every { android.util.Log.e(any(), any()) } returns 0
    }

    @Test
    fun `loadProductDetail with valid id sets Success state`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            // Given
            val itemId = "1"
            val itemDetailDto = ItemDetailDto(
                id = itemId,
                title = "Product Detail",
                price = 150.0,
                currencyId = "ARS",
                pictures = emptyList(),
                condition = "new",
                availableQuantity = 10,
                soldQuantity = 5,
                warranty = "1 year",
                attributes = emptyList()
            )
            fakeRepository.addProductDetail(itemId, itemDetailDto)
            
            val savedStateHandle = SavedStateHandle(mapOf("itemId" to itemId))
            
            // When
            val viewModel = ProductDetailViewModel(savedStateHandle, getProductDetailUseCase)
            
            // Execute pending coroutines
            advanceUntilIdle()

            // Then
            val state = viewModel.uiState.value
            assertTrue("State should be Success but was $state", state is UiState.Success)
            val successState = state as UiState.Success<ProductDetail>
            assertEquals(itemId, successState.data.id)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `loadProductDetail with invalid id sets Error state`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            // Given
            val itemId = "invalid"
            // Do not add to repository
            
            val savedStateHandle = SavedStateHandle(mapOf("itemId" to itemId))
            
            // When
            val viewModel = ProductDetailViewModel(savedStateHandle, getProductDetailUseCase)
            
            // Execute pending coroutines
            advanceUntilIdle()

            // Then
            val state = viewModel.uiState.value
            assertTrue("State should be Error but was $state", state is UiState.Error)
            val errorState = state as UiState.Error
            assertEquals("Product not found", errorState.message)
        } finally {
            Dispatchers.resetMain()
        }
    }
}