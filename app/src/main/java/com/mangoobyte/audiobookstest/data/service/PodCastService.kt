package com.mangoobyte.audiobookstest.data.service

import com.mangoobyte.audiobookstest.API_VERSION
import com.mangoobyte.audiobookstest.podcast.podcastlist.datamodel.PodCastListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Service class for adding all list of service calls..
 */
interface PodCastService {
    @GET("${API_VERSION}/best_podcasts")
    suspend fun getPodCastList(
        @Query("page") page: Int
    ): Response<PodCastListResponse>
}