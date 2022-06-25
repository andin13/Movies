package com.example.movies.data.entities

import com.google.gson.annotations.SerializedName

data class Link (

    @SerializedName("type"                ) val type              : String?,
    @SerializedName("url"                 ) val url               : String?,
    @SerializedName("suggested_link_text" ) val suggestedLinkText : String?

)