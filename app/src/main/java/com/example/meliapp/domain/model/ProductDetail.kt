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
    val attributes: List<Attribute>
)

data class Attribute(
    val name: String,
    val value: String
)