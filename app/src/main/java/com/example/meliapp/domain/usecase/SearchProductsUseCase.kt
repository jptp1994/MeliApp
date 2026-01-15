package com.example.meliapp.domain.usecase

import com.example.meliapp.data.repository.ProductRepository
import com.example.meliapp.domain.mapper.toDomain
import com.example.meliapp.domain.model.ProductSearchResult
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    /**
     * input: query (String), limit (Int), offset (Int)
     * output: ProductSearchResult
     * utility: Executes the business logic for searching products, mapping repository results to domain models.
     */
    suspend operator fun invoke(query: String, limit: Int = 20, offset: Int = 0): ProductSearchResult {
        val searchItemsResult = repository.searchProducts(query, limit, offset)
        val products = searchItemsResult.items.toDomain()
        return ProductSearchResult(
            products = products,
            source = searchItemsResult.source
        )
    }
}
