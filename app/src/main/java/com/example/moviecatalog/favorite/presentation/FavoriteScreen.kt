package com.example.moviecatalog.favorite.presentation

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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviecatalog.R
import com.example.moviecatalog.common.navigation.Routes
import com.example.moviecatalog.common.ui.component.MyBottomBar
import com.example.moviecatalog.common.ui.component.MyTab
import com.example.moviecatalog.common.ui.component.ReviewElement
import com.example.moviecatalog.common.ui.theme.label14MTextStyle
import com.example.moviecatalog.common.ui.theme.text15RTextStyle
import com.example.moviecatalog.common.ui.theme.title20B2TextStyle
import com.example.moviecatalog.common.ui.theme.title24BTextStyle
import com.example.moviecatalog.common.favorite.domain.model.MovieResponse


@Composable
fun FavoriteScreen(
    navController: NavHostController,
    viewModel: FavoriteViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { FavoriteTopBar() },
        bottomBar = {
            MyBottomBar(
                onMainClicked = { navController.navigate(Routes.MainScreen.name) },
                onFavoriteClicked = {},
                onProfileClicked = { navController.navigate(Routes.ProfileScreen.name) },
                myTab = MyTab.Favorite
            )
        },
        modifier = modifier
    ) { innerPadding ->
        if (uiState.isThereMovies) {
            FilledFavoritePage(
                movies = uiState.movies,
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
    movies: List<MovieResponse>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
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
                modifier = Modifier,
            )
        }
    }
}


@Composable
fun MovieCard(movie: MovieResponse, isCropped: Boolean, modifier: Modifier) {
    val rating = movie.reviews.find { it.id == movie.id }?.rating ?: 0

    Column(
        modifier = modifier.fillMaxWidth()
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
            ReviewElement(rating = rating)
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
