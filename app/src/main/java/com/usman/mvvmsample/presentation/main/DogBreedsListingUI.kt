package com.usman.mvvmsample.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.usman.mvvmsample.domain.model.DogBreedMinimal
import com.usman.mvvmsample.presentation.utils.ErrorMessage
import com.usman.mvvmsample.presentation.utils.LoadImageAsync
import com.usman.mvvmsample.presentation.utils.LoadingNextPageItem
import com.usman.mvvmsample.presentation.utils.PageLoader

@Composable
fun DogListingScreen(viewModel: DogBreedViewModel = hiltViewModel(), onItemClick: (Int) -> Unit) {
    val pagingData: LazyPagingItems<DogBreedMinimal> = viewModel.dogBreeds.collectAsLazyPagingItems()
    PaginatedDogList(list = pagingData, onItemClick = onItemClick)
}

@Composable
fun PaginatedDogList(list: LazyPagingItems<DogBreedMinimal>, onItemClick: (Int) -> Unit) {
    Surface(modifier = Modifier
        .padding(8.dp)
        .fillMaxSize()) {
        LazyVerticalGrid(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            columns = GridCells.Fixed(2)
        ) {
            items(list.itemCount) {index ->
                list[index]?.let {  ListItem(item = it, onItemClick) }
            }

            list.apply {
                when {
                    loadState.mediator?.refresh is LoadState.Loading -> {
                        item(span = {
                            // LazyGridItemSpanScope:
                            // maxLineSpan
                            GridItemSpan(maxLineSpan)
                        }) { PageLoader(modifier = Modifier.fillMaxSize()) }
                    }

                    loadState.mediator?.refresh is LoadState.Error -> {
                        val error = (list.loadState.refresh as LoadState.Error).error
                        item(span = {
                            GridItemSpan(maxLineSpan)
                        }) {
                            ErrorMessage(
                                modifier = Modifier.fillMaxSize(),
                                message = error.localizedMessage!!,
                                onClickRetry = { retry() })
                        }
                    }

                    loadState.mediator?.append is LoadState.Loading || loadState.source.append is LoadState.Loading -> {
                        item(span = {
                            // LazyGridItemSpanScope:
                            // maxLineSpan
                            GridItemSpan(maxLineSpan)
                        }) { LoadingNextPageItem(modifier = Modifier) }
                    }

                    loadState.mediator?.append is LoadState.Error || loadState.source.append is LoadState.Error ->
                    {
                        val error = (list.loadState.append as LoadState.Error).error
                        item(span = {
                            // LazyGridItemSpanScope:
                            // maxLineSpan
                            GridItemSpan(maxLineSpan)
                        }) {
                            ErrorMessage(
                                modifier = Modifier,
                                message = error.localizedMessage!!,
                                onClickRetry = { retry() })
                        }
                    }
                }
            }
        }
    }

}

//@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ListItem(item: DogBreedMinimal, onClick: (Int) -> Unit) {
    Card {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable {
                onClick(item.id)
            }) {

            LoadImageAsync(referenceImageId = item.imageReferenceId, "Dog Image", modifier = Modifier.aspectRatio(1.0f),
                contentScale = ContentScale.Crop)

            Text(
                text = item.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.subtitle1,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color(0f, 0f, 0f, 0.37f))
            )
        }
    }
}