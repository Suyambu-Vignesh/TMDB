package com.tmdb.app.core.principle

import android.content.Context
import android.util.TypedValue

/**
 * Helper fun to get the [String] from the attrId
 *
 * @return string value from the attrId
 */
fun Context.getStringValue(attrId: Int): String {
    val value = TypedValue()
    this.theme.resolveAttribute(attrId, value, true)
    return this.getString(value.resourceId)
}

/**
 * Helper fun to get the [ResourceId] from the attrId
 *
 * @return string value from the attrId
 */
fun Context.getResId(attrId: Int): Int {
    val value = TypedValue()
    this.theme.resolveAttribute(attrId, value, true)
    return value.resourceId
}
