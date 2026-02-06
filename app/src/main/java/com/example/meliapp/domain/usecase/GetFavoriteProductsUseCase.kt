package com.example.meliapp.domain.usecase

import com.example.meliapp.data.repository.ProductRepository
import com.example.meliapp.domain.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<List<Product>> {
        return repository.getFavoriteProducts()
    }
}
