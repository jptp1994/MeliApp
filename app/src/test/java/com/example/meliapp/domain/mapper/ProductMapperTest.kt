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
            thumbnail = "url",
            condition = "new",
            availableQuantity = 10
        )

        // When
        val product = itemDto.toDomain()

        // Then
        val expected = Product(
            id = "1",
            title = "Test Product",
            price = 100.0,
            currency = "ARS",
            thumbnail = "url",
            condition = "new",
            availableQuantity = 10
        )
        assertEquals(expected, product)
    }

    @Test
    fun `list of ItemDto toDomain maps correctly`() {
        // Given
        val itemDtos = listOf(
            ItemDto("1", "P1", 100.0, "ARS", "u1", "new", 10),
            ItemDto("2", "P2", 200.0, "ARS", "u2", "used", 5)
        )

        // When
        val products = itemDtos.toDomain()

        // Then
        val expected = listOf(
            Product("1", "P1", 100.0, "ARS", "u1", "new", 10),
            Product("2", "P2", 200.0, "ARS", "u2", "used", 5)
        )
        assertEquals(expected, products)
    }
}