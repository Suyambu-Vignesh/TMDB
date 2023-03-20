package com.tmdb.app.home

/**
 * Helps to give a detail about the show config
 */
sealed class ShowConfig() {
    class PopularMovieShowConfig : ShowConfig()
}
