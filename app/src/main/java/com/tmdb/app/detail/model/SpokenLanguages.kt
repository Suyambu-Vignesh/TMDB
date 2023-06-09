package com.tmdb.app.detail.model

import com.google.gson.annotations.SerializedName
import com.tmdb.app.detail.model.api.ShowLanguage

data class SpokenLanguages(
    @SerializedName("english_name")
    var englishName: String? = null,
    @SerializedName("iso_639_1")
    var iso6391: String? = null,
    @SerializedName("name")
    var name: String? = null
) : ShowLanguage {
    override fun getLanguage(): String {
        return name ?: ""
    }
}
