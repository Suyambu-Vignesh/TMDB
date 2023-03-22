package com.tmdb.app.core.shared.init

import android.view.LayoutInflater
import com.tmdb.app.core.shared.view.viewholderdelegates.ContentPagingAdapter
import com.tmdb.app.core.shared.view.viewholderdelegates.PagingLoadStateAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class PagingLoadStateAdapterInit {

    @Provides
    fun providesPagingLoadStateAdapter(
        layoutInflater: LayoutInflater,
        adapter: ContentPagingAdapter
    ): PagingLoadStateAdapter {
        return PagingLoadStateAdapter(
            layoutInflater,
            adapter
        )
    }
}
