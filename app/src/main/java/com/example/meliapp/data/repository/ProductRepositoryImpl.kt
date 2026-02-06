package com.example.meliapp.data.repository

import com.example.meliapp.data.datasource.ProductLocalDataSource
import com.example.meliapp.data.datasource.ProductRemoteDataSource
import com.example.meliapp.data.dto.ItemDetailDto
import com.example.meliapp.data.local.FavoriteProductDao
import com.example.meliapp.data.local.FavoriteProductEntity
import com.example.meliapp.domain.mapper.toDomain
import com.example.meliapp.domain.model.ProductSearchResult
import com.example.meliapp.domain.model.SearchSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource,
    private val localDataSource: ProductLocalDataSource,
    private val favoriteProductDao: FavoriteProductDao
) : ProductRepository {

    /**
     * input: query (String), limit (Int), offset (Int)
     * output: ProductSearchResult
     * utility: Orchestrates the search process by trying remote data first, caching it, or falling back to cache/mock data on failure.
     *          Also maps DTOs to Domain Products and merges favorite status.
     */
    override suspend fun searchProducts(query: String, limit: Int, offset: Int): ProductSearchResult {
        val favoriteIds = favoriteProductDao.getFavoriteIds()
        
        return try {
            val remoteItems = remoteDataSource.searchProducts(query, limit, offset)
            if (remoteItems.isNotEmpty()) {
                localDataSource.saveSearch(query, remoteItems)
                ProductSearchResult(
                    products = remoteItems.toDomain(favoriteIds),
                    source = SearchSource.REMOTE
                )
            } else {
                val cached = localDataSource.getSearch(query)
                if (cached != null && cached.isNotEmpty()) {
                    ProductSearchResult(
                        products = cached.toDomain(favoriteIds),
                        source = SearchSource.CACHE
                    )
                } else {
                    ProductSearchResult(
                        products = com.example.meliapp.data.datasource.MockData.searchResults.toDomain(favoriteIds),
                        source = SearchSource.MOCK
                    )
                }
            }
        } catch (e: Exception) {
            val cached = localDataSource.getSearch(query)
            if (cached != null && cached.isNotEmpty()) {
                ProductSearchResult(
                    products = cached.toDomain(favoriteIds),
                    source = SearchSource.CACHE
                )
            } else {
                ProductSearchResult(
                    products = com.example.meliapp.data.datasource.MockData.searchResults.toDomain(favoriteIds),
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

    override suspend fun toggleFavorite(product: com.example.meliapp.domain.model.Product) {
        if (favoriteProductDao.isFavorite(product.id)) {
            favoriteProductDao.deleteFavorite(product.id)
        } else {
            favoriteProductDao.insertFavorite(
                FavoriteProductEntity(
                    productId = product.id,
                    title = product.title,
                    price = product.price,
                    imageUrl = product.images.firstOrNull() ?: ""
                )
            )
        }
    }

    override suspend fun isFavorite(productId: String): Boolean {
        return favoriteProductDao.isFavorite(productId)
    }

    override fun getFavoriteProducts(): Flow<List<com.example.meliapp.domain.model.Product>> {
        return favoriteProductDao.getAllFavorites().map { entities ->
            entities.map { entity ->
                com.example.meliapp.domain.model.Product(
                    id = entity.productId,
                    title = entity.title,
                    price = entity.price,
                    currency = "",
                    images = listOf(entity.imageUrl),
                    condition = "",
                    availableQuantity = 0,
                    brand = "",
                    status = "",
                    category = "",
                    quality = "",
                    priority = "",
                    createdAt = "",
                    lastUpdated = "",
                    keywords = "",
                    hasVariations = false,
                    isFavorite = true
                )
            }
        }
    }
}
