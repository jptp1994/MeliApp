/**
 * Data Transfer Objects for detailed product information from Mercado Libre API.
 * Includes ItemDetailDto, PictureDto, and AttributeDto for comprehensive item details.
 * Layer: Data
 * Usage: Used for deserializing JSON responses from item detail API calls.
 */
package com.example.meliapp.data.dto

data class ItemDetailDto(
    val id: String,
    val title: String,
    val price: Double,
    val currencyId: String,
    val pictures: List<PictureDto>,
    val condition: String,
    val availableQuantity: Int,
    val soldQuantity: Int,
    val warranty: String?,
    val attributes: List<AttributeDto>
)

data class PictureDto(
    val url: String
)

data class AttributeDto(
    val name: String,
    val valueName: String
)