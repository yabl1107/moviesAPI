package com.myself.moviesappapi.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.myself.moviesappapi.R
import com.myself.moviesappapi.model.MovieList
import com.myself.moviesappapi.util.Constants.Companion.BASE_IMAGE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    title: String,
    showBackButton: Boolean = false,
    onClickBackButton: () -> Unit = {},
    showSearchButton: Boolean = false,
    onClickSearchButton: () -> Unit = {}) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if(showBackButton){
                IconButton(
                    onClick = onClickBackButton
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        },
        actions = {
            if(showSearchButton) {
                IconButton(
                    onClick = { onClickSearchButton() }
                ) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            }
        }
    )
}


@Composable
fun MovieCard(movie : MovieList, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth(0.7f),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        onClick = { onClick() }
    ) {
        Column (
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ){
            Spacer(modifier = Modifier.padding(12.dp))
            ImageCard(BASE_IMAGE + movie.poster_path)
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = movie.original_title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
fun ImageCard(url : String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        //placeholder = painterResource(R.drawable.ic_launcher_foreground),
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
    )
}

@Composable
fun Spacer(size : Int) {
    Spacer(modifier = Modifier.padding(size.dp))
}