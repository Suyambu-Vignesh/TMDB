package com.tmdb.app.core.principle.model

import androidx.paging.PagingData

typealias PagingContentModules = PagingData<ContentModuleModel>

/**
 * View Representation of List Item Model
 */
interface ContentModuleModel {
    fun getType(): ModuleType
}

/**
 * Help to decide the type of list item
 */
enum class ModuleType(val type: Int) {
    PLACE_HOLDER_TILE(1), MOVIE_TILE(2)
}
