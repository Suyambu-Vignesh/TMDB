package com.tmdb.app.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.tmdb.app.R
import com.tmdb.app.core.principle.ImageUtils
import com.tmdb.app.core.principle.deeplink.Deeplink
import com.tmdb.app.core.principle.usecase.GenericFailure
import com.tmdb.app.core.principle.usecase.Result
import com.tmdb.app.databinding.FragmentDetailBinding
import com.tmdb.app.databinding.ViewLabelBinding
import com.tmdb.app.detail.model.Genres
import com.tmdb.app.detail.model.ShowDetail
import com.tmdb.app.detail.model.SpokenLanguages
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

/**
 * View to give detail insight about the Movie and TV-Shows
 */
@AndroidEntryPoint
class ShowDetailFragment : Fragment() {

    companion object {
        internal const val ARGUMENT_MOVIE_ID = "ARGUMENT_MOVIE_ID"
    }

    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: ShowDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadShowDetail()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**
         * View Cycle Observer which look for state change and update the view based on the state.
         * It will emit one of three state
         *      1) Loading State
         *      2) Response of [ShowDetail]
         *      3) Error
         */
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getShowDetailStateFlow().collectLatest {
                    if (_binding == null) {
                        return@collectLatest
                    }

                    when {
                        it is Result.Loading<*> -> {
                            binding.viewProgress.root.visibility = View.VISIBLE
                            binding.viewError.visibility = View.GONE
                        }

                        it.data != null -> {
                            renderDetails(it.data)
                        }

                        else -> {
                            renderError(it)
                        }
                    }
                }
            }
        }
    }

    /**
     * Helper function to render the Error based on [Failure]
     *
     * @param result [Result<ShowDetail>]
     */
    private fun renderError(result: Result<ShowDetail>) {
        binding.viewProgress.root.visibility = View.GONE
        binding.viewError.visibility = View.VISIBLE
        binding.viewError.setError(
            result as? Result.Error<*> ?: Result.Error<ShowDetail>(GenericFailure())
        ) {
            loadShowDetail()
        }
    }

    /**
     * Helper function to update the state change and request new data in back in pipeline
     */
    private fun loadShowDetail() {
        arguments?.getInt(ARGUMENT_MOVIE_ID)?.let {
            viewModel.getShowDetail(it)
        }
    }

    /**
     * Helper function to render the Detail page
     *
     * @param showDetail [ShowDetail]
     */
    private fun renderDetails(showDetail: ShowDetail) {
        binding.viewProgress.root.visibility = View.GONE
        binding.viewError.visibility = View.GONE

        binding.showDetailTitle.text = showDetail.originalTitle
        loadImage(showDetail.backdropPath)
        loadUseScore(showDetail.voteAverage)
        loadImdbInfo(showDetail.imdbId)
        loadGenres(showDetail.genres)
        loadReleaseDateAndStatus(showDetail)
        loadLanguages(showDetail.spokenLanguages)
        binding.textDetail.text = showDetail.overview
    }

    /**
     * Helper function to render if the image url is not null
     *
     * @param imageUrl [String]
     */
    private fun loadImage(imageUrl: String?) {
        if (context != null && imageUrl != null) {
            val imgUrl = ImageUtils.getThumbnailImageSize(
                binding.showImagePoster.context,
                imageUrl
            )
            Glide.with(binding.showImagePoster.context)
                .load(imgUrl).centerCrop()
                .placeholder(R.drawable.ic_placeholder).into(binding.showImagePoster)
        }
    }

    /**
     * Helper Fun to render the Popularity Score if the score is not null and not zero
     *
     * @param score Popularity Score
     */
    private fun loadUseScore(score: Double?) {
        if (score == null || score == 0.0) {
            binding.viewGroupPopularity.root.visibility = View.GONE
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

    /**
     * Helper Fun to render the IMDB Button if the imdb Id is present or hide the button
     *
     * @param imdbId imdb id of the show.
     */
    private fun loadImdbInfo(imdbId: String?) {
        if (imdbId == null) {
            binding.imdb.visibility = View.GONE
        } else {
            binding.imdb.visibility = View.VISIBLE
            binding.imdb.setOnClickListener {
                if (context != null) {
                    Deeplink.handleWebUrl(
                        getString(R.string.str_imdb_url, imdbId),
                        context
                    )
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
        if (genres.isNullOrEmpty()) {
            binding.viewGenres.visibility = View.GONE
            return
        }

        binding.viewGenres.visibility = View.VISIBLE

        for (genre in genres) {
            if (genre?.name == null) {
                continue
            }

            val viewBinding = ViewLabelBinding.inflate(layoutInflater)
            binding.viewGenresCollection.addView(
                viewBinding.root
            )

            viewBinding.textLabel.text = genre.name
        }
    }

    /**
     * Helper funtion to load a set of movie metadata like release status, release date, runing time
     *
     * @param showDetail [ShowDetail]
     */
    private fun loadReleaseDateAndStatus(showDetail: ShowDetail) {
        showDetail.releaseDate?.let {
            binding.releaseDate.visibility = View.VISIBLE
            binding.releaseDate.text = getString(R.string.str_release_date, it)
        } ?: kotlin.run {
            binding.releaseDate.visibility = View.GONE
        }

        showDetail.runtime?.let {
            binding.runningTime.visibility = View.VISIBLE
            binding.runningTime.text = getString(R.string.str_running_time, it.toString())
        } ?: kotlin.run {
            binding.runningTime.visibility = View.GONE
        }

        showDetail.status?.let {
            binding.viewStatus.root.visibility = View.VISIBLE
            binding.viewStatus.textLabel.text = it
            showDetail.getStatusColor()?.let { color ->
                binding.viewStatus.textLabel.setBackgroundResource(color)
                context?.resources?.getColor(R.color.white_1)?.let { color ->
                    binding.viewStatus.textLabel.setTextColor(color)
                }
            }
        } ?: kotlin.run {
            binding.viewStatus.root.visibility = View.GONE
        }

        showDetail.homepage?.let { homeLink ->
            binding.homeLink.setOnClickListener {
                Deeplink.handleWebUrl(
                    homeLink,
                    context
                )
            }
            binding.homeLink.visibility = View.VISIBLE
        } ?: kotlin.run {
            binding.homeLink.visibility = View.GONE
        }
    }

    /**
     * Helper fun to load the Languages
     *
     * @param spokenLanguages collection of [SpokenLanguages]
     */
    private fun loadLanguages(spokenLanguages: ArrayList<SpokenLanguages?>?) {
        if (spokenLanguages.isNullOrEmpty()) {
            binding.viewLanguagesLabel.visibility = View.GONE
            return
        }

        binding.viewLanguagesLabel.visibility = View.VISIBLE

        for (lang in spokenLanguages) {
            if (lang == null || lang.name.isNullOrEmpty()) {
                continue
            }

            val viewBinding = ViewLabelBinding.inflate(layoutInflater)
            binding.viewLanguagesCollection.addView(
                viewBinding.root
            )

            viewBinding.textLabel.text = lang.name
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
