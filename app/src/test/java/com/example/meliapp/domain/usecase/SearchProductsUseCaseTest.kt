package com.example.meliapp.domain.usecase

import com.example.meliapp.data.repository.ProductRepository
import com.example.meliapp.domain.model.Product
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchProductsUseCaseTest {

    private val repository: ProductRepository = mockk()
    private val useCase = SearchProductsUseCase(repository)

    @Test
    fun `invoke returns mapped products from repository`() = runTest {
        // Given
        val mockProducts = listOf(
            Product("1", "Product 1", 100.0, "ARS", "url1", "new", 10),
            Product("2", "Product 2", 200.0, "ARS", "url2", "used", 5)
        )
        coEvery { repository.searchProducts("query", 20, 0) } returns mockProducts.map {
            com.example.meliapp.data.dto.ItemDto(
                id = it.id,
                title = it.title,
                price = it.price,
                currencyID = it.currency,
                thumbnail = it.thumbnail,
                condition = it.condition,
                availableQuantity = it.availableQuantity
            )
        }

        // When
        val result = useCase("query")

        // Then
        assertEquals(mockProducts, result)
    }
}