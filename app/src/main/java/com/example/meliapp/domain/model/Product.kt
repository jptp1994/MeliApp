/**
 * Domain model representing a product in the Mercado Libre marketplace.
 * Contains essential information about a product for display and business logic.
 * Layer: Domain
 * Usage: Used across presentation and domain layers for product representation.
 */
package com.example.meliapp.domain.model

data class Product(
    val id: String,
    val title: String,
    val price: Double,
    val currency: String,
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