package com.example.movies.data.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie (

    @SerializedName("display_title"    ) val displayTitle    : String?,
    @SerializedName("mpaa_rating"      ) val mpaaRating      : String?,
    @SerializedName("critics_pick"     ) val criticsPick     : Int?   ,
    @SerializedName("byline"           ) val byline          : String?,
    @SerializedName("headline"         ) val headline        : String?,
    @SerializedName("summary_short"    ) val summaryShort    : String?,
    @SerializedName("publication_date" ) val publicationDate : String?,
    @SerializedName("opening_date"     ) val openingDate     : String?,
    @SerializedName("date_updated"     ) val dateUpdated     : String?,
    @SerializedName("link"             ) val link            : Link?,
    @SerializedName("multimedia"       ) val multimedia      : Multimedia?

): Serializable