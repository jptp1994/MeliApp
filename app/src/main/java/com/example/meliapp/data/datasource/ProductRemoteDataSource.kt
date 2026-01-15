package com.example.meliapp.data.datasource

import com.example.meliapp.data.dto.ItemDetailDto
import com.example.meliapp.data.dto.ItemDto

interface ProductRemoteDataSource {
    suspend fun searchProducts(query: String, limit: Int, offset: Int): List<ItemDto>
    suspend fun getItemDetail(itemId: String): ItemDetailDto
}