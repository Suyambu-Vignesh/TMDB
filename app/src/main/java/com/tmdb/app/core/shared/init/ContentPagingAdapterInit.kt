package com.tmdb.app.core.shared.init

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.tmdb.app.core.principle.model.ContentModuleModel
import com.tmdb.app.core.principle.model.ModuleType
import com.tmdb.app.core.shared.view.viewholderdelegates.ContentPagingAdapter
import com.tmdb.app.core.shared.view.viewholderdelegates.PlaceholderContentDelegate
import com.tmdb.app.core.shared.view.viewholderdelegates.RecyclerViewHolderClickListener
import com.tmdb.app.core.shared.view.viewholderdelegates.RecyclerViewHolderDelegateManager
import com.tmdb.app.core.shared.view.viewholderdelegates.ShowsInfoContentDelegate
import com.tmdb.app.core.shared.view.viewholderdelegates.ViewHolderDelegate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class ContentPagingAdapterInit {

    @Provides
    @FragmentScoped
    fun providesContentPagingAdapter(
        delegateManager: RecyclerViewHolderDelegateManager<ContentModuleModel>
    ): ContentPagingAdapter {
        return ContentPagingAdapter(
            delegateManager
        )
    }

    @Provides
    fun provideLayoutInflater(fragment: Fragment): LayoutInflater {
        return fragment.layoutInflater
    }

    @Provides
    fun providesRecyclerViewHolderDelegateManager(layoutInflater: LayoutInflater, fragment: Fragment): RecyclerViewHolderDelegateManager<ContentModuleModel> {
        val hashMap = HashMap<Int, ViewHolderDelegate<out ContentModuleModel, out RecyclerView.ViewHolder>>()

        hashMap[ModuleType.MOVIE_TILE.type] = ShowsInfoContentDelegate(
            layoutInflater,
            fragment as? RecyclerViewHolderClickListener<ContentModuleModel>
        )

        hashMap[ModuleType.PLACE_HOLDER_TILE.type] = PlaceholderContentDelegate(
            layoutInflater,
            fragment as? RecyclerViewHolderClickListener<ContentModuleModel>
        )

        return RecyclerViewHolderDelegateManager(
            delegatesMap = hashMap,
            fallBackViewHolder = null
        ) { position, payload ->
            payload.getType().type
        }
    }
}
