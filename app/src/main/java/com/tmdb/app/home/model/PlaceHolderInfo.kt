package com.tmdb.app.home.model

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.tmdb.app.core.principle.model.ContentModuleModel
import com.tmdb.app.core.principle.model.ModuleType
import com.tmdb.app.core.shared.view.viewholderdelegates.ContentPagingAdapter

class PlaceHolderInfo : ContentModuleModel {
    override fun getType(): ModuleType {
        return ModuleType.PLACE_HOLDER_TILE
    }
}

internal fun PagingData.Companion.getLoadingData(): PagingData<ContentModuleModel> {
    val list = ArrayList<ContentModuleModel>()

    for (index in 0 until 20) {
        list.add(PlaceHolderInfo())
    }

    return PagingData.from(list)
}

internal fun CombinedLoadStates.decideOnState(
    adapter: ContentPagingAdapter,
    showLoading: (Boolean) -> Unit,
    showEmptyState: (Boolean) -> Unit,
    showError: (String) -> Unit
) {
    showLoading(refresh is LoadState.Loading)

    showEmptyState(
        source.append is LoadState.NotLoading &&
            source.append.endOfPaginationReached &&
            adapter.itemCount == 0
    )

    val errorState = source.append as? LoadState.Error
        ?: source.prepend as? LoadState.Error
        ?: source.refresh as? LoadState.Error
        ?: append as? LoadState.Error
        ?: prepend as? LoadState.Error
        ?: refresh as? LoadState.Error

    errorState?.let { showError(it.error.toString()) }
}
