package com.tmdb.app.core.principle.deeplink

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Helper class to handle the App's Deep link and exterior link
 */
object Deeplink {
    internal fun handleWebUrl(uri: String, context: Context?) {
        if (context == null) {
            return
        }

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context.startActivity(browserIntent)
    }
}
