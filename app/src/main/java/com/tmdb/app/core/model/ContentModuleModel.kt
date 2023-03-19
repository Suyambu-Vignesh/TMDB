package com.tmdb.app.core.model

/**
 * View Representation of List Item Model
 */
interface ContentModuleModel {
    fun getType(): ModuleType
    fun getId(): String
}

/**
 * Help to decide the type of list item
 */
enum class ModuleType(val type: Int) {
    MOVIE_TILE(1)
}
