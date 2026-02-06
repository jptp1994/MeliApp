package com.example.meliapp.domain.mapper

import com.example.meliapp.data.dto.ItemDto
import com.example.meliapp.domain.model.Product

fun ItemDto.toDomain(isFavorite: Boolean = false): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        currency = currencyID,
        images = images,
        condition = condition,
        availableQuantity = availableQuantity,
        brand = brand,
        status = status,
        category = category,
        quality = quality,
        priority = priority,
        createdAt = createdAt,
        lastUpdated = lastUpdated,
        keywords = keywords,
        hasVariations = hasVariations,
        isFavorite = isFavorite
    )
}

fun List<ItemDto>.toDomain(favoriteIds: List<String> = emptyList()): List<Product> {
    return map { it.toDomain(isFavorite = favoriteIds.contains(it.id)) }
}