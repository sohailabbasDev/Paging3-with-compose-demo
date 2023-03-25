package com.plcoding.composepaging3caching.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.plcoding.composepaging3caching.data.local.BeerDatabase
import com.plcoding.composepaging3caching.data.local.BeerEntity
import com.plcoding.composepaging3caching.data.mappers.toBeerEntity
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

/*
This is Remote Mediator which
Defines a set of callbacks used to incrementally
load data from a remote source into a local source wrapped by
a PagingSource, e.g., loading data from network into a local db cache
 */

@OptIn(ExperimentalPagingApi::class)
class BeerRemoteMediator(
    private val beerDb: BeerDatabase,
    private val beerApi: BeerApi
): RemoteMediator<Int, BeerEntity>() {

    // Callback triggered when Paging needs to request more data from a remote source due to any of the following events
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>
    ): MediatorResult {
        return try {

            // this will check load type and load the data upon that
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            // beers list from api as required
            val beers = beerApi.getBeers(
                page = loadKey,
                pageCount = state.config.pageSize
            )

            // saving it in beer db or refresh from existing
            beerDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    beerDb.dao.clearAll()
                }
                val beerEntities = beers.map { it.toBeerEntity() }
                beerDb.dao.upsertAll(beerEntities)
            }

            //setting mediator result
            MediatorResult.Success(
                endOfPaginationReached = beers.isEmpty()
            )
        } catch(e: IOException) {
            // can throw IO exception
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            // can throw HTTP exception
            MediatorResult.Error(e)
        }
    }
}