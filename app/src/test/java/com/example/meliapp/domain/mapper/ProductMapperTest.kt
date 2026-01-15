package com.example.meliapp.domain.mapper

import com.example.meliapp.data.dto.ItemDto
import com.example.meliapp.domain.model.Product
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductMapperTest {

    @Test
    fun `ItemDto toDomain maps correctly`() {
        // Given
        val itemDto = ItemDto(
            id = "1",
            title = "Test Product",
            price = 100.0,
            currencyID = "ARS",
            images = listOf("url"),
            condition = "new",
            availableQuantity = 10,
            brand = "Test Brand",
            status = "active",
            category = "cat1",
            quality = "good",
            priority = "high",
            createdAt = "2023-01-01",
            lastUpdated = "2023-01-02",
            keywords = "test",
            hasVariations = false
        )

        // When
        val product = itemDto.toDomain()

        // Then
        val expected = Product(
            id = "1",
            title = "Test Product",
            price = 100.0,
            currency = "ARS",
            images = listOf("url"),
            condition = "new",
            availableQuantity = 10,
            brand = "Test Brand",
            status = "active",
            category = "cat1",
            quality = "good",
            priority = "high",
            createdAt = "2023-01-01",
            lastUpdated = "2023-01-02",
            keywords = "test",
            hasVariations = false
        )
        assertEquals(expected, product)
    }

    @Test
    fun `list of ItemDto toDomain maps correctly`() {
        // Given
        val itemDtos = listOf(
            ItemDto("1", "P1", 100.0, "ARS", listOf("u1"), "new", 10, "B1", "S1", "C1", "Q1", "PR1", "D1", "D2", "K1", false),
            ItemDto("2", "P2", 200.0, "ARS", listOf("u2"), "used", 5, "B2", "S2", "C2", "Q2", "PR2", "D3", "D4", "K2", true)
        )

        // When
        val products = itemDtos.toDomain()

        // Then
        val expected = listOf(
            Product("1", "P1", 100.0, "ARS", listOf("u1"), "new", 10, "B1", "S1", "C1", "Q1", "PR1", "D1", "D2", "K1", false),
            Product("2", "P2", 200.0, "ARS", listOf("u2"), "used", 5, "B2", "S2", "C2", "Q2", "PR2", "D3", "D4", "K2", true)
        )
        assertEquals(expected, products)
    }
}