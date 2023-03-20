package com.tmdb.app.core.shared.view

import com.google.common.truth.Truth
import com.tmdb.app.core.principle.model.ContentModuleModel
import com.tmdb.app.core.shared.view.viewholderdelegates.ContentPagingAdapter
import com.tmdb.app.core.shared.view.viewholderdelegates.RecyclerViewHolderDelegateManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test

/**
 * Testsuite for [ContentPagingAdapter]
 */
class ContentPagingAdapterTest {

    private val delegateManager = mockk<RecyclerViewHolderDelegateManager<ContentModuleModel>>()

    @Test
    fun `test onCreateViewHolder when there is an item for position`() {
        every { delegateManager.onCreateViewHolder(any(), any()) } returns mockk()

        val contentPagingAdapter = spyk(ContentPagingAdapter(delegateManager))

        Truth.assertThat(contentPagingAdapter.onCreateViewHolder(mockk(), mockk())).isNotNull()

        verify(exactly = 1) { delegateManager.onCreateViewHolder(any(), any()) }
    }
}
