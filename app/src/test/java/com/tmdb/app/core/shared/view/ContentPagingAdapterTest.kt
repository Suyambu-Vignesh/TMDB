package com.tmdb.app.core.shared.view

import com.tmdb.app.core.principle.model.ContentModuleModel
import com.tmdb.app.core.shared.view.viewholderdelegates.ContentPagingAdapter
import com.tmdb.app.core.shared.view.viewholderdelegates.RecyclerViewHolderDelegateManager
import io.mockk.mockk
import org.junit.Test

/**
 * Testsuite for [ContentPagingAdapter]
 */
class ContentPagingAdapterTest {

    private val delegateManager = mockk<RecyclerViewHolderDelegateManager<ContentModuleModel>>()

    @Test
    fun test_onCreateViewHolder_when_there_is_an_item_for_position() {
        /*every { delegateManager.onCreateViewHolder(any(), any()) } returns mockk()

        val contentPagingAdapter: ContentPagingAdapter = ContentPagingAdapter(delegateManager)

        Truth.assertThat(contentPagingAdapter.onCreateViewHolder(mockk(), mockk())).isNotNull()

        verify(exactly = 1) { delegateManager.onCreateViewHolder(any(), any()) }*/
    }
}
