package com.example.meliapp.domain.usecase

import com.example.meliapp.data.repository.ProductRepository
import com.example.meliapp.domain.mapper.toDomain
import com.example.meliapp.domain.model.Product
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(query: String, limit: Int = 20, offset: Int = 0): List<Product> {
        return repository.searchProducts(query, limit, offset).toDomain()
    }
}