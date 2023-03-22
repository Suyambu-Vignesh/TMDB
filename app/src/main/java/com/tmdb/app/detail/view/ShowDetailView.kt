package com.tmdb.app.detail.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ScrollView
import com.tmdb.app.R
import com.tmdb.app.core.principle.deeplink.Deeplink
import com.tmdb.app.databinding.ViewLabelBinding
import com.tmdb.app.databinding.ViewShowDetailBinding
import com.tmdb.app.detail.model.Genres
import com.tmdb.app.detail.model.ShowDetail
import com.tmdb.app.detail.model.SpokenLanguages

class ShowDetailView : ScrollView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    private var binding: ViewShowDetailBinding? = null

    init {
        binding = ViewShowDetailBinding.inflate(LayoutInflater.from(context), this, true)
    }

    /**
     * Helper function to render the Scroll View section part
     *
     * @param showDetail [ShowDetail]
     */
    internal fun renderDetails(showDetail: ShowDetail) {
        binding?.let {
            it.showDetailTitle.text = showDetail.originalTitle
            it.textDetail.text = showDetail.overview
        }

        loadUseScore(showDetail.voteAverage)
        loadImdbInfo(showDetail.imdbId)
        loadGenres(showDetail.genres)
        loadReleaseDateAndStatus(showDetail)
        loadLanguages(showDetail.spokenLanguages)
    }

    /**
     * Helper Fun to render the Popularity Score if the score is not null and not zero
     *
     * @param score Popularity Score
     */
    private fun loadUseScore(score: Double?) {
        binding?.let { binding ->
            if (score == null || score == 0.0) {

                return
            }

            val popularityScore = (score * 10).toInt()

            binding.viewGroupPopularity.root.visibility = View.VISIBLE
            binding.viewGroupPopularity.textPopularity.text = "$popularityScore.0"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.viewGroupPopularity.progressPopularity.setProgress(popularityScore, true)
            } else {
                binding.viewGroupPopularity.progressPopularity.progress = popularityScore
            }
        }
    }

    /**
     * Helper Fun to render the IMDB Button if the imdb Id is present or hide the button
     *
     * @param imdbId imdb id of the show.
     */
    private fun loadImdbInfo(imdbId: String?) {
        binding?.let { binding ->
            if (imdbId == null) {
                binding.imdb.visibility = View.GONE
            } else {
                binding.imdb.visibility = View.VISIBLE
                binding.imdb.setOnClickListener {
                    if (context != null) {
                        Deeplink.handleWebUrl(
                            context.getString(R.string.str_imdb_url, imdbId),
                            context
                        )
                    }
                }
            }
        }
    }

    /**
     * Helper Fun to load all the Genres If present
     *
     * @param genres collection of Genere
     */
    private fun loadGenres(genres: ArrayList<Genres?>?) {
        binding?.let { binding ->
            if (genres.isNullOrEmpty()) {
                binding.viewGenres.visibility = View.GONE
                return
            }

            binding.viewGenres.visibility = View.VISIBLE

            for (genre in genres) {
                if (genre?.name == null) {
                    continue
                }

                val viewBinding = ViewLabelBinding.inflate(LayoutInflater.from(context))
                binding.viewGenresCollection.addView(
                    viewBinding.root
                )

                viewBinding.textLabel.text = genre.name
            }
        }
    }

    /**
     * Helper funtion to load a set of movie metadata like release status, release date, runing time
     *
     * @param showDetail [ShowDetail]
     */
    private fun loadReleaseDateAndStatus(showDetail: ShowDetail) {
        binding?.let { binding ->
            if (!showDetail.releaseDate.isNullOrEmpty()) {
                binding.releaseDate.visibility = View.VISIBLE
                binding.releaseDate.text =
                    context.getString(R.string.str_release_date, showDetail.releaseDate)
            } else {
                binding.releaseDate.visibility = View.GONE
            }

            showDetail.runtime?.let {
                binding.runningTime.visibility = View.VISIBLE
                binding.runningTime.text =
                    context.getString(R.string.str_running_time, it.toString())
            } ?: kotlin.run {
                binding.runningTime.visibility = View.GONE
            }

            if (!showDetail.status.isNullOrEmpty()) {
                binding.viewStatus.root.visibility = View.VISIBLE
                binding.viewStatus.textLabel.text = showDetail.status
                showDetail.getStatusColor()?.let { color ->
                    binding.viewStatus.textLabel.setBackgroundResource(color)
                    context?.resources?.getColor(R.color.white_1)?.let { color ->
                        binding.viewStatus.textLabel.setTextColor(color)
                    }
                }
            } else {
                binding.viewStatus.root.visibility = View.GONE
            }

            if (!showDetail.homepage.isNullOrEmpty()) {
                binding.homeLink.setOnClickListener {
                    Deeplink.handleWebUrl(
                        showDetail.homepage,
                        context
                    )
                }
                binding.homeLink.visibility = View.VISIBLE
            } else {
                binding.homeLink.visibility = View.GONE
            }
        }
    }

    /**
     * Helper fun to load the Languages
     *
     * @param spokenLanguages collection of [SpokenLanguages]
     */
    private fun loadLanguages(spokenLanguages: ArrayList<SpokenLanguages?>?) {
        binding?.let { binding ->
            if (spokenLanguages.isNullOrEmpty()) {
                binding.viewLanguagesLabel.visibility = View.GONE
                return
            }

            binding.viewLanguagesLabel.visibility = View.VISIBLE

            for (lang in spokenLanguages) {
                if (lang == null || lang.name.isNullOrEmpty()) {
                    continue
                }

                val viewBinding = ViewLabelBinding.inflate(LayoutInflater.from(context))
                binding.viewLanguagesCollection.addView(
                    viewBinding.root
                )

                viewBinding.textLabel.text = lang.name
            }
        }
    }
}
