package com.mangoobyte.audiobookstest.podcast.podcastlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mangoobyte.audiobookstest.data.UiState
import com.mangoobyte.audiobookstest.data.repository.PodCastRepository
import com.mangoobyte.audiobookstest.podcast.podcastlist.datamodel.PaginationUiState
import com.mangoobyte.audiobookstest.podcast.podcastlist.datamodel.PodCastListResponse
import com.mangoobyte.audiobookstest.podcast.podcastlist.datamodel.Podcasts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodCastListViewModel @Inject constructor(
    private val podCastRepository: PodCastRepository
): ViewModel() {

    private var _uiStatePagination: MutableStateFlow<PaginationUiState> = MutableStateFlow(
        PaginationUiState()
    )
    internal val uiStatePagination = _uiStatePagination.asStateFlow()

    init {
        getPodCastList()
    }

    private fun getPodCastList(
        nextPage: Int = 0
    ) {
        _uiStatePagination.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val response = podCastRepository.getPodCastList(page = nextPage)
            if (response.isSuccess) {
                _uiStatePagination.update {
                    it.copy(
                        podcasts = (it.podcasts + response.getOrNull()?.podcasts.orEmpty()),
                        nextPage = response.getOrNull()?.nextPageNumber,
                        isLoading = false
                    )
                }
            } else {
                _uiStatePagination.update {
                    it.copy(
                        isLoading = false,
                        error = true
                    )
                }
            }
        }
    }

    internal fun loadMorePodcasts() {
        getPodCastList(
            nextPage = _uiStatePagination.value.nextPage ?: 0
        )
    }

    internal fun getPodCastInformation(podCastId: String): Podcasts? = _uiStatePagination.value.podcasts.find {
        it.id == podCastId
    }

    internal fun updateFavouritePodcast(
        podcastId : String,
        isFavourite: Boolean
    ) {

        _uiStatePagination.update { currentList ->
            val updatedList = currentList.podcasts.toMutableList()
            val index = updatedList.indexOfFirst { it.id == podcastId  }
            if (index != -1){
                updatedList[index] = updatedList[index].copy(
                    isFavorite = isFavourite
                )
            }
            currentList.copy(podcasts = updatedList)
        }
    }
}