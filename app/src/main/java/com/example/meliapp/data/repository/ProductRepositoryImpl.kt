package com.example.meliapp.data.repository

import com.example.meliapp.data.datasource.ProductRemoteDataSource
import com.example.meliapp.data.dto.ItemDetailDto
import com.example.meliapp.data.dto.ItemDto
import com.example.meliapp.data.exception.NetworkException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource
) : ProductRepository {

    override suspend fun searchProducts(query: String, limit: Int, offset: Int): List<ItemDto> {
        return remoteDataSource.searchProducts(query, limit, offset)
    }

    override suspend fun getItemDetail(itemId: String): ItemDetailDto {
        return remoteDataSource.getItemDetail(itemId)
    }
}