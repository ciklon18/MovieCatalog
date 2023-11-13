package com.example.moviecatalog.main.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviecatalog.R
import com.example.moviecatalog.common.main.domain.model.UpdatedMovieElementModel
import com.example.moviecatalog.common.navigation.Routes
import com.example.moviecatalog.common.ui.component.MyBottomBar
import com.example.moviecatalog.common.ui.component.MyTab
import com.example.moviecatalog.common.ui.component.PageIndicator
import com.example.moviecatalog.common.ui.theme.title24BTextStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val lazyMovieItems: LazyPagingItems<UpdatedMovieElementModel> =
        uiState.movies.collectAsLazyPagingItems()

    Scaffold(
        bottomBar = {
            MyBottomBar(
                onMainClicked = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                onFavoriteClicked = { navController.navigate(Routes.FavoriteScreen.name) },
                onProfileClicked = { navController.navigate(Routes.ProfileScreen.name) },
                myTab = MyTab.Main
            )
        }, modifier = modifier
    ) { innerPadding ->
        MainContent(
            navController = navController,
            listState = listState,
            lazyMovieItems = lazyMovieItems,
            innerPadding = innerPadding
        )
    }
}

@Composable
fun MainContent(
    navController: NavHostController,
    listState: LazyListState,
    lazyMovieItems: LazyPagingItems<UpdatedMovieElementModel>,
    innerPadding: PaddingValues
) {
    LazyColumn(
        state = listState, modifier = Modifier.padding(innerPadding)
    ) {
        item {
            SwipeMovieSection(
                swipedMovieItems = getSwipeImageList(lazyMovieItems), navController = navController
            )
        }
        item { CatalogTitle() }
        movieCatalogSection(movies = lazyMovieItems, navController = navController)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeMovieSection(
    swipedMovieItems: List<UpdatedMovieElementModel>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(0, 0f) { swipedMovieItems.size }
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            if (pagerState.pageCount != 0) {
                val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                pagerState.animateScrollToPage(nextPage)
            }

        }
    }

    Box(
        modifier = modifier
            .height(600.dp)
            .fillMaxWidth(), contentAlignment = Alignment.BottomCenter
    ) {
        HorizontalPager(
            state = pagerState, pageSize = PageSize.Fill, modifier = Modifier.fillMaxSize()
        ) { index ->
            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(swipedMovieItems[index].poster).build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { navController.navigate("${Routes.MovieScreen.name}/${swipedMovieItems[index].id}") })
        }
        Box(
            modifier = Modifier.padding(10.dp)
        ) {
            PageIndicator(
                pageCount = swipedMovieItems.size,
                currentPage = pagerState.currentPage,
                modifier = Modifier
            )
        }
    }

}


@Composable
fun CatalogTitle(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.catalog),
            color = colorResource(R.color.white),
            style = title24BTextStyle,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}


fun LazyListScope.movieCatalogSection(
    movies: LazyPagingItems<UpdatedMovieElementModel>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    items(movies.itemCount) { index ->
        if (index > 3) {
            movies[index]?.let { movie ->
                MovieCard(
                    movie = movie,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                        .clickable {
                            navController.navigate("${Routes.MovieScreen.name}/${movie.id}")
                        }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

private fun getSwipeImageList(
    lazyMovieItems: LazyPagingItems<UpdatedMovieElementModel>
): List<UpdatedMovieElementModel> {
    return if (lazyMovieItems.itemCount > 0) {
        (0..3).mapNotNull { index -> lazyMovieItems[index] }
    } else emptyList()
}
