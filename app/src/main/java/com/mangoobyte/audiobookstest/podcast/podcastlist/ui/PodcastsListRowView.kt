package com.mangoobyte.audiobookstest.podcast.podcastlist.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.mangoobyte.audiobookstest.R
import com.mangoobyte.audiobookstest.podcast.podcastlist.datamodel.Podcasts

/**
 * View to display displaying each item in the podcast list.
 */
@Composable
internal fun PodcastsListRowView(
    podcastsInformation: Podcasts,
    onCLick: (id: String) -> Unit
) {
    val rememberPodcastsInformation = remember { podcastsInformation }

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onCLick(rememberPodcastsInformation.id.orEmpty()) }
    ) {
        AsyncImage(
            modifier = Modifier.size(100.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(rememberPodcastsInformation.thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = "Podcast Thumbnail",
            placeholder = painterResource(R.drawable.ic_launcher_background),
            error = painterResource(R.drawable.ic_launcher_background),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = rememberPodcastsInformation.title.orEmpty()
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = rememberPodcastsInformation.publisher.orEmpty(),
                fontStyle = FontStyle.Italic
            )
            if (podcastsInformation.isFavorite) {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Favourite",
                    color = Color.Red
                )
            }
        }
    }
}