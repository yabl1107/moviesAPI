package com.myself.moviesappapi.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.myself.moviesappapi.components.Spacer
import com.myself.moviesappapi.viewModel.MoviesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMovieView(viewModel : MoviesViewModel, navHostController: NavHostController) {

    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val movies by viewModel.movies.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Spacer(20)
        SearchBar(
            modifier = Modifier.align(androidx.compose.ui.Alignment.TopCenter),
            //.fillMaxWidth(),
            //.padding(15.dp),
            query = query,
            onQueryChange = { query = it },
            onSearch = { active = false },
            active = active,
            onActiveChange = { active = it },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close",
                    modifier = Modifier.clickable {
                        navHostController.popBackStack()
                    })
            },
            placeholder = {
                Text(text = "Search")
            }
        ) {
            Spacer(8)
            if (query.isNotEmpty()) {
                val filterMovies = movies.filter { movie ->
                    movie.original_title.contains(query, ignoreCase = true)
                }
                filterMovies.forEach { movie ->
                    movieListElement(movie.original_title) {
                        navHostController.navigate("DetailView/${movie.id}")
                    }
                    Spacer(6)
                }

            }
        }
    }



}

@Composable
fun movieListElement(title : String, onClick : () -> Unit) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            //.height(15.dp)
            .clickable {
            onClick()
        }
    )
}