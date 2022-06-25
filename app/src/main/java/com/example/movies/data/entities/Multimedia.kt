package com.example.movies.data.entities

import com.google.gson.annotations.SerializedName

data class Multimedia (

    @SerializedName("type"   ) val type   : String?,
    @SerializedName("src"    ) val src    : String?,
    @SerializedName("height" ) val height : Int?,
    @SerializedName("width"  ) val width  : Int?

)