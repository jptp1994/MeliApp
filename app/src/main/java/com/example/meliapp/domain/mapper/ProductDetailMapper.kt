package com.example.meliapp.domain.mapper

import com.example.meliapp.data.dto.ItemDetailDto
import com.example.meliapp.domain.model.Attribute
import com.example.meliapp.domain.model.ProductDetail

fun ItemDetailDto.toDomain(isFavorite: Boolean = false): ProductDetail {
    return ProductDetail(
        id = id,
        title = title,
        price = price,
        currency = currencyId,
        pictures = pictures.map { it.url },
        condition = condition,
        availableQuantity = availableQuantity,
        soldQuantity = soldQuantity,
        warranty = warranty,
        attributes = attributes.map { Attribute(it.name, it.valueName) },
        isFavorite = isFavorite
    )
}