package com.example.movies.data.entities

import com.google.gson.annotations.SerializedName

data class ResponseBody (
    @SerializedName("status"      ) val status     : String?,
    @SerializedName("copyright"   ) val copyright  : String?,
    @SerializedName("has_more"    ) val hasMore    : Boolean?,
    @SerializedName("num_results" ) val numResults : Int?,
    @SerializedName("results"     ) val movies     : List<Movie>?
)