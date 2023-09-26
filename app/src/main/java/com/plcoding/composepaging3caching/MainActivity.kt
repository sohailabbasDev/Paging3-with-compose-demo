package com.plcoding.composepaging3caching

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.plcoding.composepaging3caching.presentation.BeerScreen
import com.plcoding.composepaging3caching.presentation.BeerViewModel
import com.plcoding.composepaging3caching.ui.theme.ComposePaging3CachingTheme
import dagger.hilt.android.AndroidEntryPoint

// main entry point which sets up the beer screen
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePaging3CachingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    // view model instance of field injection
                    val viewModel = hiltViewModel<BeerViewModel>()

                    // beers item collected with lazy paging
                    val beers = viewModel.beerPagingFlow.collectAsLazyPagingItems()

                    //beers screen
                    BeerScreen(beers = beers)
                }
            }
        }
    }
}