package com.example.meliapp.di

import com.example.meliapp.data.datasource.ProductLocalDataSource
import com.example.meliapp.data.datasource.ProductLocalDataSourceImpl
import com.example.meliapp.data.datasource.ProductRemoteDataSource
import com.example.meliapp.data.datasource.ProductRemoteDataSourceImpl
import com.example.meliapp.data.repository.ProductRepository
import com.example.meliapp.data.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindProductRemoteDataSource(
        productRemoteDataSourceImpl: ProductRemoteDataSourceImpl
    ): ProductRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindProductLocalDataSource(
        productLocalDataSourceImpl: ProductLocalDataSourceImpl
    ): ProductLocalDataSource
}
