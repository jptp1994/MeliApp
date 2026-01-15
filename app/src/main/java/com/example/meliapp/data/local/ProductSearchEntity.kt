package com.example.meliapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_search")
data class ProductSearchEntity(
    @PrimaryKey
    val query: String,
    val itemsJson: String,
    val createdAt: Long
)

