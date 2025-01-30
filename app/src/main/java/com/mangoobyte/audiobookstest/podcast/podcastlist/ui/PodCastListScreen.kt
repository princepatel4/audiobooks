package com.mangoobyte.audiobookstest.podcast.podcastlist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mangoobyte.audiobookstest.navigation.RoutePath
import com.mangoobyte.audiobookstest.podcast.podcastlist.datamodel.PaginationUiState
import com.mangoobyte.audiobookstest.podcast.podcastlist.datamodel.Podcasts
import com.mangoobyte.audiobookstest.podcast.podcastlist.viewmodel.PodCastListViewModel
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * Compose view to display list of podcasts.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PodCastListScreen(
    navController: NavController,
    podCastListViewModel: PodCastListViewModel
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            val paginationState = podCastListViewModel.uiStatePagination.collectAsStateWithLifecycle().value

            PodCastListView(
                uiStatePagination = paginationState,
                loadMorePodcasts = { podCastListViewModel.loadMorePodcasts() },
                onCLick = {
                    navController.navigate(
                        route = "${RoutePath.PODCAST_DETAILS}/id=$it"
                    )
                }
            )
        }
    }
}

@Composable
fun PodCastListView(
    uiStatePagination: PaginationUiState,
    loadMorePodcasts: () -> Unit,
    onCLick: (id: String) -> Unit
) {
    // Remembering the state of the LazyColumn for smooth scrolling
    val rememberPaginationUiState = rememberUpdatedState(uiStatePagination)
    val paginationIsLoading = remember {
        derivedStateOf { rememberPaginationUiState.value.isLoading }
    }

    val listState = rememberLazyListState()

    Text(
        modifier = Modifier.padding(16.dp),
        text = "Podcasts",
        fontSize = 20.sp
    )
    when {
        rememberPaginationUiState.value.error -> {

        }
        else -> {
            LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
                items(items = rememberPaginationUiState.value.podcasts, key = { it.id.hashCode() }) { item ->
                    PodcastsListRowView(
                        podcastsInformation = item,
                        onCLick = onCLick
                    )
                }
                if (paginationIsLoading.value) {
                    item {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }

    LaunchedEffect(listState) {
        // Using snapshotFlow to monitor the last visible item index in the list.
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged() // Avoid triggering multiple times for the same index
            .collect { index ->
                // If the user is near the end of the list and no data is currently loading, load more items.
                if (index != null && index >= rememberPaginationUiState.value.podcasts.lastIndex - 5 && !paginationIsLoading.value) {
                    loadMorePodcasts()
                }
            }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun PodCastListScreenPreview() {
    val uiStatePagination =
        PaginationUiState(
            podcasts = arrayListOf(
                Podcasts(
                    title = "Podcast1",
                    thumbnail = "https://cdn-images-3.listennotes.com/podcasts/worklife-with-adam-grant-ted-KgaXjFPEoVc.300x300.jpg",
                    publisher = "Author"
                ),
                Podcasts(
                    title = "Podcast2",
                    thumbnail = "https://cdn-images-3.listennotes.com/podcasts/worklife-with-adam-grant-ted-KgaXjFPEoVc.300x300.jpg",
                    publisher = "Author"
                )
            )
        )

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            PodCastListView(
                uiStatePagination = uiStatePagination,
                loadMorePodcasts = {},
                onCLick = {}
            )
        }
    }
}