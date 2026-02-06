package com.example.meliapp.domain.usecase

import com.example.meliapp.data.repository.FakeProductRepository
import com.example.meliapp.domain.model.Product
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ToggleFavoriteUseCaseTest {

    private val fakeRepository = FakeProductRepository()
    private val toggleFavoriteUseCase = ToggleFavoriteUseCase(fakeRepository)

    @Test
    fun `invoke toggles favorite status`() = runTest {
        // Given
        val product = Product(
            id = "1",
            title = "Test Product",
            price = 100.0,
            currency = "ARS",
            images = emptyList(),
            condition = "new",
            availableQuantity = 1,
            brand = "Brand",
            status = "active",
            category = "cat",
            quality = "q",
            priority = "p",
            createdAt = "",
            lastUpdated = "",
            keywords = "",
            hasVariations = false,
            isFavorite = false
        )

        // When (Add to favorites)
        toggleFavoriteUseCase(product)

        // Then
        assertTrue(fakeRepository.isFavorite("1"))

        // When (Remove from favorites)
        toggleFavoriteUseCase(product)

        // Then
        assertFalse(fakeRepository.isFavorite("1"))
    }
}