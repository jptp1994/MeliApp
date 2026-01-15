package com.example.meliapp.data.datasource

import com.example.meliapp.data.api.MercadoLibreApi
import com.example.meliapp.data.dto.ItemDetailDto
import com.example.meliapp.data.dto.ItemDto
import com.example.meliapp.utils.Constants.tag
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val api: MercadoLibreApi
) : ProductRemoteDataSource {

    /**
     * input: query (String), limit (Int), offset (Int)
     * output: List<ItemDto>
     * utility: Fetches a list of products from the Mercado Libre API based on a search query. Returns mock data on error.
     */
    override suspend fun searchProducts(query: String, limit: Int, offset: Int): List<ItemDto> {
        return try {
            val catalogResults = api.searchProducts(query = query, limit = limit, offset = offset).results
            catalogResults.map { catalogItem ->
                val images = catalogItem.pictures?.mapNotNull { it.url } ?: emptyList()
                val brand = catalogItem.attributes?.find { it.id == "BRAND" }?.value_name ?: "Sin marca"
                
                ItemDto(
                    id = catalogItem.id,
                    title = catalogItem.name ?: "",
                    price = 0.0, // Price might not be in search result, keeping 0.0 as per previous implementation
                    currencyID = "",
                    images = images,
                    condition = "",
                    availableQuantity = 0,
                    brand = brand,
                    status = catalogItem.status ?: "",
                    category = catalogItem.domain_id ?: "",
                    quality = catalogItem.quality_type ?: "",
                    priority = catalogItem.priority ?: "",
                    createdAt = formatDate(catalogItem.date_created),
                    lastUpdated = formatDate(catalogItem.last_updated),
                    keywords = catalogItem.keywords ?: "",
                    hasVariations = (catalogItem.variations?.size ?: 0) > 0
                )
            }
        } catch (e: Exception) {
            // android.util.Log.e(tag, "Search Error: ${e.message}, returning mock data")
            return MockData.searchResults
        }
    }

    /**
     * input: dateString (String?)
     * output: String
     * utility: Formats an ISO-8601 date string (YYYY-MM-DD...) into a more readable format (DD/MM/YYYY).
     */
    private fun formatDate(dateString: String?): String {
        if (dateString.isNullOrEmpty()) return ""
        return try {
            // Simple ISO parser (YYYY-MM-DD...) to DD/MM/YYYY
            if (dateString.length >= 10) {
                val datePart = dateString.take(10)
                val parts = datePart.split("-")
                if (parts.size == 3) {
                    "${parts[2]}/${parts[1]}/${parts[0]}"
                } else dateString
            } else dateString
        } catch (e: Exception) {
            dateString
        }
    }

    /**
     * input: itemId (String)
     * output: ItemDetailDto
     * utility: Retrieves detailed information for a specific item from the API. Returns mock data on error.
     */
    override suspend fun getItemDetail(itemId: String): ItemDetailDto {
        return try {
            api.getItemDetail(itemId)
        } catch (e: Exception) {
            // Fallback to Mock Data on ANY error as per user request for reliability
            android.util.Log.e(tag, "Detail Error: ${e.message}, returning mock data")
            return MockData.getItemDetail(itemId)
        }
    }
}
