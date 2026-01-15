/**
 * Data Transfer Object for product items from Mercado Libre API.
 * Represents the basic information of a product in search results.
 * Layer: Data
 * Usage: Used for deserializing JSON responses from search API calls.
 */
package com.example.meliapp.data.dto

data class ItemDto(
    val id: String,
    val title: String,
    val price: Double,
    val currencyID: String,
    val images: List<String>,
    val condition: String,
    val availableQuantity: Int,
    val brand: String,
    val status: String,
    val category: String,
    val quality: String,
    val priority: String,
    val createdAt: String,
    val lastUpdated: String,
    val keywords: String,
    val hasVariations: Boolean
)