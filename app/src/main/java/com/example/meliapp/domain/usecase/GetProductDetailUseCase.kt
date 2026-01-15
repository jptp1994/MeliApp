package com.example.meliapp.domain.usecase

import com.example.meliapp.data.repository.ProductRepository
import com.example.meliapp.domain.mapper.toDomain
import com.example.meliapp.domain.model.ProductDetail
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    /**
     * input: itemId (String)
     * output: ProductDetail
     * utility: Executes the business logic for retrieving product details, mapping repository results to domain models.
     */
    suspend operator fun invoke(itemId: String): ProductDetail {
        return repository.getItemDetail(itemId).toDomain()
    }
}