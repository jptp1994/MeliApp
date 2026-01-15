package com.example.meliapp.domain.mapper

import com.example.meliapp.data.dto.ItemDto
import com.example.meliapp.domain.model.Product

fun ItemDto.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        currency = currencyID,
        thumbnail = thumbnail,
        condition = condition,
        availableQuantity = availableQuantity
    )
}

fun List<ItemDto>.toDomain(): List<Product> {
    return map { it.toDomain() }
}