package com.example.meliapp.domain.model

data class ProductSearchResult(
    val products: List<Product>,
    val source: SearchSource
)

