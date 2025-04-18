package com.myself.moviesappapi.views

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.myself.moviesappapi.R
import com.myself.moviesappapi.components.ImageCard
import com.myself.moviesappapi.components.MyTopBar
import com.myself.moviesappapi.model.singleGameModel
import com.myself.moviesappapi.util.Constants.Companion.BASE_IMAGE
import com.myself.moviesappapi.viewModel.MoviesViewModel
import java.text.NumberFormat
import java.util.Locale
import com.myself.moviesappapi.components.Spacer
import com.myself.moviesappapi.model.genreModel

@Composable
fun DetailView(navHostController: NavHostController, viewModel: MoviesViewModel, id: Int) {

    val movie = viewModel.movieState.movie

    Scaffold(
        topBar = {
            MyTopBar(movie.title, showBackButton = true, onClickBackButton =  {
                navHostController.popBackStack()
            })
        },
        floatingActionButton = {
            if(movie.homepage.isNotEmpty())
            {
                websiteFAB(movie.homepage)
            }
        }
    ) { padding ->
        ContentDetailView(movie, padding)
    }

    LaunchedEffect(Unit) {
        viewModel.fetchMovieDetail(id)
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clean()
        }
    }
}

@Composable
fun ContentDetailView(movie: singleGameModel, padd: PaddingValues) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padd)
            .padding(horizontal = 25.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = CenterHorizontally
    ) {


        Spacer(15)

        ImageCard(
            BASE_IMAGE + movie.backdrop_path,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(4)
        moviewInfoRow(movie.runtime, movie.release_date)
        genreRow(movie.genres) //LazyRow para mostrar los generos
        Spacer(5)
        Text(
            text = movie.overview,
            textAlign = TextAlign.Justify
        )

        Spacer(10)
        MovieFinanceInfo(movie.budget, movie.revenue)
        Spacer(10)
    }
}

@Composable
fun genreRow(genres : List<genreModel>) {
    Column{
        Text(text ="Genres", fontWeight = FontWeight.Bold,modifier = Modifier.align(Alignment.Start))
        Spacer(5)
        LazyRow (
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ){
            items(genres) { genre ->
                genre(genre.name)
            }
        }
    }
}



@Composable
fun moviewInfoRow(runtime:Int, release_date : String) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ){
        Image(
            painter = painterResource(id = R.drawable.movie),
            contentDescription = "movie duration",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.size(20.dp),
            alpha = 0.7F
        )
        Spacer(2)
        Text(text = "${runtime} min")
        Spacer(10)
        Image(
            painter = painterResource(id = R.drawable.calendar),
            contentDescription = "release date",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.size(20.dp),
            alpha = 0.5F
        )
        Spacer(2)
        Text(text = release_date)
    }
}


@Composable
fun genre(name : String, modifier: Modifier = Modifier) {
    Card (
        shape = RoundedCornerShape(5.dp)
   ){
        Text(
            text = name,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
   }
}


@Composable
fun MovieFinanceInfo(budget: Long, revenue: Long) {
    val profit = revenue - budget

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        FinanceRow(label = "Budget", value = budget.toCurrency(), icon = R.drawable.chart)
        FinanceRow(label = "Revenue", value = revenue.toCurrency(), icon = R.drawable.piggy)
        FinanceRow(label = "Profit", value = profit.toCurrency(), icon = R.drawable.moneybag,true)
    }
}

fun Long.toCurrency(): String {
    return NumberFormat.getCurrencyInstance(Locale.US).format(this)
}

@Composable
fun FinanceRow(label: String, value: String, @DrawableRes icon: Int, isProfit: Boolean = false) {
    val color = if (isProfit && value.contains("-"))  Color.Red else Color.Green
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            colorFilter = if(isProfit) ColorFilter.tint(color) else ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
        )
        Text(text = "$label: ", fontWeight = FontWeight.Bold)
        Text(text = value)
    }
}

@Composable
fun websiteFAB(url : String) {

    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

    FloatingActionButton(
        onClick = {
            //Ir a la pagina de busqueda
            context.startActivity(intent) }
    ){
        Icon(
            painter = painterResource(id = R.drawable.internet),
            contentDescription = "Search")
    }
}