package com.example.meliapp.data.repository

import com.example.meliapp.data.datasource.FakeProductRemoteDataSource
import com.example.meliapp.data.dto.ItemDetailDto
import com.example.meliapp.data.dto.ItemDto
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductRepositoryImplTest {

    private val fakeDataSource = FakeProductRemoteDataSource()
    private val repository = ProductRepositoryImpl(fakeDataSource)

    @Test
    fun `searchProducts returns list of ItemDto on success`() = runTest {
        // Given
        val itemDto = ItemDto("1", "Product 1", 100.0, "ARS", "url1", "new", 10)
        fakeDataSource.addProduct(itemDto)

        // When
        val result = repository.searchProducts("Product", 20, 0)

        // Then
        assertEquals(listOf(itemDto), result)
    }

    @Test
    fun `getItemDetail returns ItemDetailDto on success`() = runTest {
        // Given
        val itemDetail = ItemDetailDto("1", "Product 1", 100.0, "ARS", emptyList(), "new", 10, 5, null, emptyList())
        fakeDataSource.addProductDetail("1", itemDetail)

        // When
        val result = repository.getItemDetail("1")

        // Then
        assertEquals(itemDetail, result)
    }
}