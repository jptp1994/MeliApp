package com.example.meliapp.data.datasource

import com.example.meliapp.data.api.MercadoLibreApi
import com.example.meliapp.data.dto.ItemDetailDto
import com.example.meliapp.data.dto.ItemDto
import com.example.meliapp.data.exception.NetworkException
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val api: MercadoLibreApi
) : ProductRemoteDataSource {

    override suspend fun searchProducts(query: String, limit: Int, offset: Int): List<ItemDto> {
        return try {
            api.searchProducts(query = query, limit = limit, offset = offset).results
        } catch (e: HttpException) {
            throw NetworkException.HttpError(e.code(), e.message())
        } catch (e: IOException) {
            throw NetworkException.NoInternetError("No internet connection")
        } catch (e: Exception) {
            throw NetworkException.UnknownError(e.message ?: "Unknown error")
        }
    }

    override suspend fun getItemDetail(itemId: String): ItemDetailDto {
        return try {
            api.getItemDetail(itemId)
        } catch (e: HttpException) {
            throw NetworkException.HttpError(e.code(), e.message())
        } catch (e: IOException) {
            throw NetworkException.NoInternetError("No internet connection")
        } catch (e: Exception) {
            throw NetworkException.UnknownError(e.message ?: "Unknown error")
        }
    }
}