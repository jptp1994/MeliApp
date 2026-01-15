/**
 * Retrofit API interface for Mercado Libre services.
 * Defines endpoints for searching products and retrieving item details.
 * Layer: Data
 * Usage: Used by repositories to make network calls to Mercado Libre API.
 */
package com.example.meliapp.data.api

import com.example.meliapp.data.dto.ItemDetailDto
import com.example.meliapp.data.dto.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MercadoLibreApi {

    @GET("sites/{siteId}/search")
    suspend fun searchProducts(
        @Path("siteId") siteId: String = "MLA",
        @Query("q") query: String,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): SearchResponseDto

    @GET("items/{itemId}")
    suspend fun getItemDetail(
        @Path("itemId") itemId: String
    ): ItemDetailDto
}