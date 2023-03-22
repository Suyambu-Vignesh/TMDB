package com.tmdb.app.detail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.tmdb.app.R
import com.tmdb.app.core.principle.ImageUtils
import com.tmdb.app.core.principle.usecase.GenericFailure
import com.tmdb.app.core.principle.usecase.Result
import com.tmdb.app.databinding.FragmentDetailBinding
import com.tmdb.app.detail.ShowDetailViewModel
import com.tmdb.app.detail.model.ShowDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.view_error.view.*
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
        TransitionManager.beginDelayedTransition(binding.root)

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
        TransitionManager.beginDelayedTransition(binding.root)

        binding.viewProgress.root.visibility = View.GONE
        binding.viewError.visibility = View.GONE

        loadImage(showDetail.backdropPath)

        binding.showDetailScrollView.renderDetails(showDetail)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
