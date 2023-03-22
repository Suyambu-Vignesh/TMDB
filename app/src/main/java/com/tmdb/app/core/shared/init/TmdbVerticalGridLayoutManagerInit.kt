package com.tmdb.app.core.shared.init

import android.content.Context
import androidx.fragment.app.Fragment
import com.tmdb.app.R
import com.tmdb.app.core.shared.view.TmdbVerticalGridLayoutManager
import com.tmdb.app.core.shared.view.viewholderdelegates.ContentPagingAdapter
import com.tmdb.app.core.shared.view.viewholderdelegates.PagingLoadStateAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class TmdbVerticalGridLayoutManagerInit {

    @Provides
    fun providesContext(fragment: Fragment): Context? {
        return fragment.context
    }

    @Provides
    fun providesVerticalGridLayoutManager(
        context: Context?,
        adapter: ContentPagingAdapter,
        footerAdapter: PagingLoadStateAdapter
    ): TmdbVerticalGridLayoutManager {
        return TmdbVerticalGridLayoutManager(
            adapter,
            footerAdapter,
            context,
            context?.resources?.getInteger(R.integer.column_span) ?: 2
        )
    }
}
