package com.example.meliapp.domain.model

data class Product(
    val id: String,
    val title: String,
    val price: Double,
    val currency: String,
    val thumbnail: String,
    val condition: String,
    val availableQuantity: Int
)