package com.example.meliapp.di

import android.content.Context
import androidx.room.Room
import com.example.meliapp.data.local.MeliDatabase
import com.example.meliapp.data.local.ProductSearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MeliDatabase {
        return Room.databaseBuilder(
            context,
            MeliDatabase::class.java,
            "meli_db"
        ).build()
    }

    @Provides
    fun provideProductSearchDao(database: MeliDatabase): ProductSearchDao {
        return database.productSearchDao()
    }
}
