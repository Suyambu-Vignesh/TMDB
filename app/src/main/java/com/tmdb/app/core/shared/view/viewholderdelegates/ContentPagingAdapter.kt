package com.tmdb.app.core.shared.view.viewholderdelegates

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tmdb.app.core.principle.model.ContentModuleModel
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Inject

/**
 * Content Module Adapter for Paging Data.
 * [RecyclerView.Adapter] which treat the RecyclerView's Items as Module [ContentModuleModel].
 * To help this action we need to provide [RecyclerViewHolderDelegateManager]. RecyclerViewHolderDelegateManager
 * is a collection Of [ViewHolderDelegate] that help to create and [RecyclerView.ViewHolder]
 *
 * @property delegateManager - List of whitelisted delegates [RecyclerViewHolderDelegateManager]
 */
@Module
@InstallIn(FragmentComponent::class)
class ContentPagingAdapter @Inject constructor(private val delegateManager: RecyclerViewHolderDelegateManager<ContentModuleModel>) :
    PagingDataAdapter<ContentModuleModel, RecyclerView.ViewHolder>(DiffAdapter()) {

    internal class DiffAdapter : DiffUtil.ItemCallback<ContentModuleModel>() {
        override fun areItemsTheSame(
            oldItem: ContentModuleModel,
            newItem: ContentModuleModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ContentModuleModel,
            newItem: ContentModuleModel
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            delegateManager.onBindViewHolder(holder, position, it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateManager.onCreateViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.let {
            delegateManager.getItemViewType(position, it)
        } ?: -1 // will lead to fall back layout
    }
}
