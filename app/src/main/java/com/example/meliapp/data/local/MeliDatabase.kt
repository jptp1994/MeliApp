package com.example.meliapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProductSearchEntity::class, FavoriteProductEntity::class],
    version = 2,
    exportSchema = false
)
abstract class MeliDatabase : RoomDatabase() {
    abstract fun productSearchDao(): ProductSearchDao
    abstract fun favoriteProductDao(): FavoriteProductDao
}

