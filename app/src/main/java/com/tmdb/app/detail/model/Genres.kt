package com.tmdb.app.detail.model

import com.google.gson.annotations.SerializedName
import com.tmdb.app.detail.model.api.ShowGenre

data class Genres(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null
) : ShowGenre {
    override fun getShowGenre(): String {
        return name ?: ""
    }
}
