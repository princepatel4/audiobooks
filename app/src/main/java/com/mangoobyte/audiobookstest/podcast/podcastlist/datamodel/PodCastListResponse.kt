package com.mangoobyte.audiobookstest.podcast.podcastlist.datamodel

import com.google.gson.annotations.SerializedName

data class PodCastListResponse(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("name") var name : String? = null,
    @SerializedName("total") var total : Int? = null,
    @SerializedName("has_next") var hasNext : Boolean? = null,
    @SerializedName("podcasts") var podcasts : ArrayList<Podcasts> = arrayListOf(),
    @SerializedName("parent_id") var parentId : Int? = null,
    @SerializedName("page_number") var pageNumber : Int? = null,
    @SerializedName("has_previous") var hasPrevious : Boolean? = null,
    @SerializedName("listennotes_url") var listennotesUrl : String? = null,
    @SerializedName("next_page_number") var nextPageNumber : Int? = null,
    @SerializedName("previous_page_number") var previousPageNumber : Int? = null
)

data class Podcasts (
    @SerializedName("id") var id : String? = null,
    @SerializedName("rss") var rss : String? = null,
    @SerializedName("type") var type : String? = null,
    @SerializedName("email") var email : String? = null,
    @SerializedName("image") var image : String? = null,
    @SerializedName("title") var title : String? = null,
    @SerializedName("country") var country : String? = null,
    @SerializedName("website") var website : String? = null,
    @SerializedName("is_claimed") var isClaimed : Boolean = false,
    @SerializedName("is_favorite") var isFavorite : Boolean = false,
    @SerializedName("language") var language : String? = null,
    @SerializedName("genre_ids") var genreIds : ArrayList<Int> = arrayListOf(),
    @SerializedName("itunes_id") var itunesId : Int? = null,
    @SerializedName("publisher") var publisher : String? = null,
    @SerializedName("thumbnail") var thumbnail : String? = null,
    @SerializedName("description") var description : String? = null,
)