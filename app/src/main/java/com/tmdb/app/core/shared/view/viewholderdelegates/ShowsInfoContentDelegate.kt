package com.tmdb.app.core.shared.view.viewholderdelegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tmdb.app.R
import com.tmdb.app.core.principle.ImageUtils
import com.tmdb.app.core.principle.model.ContentModuleModel
import com.tmdb.app.databinding.ViewShowInfoBinding
import com.tmdb.app.home.model.MovieAndTvShowsInfo

/**
 * Delegate For the Show Info Tile
 *
 * @param layoutInflater [LayoutInflater],
 * @param onClickListener Click Listener
 */
class ShowsInfoContentDelegate(
    private val layoutInflater: LayoutInflater,
    private val onClickListener: RecyclerViewHolderClickListener<ContentModuleModel>?
) : ViewHolderDelegate<MovieAndTvShowsInfo, ShowsInfoContentDelegate.ShowInfoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowInfoViewHolder {
        return ShowInfoViewHolder(ViewShowInfoBinding.inflate(layoutInflater, parent, false))
    }

    override fun <DataType, ViewHolderType> onBindViewHolder(
        holder: ViewHolderType,
        position: Int,
        payLoad: DataType
    ) {
        (holder as ShowInfoViewHolder).setContent(payLoad as MovieAndTvShowsInfo)
    }

    /**
     * ViewHolder for the Show Info.
     *
     * @param viewBinding [ViewShowInfoBinding] binding rep of Show Info.
     */
    inner class ShowInfoViewHolder(private val viewBinding: ViewShowInfoBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        private var content: MovieAndTvShowsInfo? = null

        init {
            viewBinding.root.setOnClickListener {
                content?.let {
                    onClickListener?.onItemClicked(it)
                }
            }
        }

        internal fun setContent(item: MovieAndTvShowsInfo) {
            content = item
            viewBinding.textShowTitle.text = item.originalTitle
            viewBinding.textReleaseDate.text = item.releaseDate

            if (item.voteAverage != null && item.voteAverage != 0.0) {
                viewBinding.viewGroupPopularity.root.visibility = View.VISIBLE
                val popularity = item.voteAverage * 10
                viewBinding.viewGroupPopularity.textPopularity.text = popularity.toString()
                viewBinding.viewGroupPopularity.progressPopularity.setProgress(popularity.toInt())
            }

            val context = viewBinding.imageShowThumbnail.context

            if (context != null && item.backdropPath != null) {
                val imgUrl = ImageUtils.getThumbnailImageSize(
                    viewBinding.imageShowThumbnail.context,
                    item.backdropPath
                )
                Glide.with(viewBinding.imageShowThumbnail.context)
                    .load(imgUrl).centerCrop()
                    .placeholder(R.drawable.ic_placeholder).into(viewBinding.imageShowThumbnail)
            }
        }
    }
}
