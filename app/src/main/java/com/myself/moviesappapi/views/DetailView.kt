package com.myself.moviesappapi.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.myself.moviesappapi.R
import com.myself.moviesappapi.components.ImageCard
import com.myself.moviesappapi.components.MyTopBar
import com.myself.moviesappapi.util.Constants.Companion.BASE_IMAGE
import com.myself.moviesappapi.viewModel.MoviesViewModel

@Composable
fun DetailView(navHostController: NavHostController, viewModel: MoviesViewModel, id: Int) {

    Scaffold(
        topBar = {MyTopBar("Detail Screen", true){
            navHostController.popBackStack()
        }}
    ){ padding ->
        ContentDetailView(viewModel, padding)
    }

    LaunchedEffect(Unit) {
        viewModel.fetchMovieDetail(id)
    }

    DisposableEffect(Unit){
        onDispose {
            viewModel.clean()
        }
    }

}

@Composable
fun ContentDetailView(viewModel: MoviesViewModel, padd: PaddingValues) {
    val movie = viewModel.movieState.movie

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padd)
            .background(Color.Cyan),
        horizontalAlignment = CenterHorizontally
    ) {

        if(movie!=null){
            Text(text = movie.title)
            Spacer(modifier=Modifier.padding(10.dp))

            ImageCard(BASE_IMAGE + movie.backdrop_path)

            Spacer(modifier=Modifier.padding(10.dp))
            Text(text = movie.overview)
            Spacer(modifier=Modifier.padding(10.dp))
            Text(text = movie.release_date)
            Spacer(modifier=Modifier.padding(10.dp))
        }

    }
}
