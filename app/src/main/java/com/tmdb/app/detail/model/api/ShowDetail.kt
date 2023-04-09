package com.tmdb.app.detail.model.api

import com.tmdb.app.R
import com.tmdb.app.detail.model.ShowDetailImpl

/**
 * ViewData representation of Show Detail screen
 */
interface ShowDetail {
    /**
     * Method to get the showoverview
     *
     * @return overview or null
     */
    fun getShowOverview(): String?

    /**
     * Method to get the Movie Title
     *
     * @return show title or null
     */
    fun getShowTitle(): String?

    /**
     * Helper method to get the image url
     *
     * @return url as string or null
     */
    fun getImageUrlPath(): String?

    /**
     * Method to get the Show Customer ratting/score
     *
     * @return the ratting as double
     */
    fun getShowScore(): Double?

    /**
     * Method to get the show IMDB id
     *
     * @return IMDB id as string or null
     */
    fun getShowImdbId(): String?

    /**
     * Method to get the Release Date
     *
     * @return the release date as string or null
     */
    fun getShowReleaseDate(): String?

    /**
     * Method to get the Show's Running Time in Minutes.
     *
     * @return the Running Time as string or null
     */
    fun getShowRunningTime(): String?

    /**
     * Method to get the Show Page destination url
     *
     * @return Page destination url or null
     */
    fun getShowsHomePageDestination(): String?

    /**
     * Helper method to get the list of Genere or null. The Genre will be added only if that is
     * not null or empty.
     *
     * @return List of [ShowGenre] or null
     */
    fun getShowGenre(): ArrayList<ShowGenre>?

    /**
     * Helper method to get the list of Languages or null
     *
     * @return Language or null
     */
    fun getShowLanguages(): ArrayList<ShowLanguage>?

    /**
     * Method to get the Show's Release status
     *
     * @return the Release status as string or null
     */
    fun getShowReleaseStatus(): String?

    /**
     * Method to convert the show status to its color rep
     *
     * @return [ColorRes]
     */
    fun getStatusColor(): Int? {
        if (getShowReleaseStatus() == null) {
            return null
        }

        return when (getShowReleaseStatus()) {
            ShowDetailImpl.STATUS_PLANNED -> R.color.status_planned
            ShowDetailImpl.STATUS_IN_PRODUCTION -> R.color.status_in_prod
            ShowDetailImpl.STATUS_POST_PRODUCTION -> R.color.status_post_prod
            ShowDetailImpl.STATUS_RELEASED -> R.color.status_released
            ShowDetailImpl.STATUS_CANCELED -> R.color.status_cancelled
            else -> R.color.light_gray
        }
    }
}

/**
 * ViewData representation of Show Detail Genre.
 */
interface ShowGenre {

    /**
     * Genre Info of the show
     *
     * @return Genre as string or empty string.
     */
    fun getShowGenre(): String
}

/**
 * Holds Information about the show Languages
 */
interface ShowLanguage {

    /**
     * Method to get the Language as string
     *
     * @return Language as string or empty string.
     */
    fun getLanguage(): String
}
