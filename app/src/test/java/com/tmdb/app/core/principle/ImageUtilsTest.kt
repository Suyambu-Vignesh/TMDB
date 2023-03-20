package com.tmdb.app.core.principle

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.DisplayMetrics.DENSITY_HIGH
import android.util.DisplayMetrics.DENSITY_XHIGH
import android.util.DisplayMetrics.DENSITY_XXHIGH
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class ImageUtilsTest {

    @Test
    fun `test getThumbnailImageSize for default dpi`() {
        val context = mockk<Context>()
        val displayMetrics = mockk<DisplayMetrics>()
        val resource = mockk<Resources>()

        every { context.resources } returns resource
        every { resource.displayMetrics } returns displayMetrics

        Truth.assertThat(ImageUtils.getThumbnailImageSize(context)).isEqualTo("w500")
    }

    @Test
    fun `test getThumbnailImageSize for Hdpi`() {
        val context = mockk<Context>()
        val displayMetrics = mockk<DisplayMetrics>()
        displayMetrics.densityDpi = DENSITY_HIGH
        val resource = mockk<Resources>()

        every { context.resources } returns resource
        every { resource.displayMetrics } returns displayMetrics

        Truth.assertThat(ImageUtils.getThumbnailImageSize(context)).isEqualTo("w200")
    }

    @Test
    fun `test getThumbnailImageSize for XHdpi`() {
        val context = mockk<Context>()
        val displayMetrics = mockk<DisplayMetrics>()
        displayMetrics.densityDpi = DENSITY_XHIGH
        val resource = mockk<Resources>()

        every { context.resources } returns resource
        every { resource.displayMetrics } returns displayMetrics

        Truth.assertThat(ImageUtils.getThumbnailImageSize(context)).isEqualTo("w300")
    }

    @Test
    fun `test getThumbnailImageSize for XXHdpi`() {
        val context = mockk<Context>()
        val displayMetrics = mockk<DisplayMetrics>()
        displayMetrics.densityDpi = DENSITY_XXHIGH
        val resource = mockk<Resources>()

        every { context.resources } returns resource
        every { resource.displayMetrics } returns displayMetrics

        Truth.assertThat(ImageUtils.getThumbnailImageSize(context)).isEqualTo("w500")
    }
}
