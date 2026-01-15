package com.example.meliapp.data.datasource

import com.example.meliapp.data.dto.ItemDto

class FakeProductLocalDataSource : ProductLocalDataSource {

    var storedQuery: String? = null
    var storedItems: List<ItemDto>? = null

    override suspend fun saveSearch(query: String, items: List<ItemDto>) {
        storedQuery = query
        storedItems = items
    }

    override suspend fun getSearch(query: String): List<ItemDto>? {
        return if (storedQuery == query) storedItems else null
    }
}

