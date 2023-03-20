package com.tmdb.app.core.shared.view.viewholderdelegates

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Helps to create and Bind the ViewHolder. This is based on Adapter delegate [https://github.com/sockeqwe/AdapterDelegates]
 * which encourage composition than abstraction of viewHolder. So the ViewHolder can be distributed.
 */
class RecyclerViewHolderDelegateManager<DataType>(
    private val delegatesMap: HashMap<Int, ViewHolderDelegate<out DataType, out RecyclerView.ViewHolder>>,
    private val fallBackViewHolder: RecyclerView.ViewHolder? = null,
    private val viewType: (position: Int, payload: DataType) -> Int
) {

    /**
     * To get the view type which helps to find the ViewHolder
     *
     * @param position Position of the Item
     * @param payload Object Payload of DataType
     */
    fun getItemViewType(position: Int, payload: DataType): Int {
        return viewType(position, payload)
    }

    /**
     * Created the viewHolder for the provided viewType.
     * When a ViewHoler is not present for a view type the fall back view holder provided by the API
     * caller will be added. If FallBackViewHolder is not Default FallBackViewHolder is added
     *
     * @param parent - Root ViewGroup
     * @param viewType - ViewType for which a viewHolder need to be created
     */
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesMap[viewType]?.onCreateViewHolder(parent, viewType) ?: fallBackViewHolder
            ?: FallBackViewHolder(parent)
    }

    /**
     * To Bind the data to the view holder.
     *
     * @param holder for which we need to bind the data.
     * @param position Position of the item in the Recycler View
     * @param payload Object need to be bind to the view
     */
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payload: DataType) {
        val viewType = viewType(position, payload)
        delegatesMap[viewType]?.onBindViewHolder(holder, position, payload)
    }

    /**
     * Default ViewHolder added as Fallback when no proper viewHolder found for viewType
     */
    private class FallBackViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(View(parent.context))
}

abstract class ViewHolderDelegate<DataType, ViewHolderType : RecyclerView.ViewHolder> {

    /**
     * Created the viewHolder for the provided viewType
     *
     * @param parent - Root ViewGroup
     * @param viewType - ViewType for which a viewHolder need to be created
     */
    abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderType

    /**
     * To Bind the data to the view holder.
     *
     * @param holder for which we need to bind the data.
     * @param position Position of the item in the Recycler View
     * @param payLoad Object need to be bind to the view
     */
    abstract fun <DataType, ViewHolderType> onBindViewHolder(
        holder: ViewHolderType,
        position: Int,
        payLoad: DataType
    )
}

interface RecyclerViewHolderClickListener<DataType> {
    fun onItemClicked(dataType: DataType)
}
