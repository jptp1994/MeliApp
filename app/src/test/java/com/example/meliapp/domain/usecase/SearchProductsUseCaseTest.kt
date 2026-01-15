package com.example.meliapp.domain.usecase

import com.example.meliapp.data.repository.ProductRepository
import com.example.meliapp.data.repository.SearchItemsResult
import com.example.meliapp.domain.model.Product
import com.example.meliapp.domain.model.SearchSource
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
            Product("1", "Product 1", 100.0, "ARS", listOf("url1"), "new", 10, "B", "S", "C", "Q", "P", "D1", "D2", "K", false),
            Product("2", "Product 2", 200.0, "ARS", listOf("url2"), "used", 5, "B", "S", "C", "Q", "P", "D1", "D2", "K", false)
        )
        coEvery { repository.searchProducts("query", 20, 0) } returns SearchItemsResult(
            items = mockProducts.map {
                com.example.meliapp.data.dto.ItemDto(
                    id = it.id,
                    title = it.title,
                    price = it.price,
                    currencyID = it.currency,
                    images = it.images,
                    condition = it.condition,
                    availableQuantity = it.availableQuantity,
                    brand = it.brand,
                    status = it.status,
                    category = it.category,
                    quality = it.quality,
                    priority = it.priority,
                    createdAt = it.createdAt,
                    lastUpdated = it.lastUpdated,
                    keywords = it.keywords,
                    hasVariations = it.hasVariations
                )
            },
            source = SearchSource.REMOTE
        )

        // When
        val result = useCase("query")

        // Then
        assertEquals(mockProducts, result.products)
    }
}
