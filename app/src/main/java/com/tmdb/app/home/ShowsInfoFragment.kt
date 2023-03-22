package com.tmdb.app.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.transition.TransitionManager
import com.tmdb.app.R
import com.tmdb.app.core.principle.model.ContentModuleModel
import com.tmdb.app.core.principle.model.PagingContentModules
import com.tmdb.app.core.principle.usecase.Failure
import com.tmdb.app.core.principle.usecase.GenericFailure
import com.tmdb.app.core.principle.usecase.PageLoadException
import com.tmdb.app.core.shared.view.TmdbVerticalGridLayoutManager
import com.tmdb.app.core.shared.view.viewholderdelegates.ContentPagingAdapter
import com.tmdb.app.core.shared.view.viewholderdelegates.PagingLoadStateAdapter
import com.tmdb.app.core.shared.view.viewholderdelegates.RecyclerViewHolderClickListener
import com.tmdb.app.databinding.FragmentShowsInfoBinding
import com.tmdb.app.detail.view.ShowDetailFragment
import com.tmdb.app.home.model.MovieAndTvShowsInfo
import com.tmdb.app.home.model.getLoadingData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

/**
 * View to Display the list of popular movies
 *
 * Have not created a BaseFragment. This part of design choice as we Dont have a lot of shared component.
 * Can Use Create Write new Components (like analytics) as LifeCyle aware Components. But Application of very large
 * scale may need BaseFragment cases like when We plan limit the Fragment type to extend
 */
@AndroidEntryPoint
class ShowsInfoFragment :
    Fragment(),
    RecyclerViewHolderClickListener<ContentModuleModel>,
    (CombinedLoadStates) -> Unit {

    @Inject
    lateinit var showsInfoAdapter: ContentPagingAdapter

    @Inject
    lateinit var footerPagingLoadStateAdapter: PagingLoadStateAdapter

    @Inject
    lateinit var gridLayoutManager: TmdbVerticalGridLayoutManager

    private val viewModel: ShowsInfoViewModel by viewModels()
    private var _binding: FragmentShowsInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getShowInfo(
            ShowConfig.PopularMovieShowConfig()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowsInfoBinding.inflate(inflater, container, false)
        showsInfoAdapter.addLoadStateListener(this)

        with(binding.viewRecyclerview) {
            this.adapter = showsInfoAdapter.withLoadStateFooter(
                footer = footerPagingLoadStateAdapter
            )

            this.layoutManager = TmdbVerticalGridLayoutManager(
                showsInfoAdapter,
                footerPagingLoadStateAdapter,
                context,
                context?.resources?.getInteger(R.integer.column_span) ?: 2
            )
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                if (showsInfoAdapter.itemCount == 0) {
                    // initial stage load the page
                    showsInfoAdapter.submitData(PagingData.getLoadingData())
                }
                viewModel.getShowInfoFlow().distinctUntilChanged().collectLatest {
                    renderPaginatedResults(it)
                }
            }
        }

        return binding.root
    }

    /**
     * Helper fun to render the Paging recyler view when the state changes to [PagingData]
     *
     * @param modules - [PagingData]
     */
    private fun renderPaginatedResults(modules: PagingContentModules) {
        _binding?.let {
            it.viewError.visibility = View.GONE
            it.viewRecyclerview.visibility = View.VISIBLE
        }
        lifecycleScope.launchWhenCreated {
            _binding?.let {
                showsInfoAdapter.submitData(modules)
            }
        }
    }

    /**
     * Helper fun to render the error when the state change request to render error
     *
     * @param failure [Failure]
     */
    private fun renderError(failure: Failure) {
        _binding?.let {
            TransitionManager.beginDelayedTransition(binding.root)

            it.viewError.visibility = View.VISIBLE
            it.viewRecyclerview.visibility = View.GONE

            it.viewError.setFailure(
                failure
            ) {
                viewModel.getShowInfo(
                    ShowConfig.PopularMovieShowConfig()

                )
            }
        }
    }

    /**
     * Item Click listener of Paging data module view
     *
     * @param data - Module Data which is been tapped
     */
    override fun onItemClicked(data: ContentModuleModel) {
        if (data is MovieAndTvShowsInfo) {

            if (data.id == null) {
                return
            }

            val bundle = Bundle()
            bundle.putInt(ShowDetailFragment.ARGUMENT_MOVIE_ID, data.id)
            findNavController().navigate(R.id.action_to_ShowDetailFragment, bundle)
        }
    }

    /**
     * Callback to update the loadState of Paging. Through this we can reuse the paging state to
     * show error/loading avoid duplicate state to hold the error
     *
     * @param loadState [CombinedLoadStates]
     */
    override fun invoke(loadState: CombinedLoadStates) {
        val errorState = loadState.refresh as? LoadState.Error
        if (errorState != null) {
            val error = errorState.error as? PageLoadException
            renderError(error?.failure ?: GenericFailure())
        }
    }

    /**
     * unBind the null and removeLoadStateListener
     */
    override fun onDestroyView() {
        super.onDestroyView()
        showsInfoAdapter.removeLoadStateListener(this)
        _binding = null
    }
}
