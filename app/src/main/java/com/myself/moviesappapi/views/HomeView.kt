package com.myself.moviesappapi.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.myself.moviesappapi.R
import com.myself.moviesappapi.components.MovieCard
import com.myself.moviesappapi.components.MyTopBar
import com.myself.moviesappapi.model.MovieList
import com.myself.moviesappapi.util.Constants.Companion.BASE_IMAGE
import com.myself.moviesappapi.viewModel.MoviesViewModel


@Composable
fun HomeView(viewModel: MoviesViewModel, navController: NavHostController) {
    Scaffold(
        topBar = { MyTopBar(title = "Home View", showBackButton = false, {}) }
    ) {
        ContentHomeView(viewModel, it, navController)
    }
}


@Composable
fun ContentHomeView(
    viewModel: MoviesViewModel,
    pad: PaddingValues,
    navController: NavHostController
) {
    val movies by viewModel.movies.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(pad),
        horizontalAlignment = CenterHorizontally,
    ) {
        itemsIndexed(movies) { index, movie ->
            Spacer(modifier = Modifier.padding(5.dp))
            MovieCard(movie) {
                navController.navigate("DetailView/${movie.id}")
            }
            Spacer(modifier = Modifier.padding(5.dp))
        }
    }
}