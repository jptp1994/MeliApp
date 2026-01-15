package com.example.meliapp.data.datasource

import com.example.meliapp.data.dto.ItemDetailDto
import com.example.meliapp.data.dto.ItemDto

class FakeProductRemoteDataSource : ProductRemoteDataSource {

    private val products = mutableListOf<ItemDto>()
    private val productDetails = mutableMapOf<String, ItemDetailDto>()

    fun addProduct(product: ItemDto) {
        products.add(product)
    }

    fun addProductDetail(id: String, detail: ItemDetailDto) {
        productDetails[id] = detail
    }

    override suspend fun searchProducts(query: String, limit: Int, offset: Int): List<ItemDto> {
        return products.filter { it.title.contains(query, ignoreCase = true) }
            .drop(offset)
            .take(limit)
    }

    override suspend fun getItemDetail(itemId: String): ItemDetailDto {
        return productDetails[itemId] ?: throw IllegalArgumentException("Product not found")
    }
}