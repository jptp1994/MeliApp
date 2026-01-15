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
}
