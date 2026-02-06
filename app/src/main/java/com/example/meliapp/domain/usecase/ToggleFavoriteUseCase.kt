package com.example.meliapp.domain.usecase

import com.example.meliapp.data.repository.ProductRepository
import com.example.meliapp.domain.model.Product
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: Product) {
        repository.toggleFavorite(product)
    }
}
