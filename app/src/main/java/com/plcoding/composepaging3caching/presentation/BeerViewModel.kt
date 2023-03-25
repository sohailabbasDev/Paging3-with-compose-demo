package com.plcoding.composepaging3caching.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.plcoding.composepaging3caching.data.local.BeerEntity
import com.plcoding.composepaging3caching.data.mappers.toBeer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// view model that will handle all the beers
@HiltViewModel
class BeerViewModel @Inject constructor(
    pager: Pager<Int, BeerEntity>
): ViewModel() {

    // val that will contain a pager flow which has paging data in it
    val beerPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toBeer() }
        }
        .cachedIn(viewModelScope)
}