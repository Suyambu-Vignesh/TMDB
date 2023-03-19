package com.tmdb.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Core Application Instance of [TMDB]
 */
@HiltAndroidApp
class TMDBApplication: Application()