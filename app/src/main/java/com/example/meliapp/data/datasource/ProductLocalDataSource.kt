package com.example.meliapp.data.datasource

import com.example.meliapp.data.dto.ItemDto

interface ProductLocalDataSource {
    suspend fun saveSearch(query: String, items: List<ItemDto>)
    suspend fun getSearch(query: String): List<ItemDto>?
}

