package com.example.moviecatalog.favorite.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviecatalog.R
import com.example.moviecatalog.common.favorite.domain.model.FavoriteMovie
import com.example.moviecatalog.common.navigation.Routes
import com.example.moviecatalog.common.ui.component.MyBottomBar
import com.example.moviecatalog.common.ui.component.MyTab
import com.example.moviecatalog.common.ui.component.ReviewElement
import com.example.moviecatalog.common.ui.theme.label14MTextStyle
import com.example.moviecatalog.common.ui.theme.text15RTextStyle
import com.example.moviecatalog.common.ui.theme.title20B2TextStyle
import com.example.moviecatalog.common.ui.theme.title24BTextStyle


@Composable
fun FavoriteScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val lazyGridState = rememberLazyGridState()
    var isFavoriteClicked by remember { mutableStateOf(false) }

    LaunchedEffect(isFavoriteClicked) {
        if (isFavoriteClicked) {
            lazyGridState.scrollToItem(0)
            isFavoriteClicked = false
        }
    }

    Scaffold(
        topBar = { FavoriteTopBar() },
        bottomBar = {
            MyBottomBar(
                onMainClicked = {
                    navController.navigate(Routes.MainScreen.name) {
                        navController.popBackStack()
                    }
                },
                onFavoriteClicked = { isFavoriteClicked = true },
                onProfileClicked = {
                    navController.navigate(Routes.ProfileScreen.name) {
                        navController.popBackStack()
                    }
                },
                myTab = MyTab.Favorite
            )
        },
        modifier = modifier
    ) { innerPadding ->
        if (uiState.isThereMovies) {
            FilledFavoritePage(
                movies = uiState.movies,
                navController = navController,
                lazyGridState = lazyGridState,
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            EmptyFavoritePage(Modifier.padding(innerPadding))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteTopBar() {
    CenterAlignedTopAppBar(title = {
        Text(
            text = stringResource(R.string.favorite),
            style = title24BTextStyle,
            color = colorResource(
                R.color.white
            )
        )
    })
}

@Composable
private fun FilledFavoritePage(
    movies: List<FavoriteMovie>,
    navController: NavHostController,
    lazyGridState: LazyGridState,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        state = lazyGridState
    ) {
        items(
            movies,
            span = { item ->
                GridItemSpan(if (movies.indexOf(item) % 3 == 2) 2 else 1)
            }) { item ->
            val isCropped = movies.indexOf(item) % 3 == 2
            MovieCard(
                movie = item,
                isCropped = isCropped,
                onClick = { navController.navigate("${Routes.MovieScreen.name}/${item.id}") },
                modifier = Modifier
            )
        }
    }
}


@Composable
fun MovieCard(movie: FavoriteMovie, isCropped: Boolean, onClick: () -> Unit, modifier: Modifier) {


    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.poster)
                    .build(),
                contentDescription = stringResource(R.string.profile_icon),
                contentScale = if (isCropped) ContentScale.Crop else ContentScale.FillWidth,
                modifier = modifier
                    .height(205.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(3.dp))
            )
            if (movie.userReview?.rating != null) {
                ReviewElement(rating = movie.userReview.rating)
            }

        }

        Text(text = movie.name, style = label14MTextStyle)
    }

}


@Preview
@Composable
fun PreviewRevEm() {
    ReviewElement(rating = 5)
}

@Composable
private fun EmptyFavoritePage(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(text = stringResource(R.string.dont_have_favorite_movies), style = title20B2TextStyle)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = stringResource(R.string.select_favorite_movie), style = text15RTextStyle)
    }

}
