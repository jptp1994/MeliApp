package com.example.meliapp.data.repository

import com.example.meliapp.data.datasource.ProductLocalDataSource
import com.example.meliapp.data.datasource.ProductRemoteDataSource
import com.example.meliapp.data.dto.ItemDetailDto
import com.example.meliapp.data.dto.ItemDto
import com.example.meliapp.domain.model.SearchSource
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource,
    private val localDataSource: ProductLocalDataSource
) : ProductRepository {

    /**
     * input: query (String), limit (Int), offset (Int)
     * output: SearchItemsResult
     * utility: Orchestrates the search process by trying remote data first, caching it, or falling back to cache/mock data on failure.
     */
    override suspend fun searchProducts(query: String, limit: Int, offset: Int): SearchItemsResult {
        return try {
            val remoteItems = remoteDataSource.searchProducts(query, limit, offset)
            if (remoteItems.isNotEmpty()) {
                localDataSource.saveSearch(query, remoteItems)
                SearchItemsResult(
                    items = remoteItems,
                    source = SearchSource.REMOTE
                )
            } else {
                val cached = localDataSource.getSearch(query)
                if (cached != null && cached.isNotEmpty()) {
                    SearchItemsResult(
                        items = cached,
                        source = SearchSource.CACHE
                    )
                } else {
                    SearchItemsResult(
                        items = com.example.meliapp.data.datasource.MockData.searchResults,
                        source = SearchSource.MOCK
                    )
                }
            }
        } catch (e: Exception) {
            val cached = localDataSource.getSearch(query)
            if (cached != null && cached.isNotEmpty()) {
                SearchItemsResult(
                    items = cached,
                    source = SearchSource.CACHE
                )
            } else {
                SearchItemsResult(
                    items = com.example.meliapp.data.datasource.MockData.searchResults,
                    source = SearchSource.MOCK
                )
            }
        }
    }

    /**
     * input: itemId (String)
     * output: ItemDetailDto
     * utility: Retrieves detailed information for a specific item from the remote data source.
     */
    override suspend fun getItemDetail(itemId: String): ItemDetailDto {
        return remoteDataSource.getItemDetail(itemId)
    }
}
