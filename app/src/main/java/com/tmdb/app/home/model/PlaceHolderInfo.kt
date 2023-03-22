package com.tmdb.app.home.model

import androidx.paging.PagingData
import com.tmdb.app.core.principle.model.ContentModuleModel
import com.tmdb.app.core.principle.model.ModuleType

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

    return from(list)
}
