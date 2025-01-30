package com.mangoobyte.audiobookstest.podcast.podcastdetails.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.mangoobyte.audiobookstest.R
import com.mangoobyte.audiobookstest.podcast.podcastlist.datamodel.PaginationUiState
import com.mangoobyte.audiobookstest.podcast.podcastlist.datamodel.Podcasts
import com.mangoobyte.audiobookstest.podcast.podcastlist.viewmodel.PodCastListViewModel

@Composable
fun PodCastDetailsView(
    id: String,
    navController: NavController,
    viewModel: PodCastListViewModel
) {
    PodCastDetails(
        podCastDetails = viewModel.getPodCastInformation(id),
        navController = navController,
        updateFavourite = {
            viewModel.updateFavouritePodcast(
                podcastId = id,
                isFavourite = it
            )
        }
    )
}

@Composable
private fun PodCastDetails(
    podCastDetails: Podcasts?,
    navController: NavController,
    updateFavourite: (isFavourite: Boolean) -> Unit
) {

    Column {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.clickable {
                    navController.popBackStack()
                },
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "back icon"
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Back"
            )
        }
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            podCastDetails?.let { details ->
                var isFavorite = remember { mutableStateOf(details.isFavorite) }

                Text(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    text = details.title.orEmpty(),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    text = details.publisher.orEmpty(),
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp
                )

                AsyncImage(
                    modifier = Modifier.size(300.dp).align(Alignment.CenterHorizontally),

                    model = ImageRequest.Builder(LocalContext.current)
                        .data(details.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Podcast Thumbnail",
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    error = painterResource(R.drawable.ic_launcher_background),
                    contentScale = ContentScale.Crop
                )

                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        isFavorite.value = !isFavorite.value
                        updateFavourite(isFavorite.value)
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = if (isFavorite.value)
                            "Favourited"
                        else
                            "Favourite",
                        color = Color.White
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    text = details.description.orEmpty(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun PodCastDetailsPreview() {
    PodCastDetails(
        podCastDetails = Podcasts(
            title = "Pod cast title",
            description = "description"
        ),
        navController = rememberNavController(),
        updateFavourite = {}
    )
}