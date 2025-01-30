package com.mangoobyte.audiobookstest.podcast.podcastlist.datamodel

/**
 * Data class to represent the UI state of the paginated list.
 */
data class PaginationUiState (
    val podcasts: List<Podcasts> = emptyList(),
    val nextPage: Int? = null,
    val isLoading: Boolean = false,
    val error: Boolean = false
)