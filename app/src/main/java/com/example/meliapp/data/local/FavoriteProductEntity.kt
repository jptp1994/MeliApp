package com.example.meliapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class FavoriteProductEntity(
    @PrimaryKey val productId: String,
    val title: String,
    val price: Double,
    val imageUrl: String,
    val timestamp: Long = System.currentTimeMillis()
)
