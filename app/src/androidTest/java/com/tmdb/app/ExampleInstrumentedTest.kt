package com.tmdb.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.tmdb.app", appContext.packageName)
    }

    class Solution {
        fun canPlaceFlowers(flowerbed: IntArray, n: Int): Boolean {
            var index = 1

            var count = 0

            while (index < flowerbed.size) {
                when {
                    (flowerbed[index] == 1) -> {
                        index += 2
                    }

                    (
                        flowerbed[index] == 0 &&
                            flowerbed[index - 1] == 0 &&
                            (index == flowerbed.size - 1 || flowerbed[index + 1] == 0)
                        ) -> {
                        index += 2

                        count++
                    }

                    else -> {
                        index++
                    }
                }

                if (count >= n) {
                    return true
                }
            }

            return false
        }
    }
}
