package com.example.meliapp.data.repository

import com.example.meliapp.data.dto.ItemDetailDto
import com.example.meliapp.data.dto.ItemDto
import com.example.meliapp.domain.model.SearchSource

class FakeProductRepository : ProductRepository {

    private val products = mutableListOf<ItemDto>()
    private val productDetails = mutableMapOf<String, ItemDetailDto>()

    fun addProduct(product: ItemDto) {
        products.add(product)
    }

    fun addProductDetail(id: String, detail: ItemDetailDto) {
        productDetails[id] = detail
    }

    override suspend fun searchProducts(query: String, limit: Int, offset: Int): SearchItemsResult {
        val items = products.filter { it.title.contains(query, ignoreCase = true) }
            .drop(offset)
            .take(limit)
        return SearchItemsResult(
            items = items,
            source = SearchSource.REMOTE
        )
    }

    override suspend fun getItemDetail(itemId: String): ItemDetailDto {
        return productDetails[itemId] ?: throw IllegalArgumentException("Product not found")
    }

    private val favorites = mutableSetOf<String>()

    override suspend fun toggleFavorite(product: com.example.meliapp.domain.model.Product) {
        if (favorites.contains(product.id)) {
            favorites.remove(product.id)
        } else {
            favorites.add(product.id)
        }
    }

    override suspend fun isFavorite(productId: String): Boolean {
        return favorites.contains(productId)
    }

    override fun getFavoriteProducts(): kotlinx.coroutines.flow.Flow<List<com.example.meliapp.domain.model.Product>> {
        return kotlinx.coroutines.flow.flowOf(
            products.filter { favorites.contains(it.id) }.map { 
                com.example.meliapp.domain.model.Product(
                    id = it.id,
                    title = it.title,
                    price = it.price,
                    currency = it.currencyID,
                    images = it.images,
                    condition = it.condition,
                    availableQuantity = it.availableQuantity,
                    brand = it.brand,
                    status = it.status,
                    category = it.category,
                    quality = it.quality,
                    priority = it.priority,
                    createdAt = it.createdAt,
                    lastUpdated = "",
                    keywords = "",
                    hasVariations = false,
                    isFavorite = true
                )
            }
        )
    }
}
