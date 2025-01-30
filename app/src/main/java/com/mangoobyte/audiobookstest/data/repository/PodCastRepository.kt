package com.mangoobyte.audiobookstest.data.repository

import com.mangoobyte.audiobookstest.data.service.PodCastService
import com.mangoobyte.audiobookstest.podcast.podcastlist.datamodel.PodCastListResponse
import javax.inject.Inject

/**
 * Repository class to handle api request and response.
 */
class PodCastRepository @Inject constructor(
    private val podCastService: PodCastService
) {
    suspend fun getPodCastList(
        page: Int = 1
    ): Result<PodCastListResponse> {
        return try {
            val response = podCastService.getPodCastList(
                page = page
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("${response.code()}"))
            } else {
                Result.failure(Exception("${response.code()}"))
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}