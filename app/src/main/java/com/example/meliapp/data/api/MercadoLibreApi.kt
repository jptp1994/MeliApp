/**
 * Retrofit API interface for Mercado Libre services.
 * Defines endpoints for searching products and retrieving item details.
 * Layer: Data
 * Usage: Used by repositories to make network calls to Mercado Libre API.
 */
package com.example.meliapp.data.api

import com.example.meliapp.data.dto.ItemDetailDto
import com.example.meliapp.data.dto.ProductsSearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MercadoLibreApi {

    /**
     * input: query (String), limit (Int), offset (Int)
     * output: ProductsSearchResponseDto
     * utility: Makes a GET request to the Mercado Libre API to search for products.
     */
    @GET("products/search")
    suspend fun searchProducts(
        @Query("status") status: String = "active",
        @Query("site_id") siteId: String = "MLA",
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): ProductsSearchResponseDto

    /**
     * input: itemId (String)
     * output: ItemDetailDto
     * utility: Makes a GET request to the Mercado Libre API to retrieve item details.
     */
    @GET("items/{itemId}")
    suspend fun getItemDetail(
        @Path("itemId") itemId: String
    ): ItemDetailDto
}
