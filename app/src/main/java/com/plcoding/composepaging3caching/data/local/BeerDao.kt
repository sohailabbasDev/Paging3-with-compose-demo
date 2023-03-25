package com.plcoding.composepaging3caching.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

//Beers Data access object
@Dao
interface BeerDao {

    //Upsert which means it will insert a new data or will update existing if the primary key matches and already exists
    @Upsert
    suspend fun upsertAll(beers: List<BeerEntity>)

    //Returns the paging source of the beer entity
    @Query("SELECT * FROM beerentity")
    fun pagingSource(): PagingSource<Int, BeerEntity>

    //deletes from the DB
    @Query("DELETE FROM beerentity")
    suspend fun clearAll()
}