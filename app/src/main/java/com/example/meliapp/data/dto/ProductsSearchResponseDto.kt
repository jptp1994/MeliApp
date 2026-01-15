package com.example.meliapp.data.dto

data class ProductsSearchResponseDto(
    val results: List<ProductCatalogDto>
)

data class ProductCatalogDto(
    val id: String,
    val name: String?,
    val pictures: List<ProductCatalogPictureDto>?,
    val attributes: List<ProductCatalogAttributeDto>?,
    val status: String?,
    val domain_id: String?,
    val quality_type: String?,
    val priority: String?,
    val date_created: String?,
    val last_updated: String?,
    val keywords: String?,
    val variations: List<Any>?
)

data class ProductCatalogPictureDto(
    val url: String?
)

data class ProductCatalogAttributeDto(
    val id: String?,
    val name: String?,
    val value_name: String?
)
