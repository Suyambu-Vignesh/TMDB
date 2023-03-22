package com.tmdb.app.core.shared.view

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tmdb.app.core.shared.view.viewholderdelegates.ContentPagingAdapter
import com.tmdb.app.core.shared.view.viewholderdelegates.PagingLoadStateAdapter
import javax.inject.Inject

class TmdbVerticalGridLayoutManager @Inject constructor(
    private val adapter: ContentPagingAdapter,
    private val footerAdapter: PagingLoadStateAdapter,
    context: Context?,
    colCount: Int
) : GridLayoutManager(context, colCount, RecyclerView.VERTICAL, false) {

    init {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == adapter.itemCount && footerAdapter.itemCount > 0) {
                    colCount
                } else {
                    1
                }
            }
        }
    }
}
