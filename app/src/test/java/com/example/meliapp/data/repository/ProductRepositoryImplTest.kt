package com.example.meliapp.data.repository

import com.example.meliapp.data.datasource.FakeProductLocalDataSource
import com.example.meliapp.data.datasource.FakeProductRemoteDataSource
import com.example.meliapp.data.dto.ItemDetailDto
import com.example.meliapp.data.dto.ItemDto
import com.example.meliapp.domain.model.SearchSource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductRepositoryImplTest {

    private val fakeRemoteDataSource = FakeProductRemoteDataSource()
    private val fakeLocalDataSource = FakeProductLocalDataSource()
    private val repository = ProductRepositoryImpl(fakeRemoteDataSource, fakeLocalDataSource)

    @Test
    fun `searchProducts returns list of ItemDto on success with remote source`() = runTest {
        // Given
        val itemDto = ItemDto("1", "Product 1", 100.0, "ARS", listOf("url1"), "new", 10, "B", "S", "C", "Q", "P", "D1", "D2", "K", false)
        fakeRemoteDataSource.addProduct(itemDto)

        // When
        val result = repository.searchProducts("Product", 20, 0)

        // Then
        assertEquals(listOf(itemDto), result.items)
        assertEquals(SearchSource.REMOTE, result.source)
    }

    @Test
    fun `getItemDetail returns ItemDetailDto on success`() = runTest {
        // Given
        val itemDetail = ItemDetailDto("1", "Product 1", 100.0, "ARS", emptyList(), "new", 10, 5, null, emptyList())
        fakeRemoteDataSource.addProductDetail("1", itemDetail)

        // When
        val result = repository.getItemDetail("1")

        // Then
        assertEquals(itemDetail, result)
    }

    @Test
    fun `searchProducts returns cache when remote is empty but cache has data`() = runTest {
        // Given
        val cachedItem = ItemDto("2", "Cached Product", 50.0, "ARS", listOf("url2"), "used", 5, "B2", "S2", "C2", "Q2", "P2", "D3", "D4", "K2", false)
        fakeLocalDataSource.saveSearch("Cached", listOf(cachedItem))

        // When
        val result = repository.searchProducts("Cached", 20, 0)

        // Then
        assertEquals(listOf(cachedItem), result.items)
        assertEquals(SearchSource.CACHE, result.source)
    }

    @Test
    fun `searchProducts returns mock when remote and cache are empty`() = runTest {
        // Given - no remote products and no cache

        // When
        val result = repository.searchProducts("NoMatch", 20, 0)

        // Then
        assertEquals(com.example.meliapp.data.datasource.MockData.searchResults, result.items)
        assertEquals(SearchSource.MOCK, result.source)
    }
}
