package com.example.meliapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductSearchDao {

    /**
     * input: query (String)
     * output: ProductSearchEntity?
     * utility: Retrieves a search result entity from the database matching the given query.
     */
    @Query("SELECT * FROM product_search WHERE `query` = :query LIMIT 1")
    suspend fun getSearchByQuery(query: String): ProductSearchEntity?

    /**
     * input: search (ProductSearchEntity)
     * output: Unit
     * utility: Inserts a new search result entity into the database, replacing it if it already exists.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(search: ProductSearchEntity)

    /**
     * input: query (String)
     * output: Unit
     * utility: Deletes a search result entity from the database matching the given query.
     */
    @Query("DELETE FROM product_search WHERE `query` = :query")
    suspend fun deleteByQuery(query: String)
}

