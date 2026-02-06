package com.example.meliapp.data.repository

import com.example.meliapp.data.dto.ItemDetailDto
import com.example.meliapp.domain.model.ProductSearchResult
import kotlinx.coroutines.flow.Flow

import com.example.meliapp.domain.model.Product

interface ProductRepository {
    // ... (keep existing)
    suspend fun searchProducts(query: String, limit: Int, offset: Int): ProductSearchResult
    suspend fun getItemDetail(itemId: String): ItemDetailDto
    
    suspend fun toggleFavorite(product: Product)
    suspend fun isFavorite(productId: String): Boolean
    fun getFavoriteProducts(): Flow<List<Product>>
}
