package com.tmdb.app.core.shared.view.viewholderdelegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tmdb.app.R
import com.tmdb.app.core.principle.getResId
import com.tmdb.app.core.principle.model.ContentModuleModel
import com.tmdb.app.databinding.ViewShowInfoBinding
import com.tmdb.app.home.model.PlaceHolderInfo

/**
 * Delegate For the Show's Placeholder Tile
 *
 * @param layoutInflater [LayoutInflater],
 * @param onClickListener Click Listener
 */
class PlaceholderContentDelegate(
    private val layoutInflater: LayoutInflater,
    private val onClickListener: RecyclerViewHolderClickListener<ContentModuleModel>?
) : ViewHolderDelegate<PlaceHolderInfo, PlaceholderContentDelegate.PlaceHolderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolderViewHolder {
        return PlaceHolderViewHolder(ViewShowInfoBinding.inflate(layoutInflater, parent, false))
    }

    override fun <DataType, ViewHolderType> onBindViewHolder(
        holder: ViewHolderType,
        position: Int,
        payLoad: DataType
    ) {
        (holder as PlaceHolderViewHolder).setContent(payLoad as PlaceHolderInfo)
    }

    /**
     * ViewHolder for the Show Info.
     *
     * @param viewBinding [ViewShowInfoBinding] binding rep of Show Info.
     */
    inner class PlaceHolderViewHolder(private val viewBinding: ViewShowInfoBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        private var content: PlaceHolderInfo? = null

        init {
            viewBinding.root.setOnClickListener {
                content?.let {
                    onClickListener?.onItemClicked(it)
                }
            }
        }

        internal fun setContent(item: PlaceHolderInfo) {
            val context = viewBinding.imageShowThumbnail.context

            if (context != null) {
                content = item
                viewBinding.textShowTitle.setBackgroundResource(context.getResId(R.attr.colorForeground))
                viewBinding.textReleaseDate.setBackgroundResource(context.getResId(R.attr.colorForeground))

                viewBinding.imageShowThumbnail.setImageResource(R.drawable.ic_placeholder)
            }
        }
    }
}
