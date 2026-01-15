package com.example.meliapp.data.repository

import com.example.meliapp.data.dto.ItemDetailDto
import com.example.meliapp.data.dto.ItemDto

interface ProductRepository {
    /**
     * Search for products matching the query.
     *
     * @param query Search query.
     * @param limit Maximum number of results.
     * @param offset Offset for pagination.
     * @return List of products.
     */
    suspend fun searchProducts(query: String, limit: Int, offset: Int): SearchItemsResult

    /**
     * Get detailed information for a specific product.
     *
     * @param itemId The product ID.
     * @return Product details.
     */
    suspend fun getItemDetail(itemId: String): ItemDetailDto
}
