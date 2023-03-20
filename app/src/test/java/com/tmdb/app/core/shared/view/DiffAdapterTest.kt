package com.tmdb.app.core.shared.view

import com.google.common.truth.Truth
import com.tmdb.app.core.principle.model.ContentModuleModel
import com.tmdb.app.core.principle.model.ModuleType
import com.tmdb.app.core.shared.view.viewholderdelegates.ContentPagingAdapter
import org.junit.Test

/**
 * Testsuite of [DiffAdapter]
 */
class DiffAdapterTest {

    @Test
    fun `test areItemsTheSame Check if the obj are same`() {
        val diffAdapter = ContentPagingAdapter.DiffAdapter()
        val obj1 = TestContentModuleModel("title1")
        val obj2 = obj1
        Truth.assertThat(diffAdapter.areItemsTheSame(obj1, obj2)).isTrue()
        Truth.assertThat(diffAdapter.areItemsTheSame(obj1, TestContentModuleModel("title1")))
            .isFalse()
        Truth.assertThat(diffAdapter.areItemsTheSame(obj1, TestContentModuleModel("title2")))
            .isFalse()
    }

    @Test
    fun `test areContentsTheSame`() {
        val diffAdapter = ContentPagingAdapter.DiffAdapter()
        val obj1 = TestContentModuleModel("title1")
        val obj2 = obj1
        Truth.assertThat(diffAdapter.areContentsTheSame(obj1, obj2)).isTrue()
        Truth.assertThat(diffAdapter.areContentsTheSame(obj1, TestContentModuleModel("title1")))
            .isTrue()
        Truth.assertThat(diffAdapter.areContentsTheSame(obj1, TestContentModuleModel("title2")))
            .isFalse()
    }
}

class TestContentModuleModel(private val title: String) : ContentModuleModel {
    override fun getType(): ModuleType {
        return ModuleType.MOVIE_TILE
    }

    override fun hashCode(): Int {
        return java.util.Objects.hash(this.title, this.getType())
    }
}
