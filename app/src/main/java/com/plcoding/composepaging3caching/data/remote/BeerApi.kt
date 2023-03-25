package com.plcoding.composepaging3caching.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

// Api interface of beers
interface BeerApi {

    //gets the list of beers from API
    @GET("beers")
    suspend fun getBeers(
        @Query("page") page: Int,
        @Query("per_page") pageCount: Int
    ): List<BeerDto>

    companion object {

        // base url of the API
        const val BASE_URL = "https://api.punkapi.com/v2/"
    }
}