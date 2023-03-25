package com.plcoding.composepaging3caching.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

//ROOM Database of beers
@Database(
    entities = [BeerEntity::class],
    version = 1
)
abstract class BeerDatabase: RoomDatabase() {

    // DAO object
    abstract val dao: BeerDao
}