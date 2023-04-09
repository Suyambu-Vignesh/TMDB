package com.tmdb.app.utils

import com.tmdb.app.BuildConfig
import java.lang.reflect.Field
import java.lang.reflect.Modifier

object MockBuildConfig {
    internal fun mockBuildConfigDebug(fieldString: String, value: Boolean) {
        val field = BuildConfig::class.java.getField(fieldString)
        field.isAccessible = true
        Field::class.java.getDeclaredField("modifiers").also {
            it.isAccessible = true
            it.set(field, field.modifiers and Modifier.FINAL.inv())
        }
        field.set(null, value)
    }
}
