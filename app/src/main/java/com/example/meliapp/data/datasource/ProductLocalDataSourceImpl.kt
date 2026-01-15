package com.example.meliapp.data.datasource

import com.example.meliapp.data.dto.ItemDto
import com.example.meliapp.data.local.ProductSearchDao
import com.example.meliapp.data.local.ProductSearchEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

class ProductLocalDataSourceImpl @Inject constructor(
    private val dao: ProductSearchDao,
    private val moshi: Moshi
) : ProductLocalDataSource {

    private val type = Types.newParameterizedType(List::class.java, ItemDto::class.java)
    private val adapter = moshi.adapter<List<ItemDto>>(type)

    /**
     * input: query (String), items (List<ItemDto>)
     * output: Unit
     * utility: Saves the search results for a specific query into the local database for offline access.
     */
    override suspend fun saveSearch(query: String, items: List<ItemDto>) {
        val json = adapter.toJson(items)
        val entity = ProductSearchEntity(
            query = query,
            itemsJson = json,
            createdAt = System.currentTimeMillis()
        )
        dao.deleteByQuery(query)
        dao.insertSearch(entity)
    }

    /**
     * input: query (String)
     * output: List<ItemDto>?
     * utility: Retrieves cached search results for a given query from the local database, or returns null if not found.
     */
    override suspend fun getSearch(query: String): List<ItemDto>? {
        val entity = dao.getSearchByQuery(query) ?: return null
        return adapter.fromJson(entity.itemsJson)
    }
}

