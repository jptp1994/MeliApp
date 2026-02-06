package com.example.meliapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteProductEntity)

    @Query("DELETE FROM favorite_products WHERE productId = :productId")
    suspend fun deleteFavorite(productId: String)

    @Query("SELECT * FROM favorite_products")
    fun getAllFavorites(): Flow<List<FavoriteProductEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_products WHERE productId = :productId)")
    suspend fun isFavorite(productId: String): Boolean
    
    @Query("SELECT productId FROM favorite_products")
    suspend fun getFavoriteIds(): List<String>
}
