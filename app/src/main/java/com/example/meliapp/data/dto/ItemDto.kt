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
    val thumbnail: String,
    val condition: String,
    val availableQuantity: Int
)