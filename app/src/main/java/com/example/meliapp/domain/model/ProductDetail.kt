/**
 * Domain model for detailed product information including attributes and specifications.
 * Extends basic product data with additional details for detailed view.
 * Layer: Domain
 * Usage: Used in product detail screens and business logic requiring full product information.
 */
package com.example.meliapp.domain.model

data class ProductDetail(
    val id: String,
    val title: String,
    val price: Double,
    val currency: String,
    val pictures: List<String>,
    val condition: String,
    val availableQuantity: Int,
    val soldQuantity: Int,
    val warranty: String?,
    val attributes: List<Attribute>,
    val isFavorite: Boolean = false
)

data class Attribute(
    val name: String,
    val value: String
)