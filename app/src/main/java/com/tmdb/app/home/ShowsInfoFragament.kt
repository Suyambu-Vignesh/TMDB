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
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.tmdb.app.R
import com.tmdb.app.core.principle.TmdbLogging
import com.tmdb.app.core.principle.model.ContentModuleModel
import com.tmdb.app.core.principle.model.PagingContentModules
import com.tmdb.app.core.principle.usecase.Result
import com.tmdb.app.core.shared.view.viewholderdelegates.ContentPagingAdapter
import com.tmdb.app.core.shared.view.viewholderdelegates.PagingLoadStateAdapter
import com.tmdb.app.core.shared.view.viewholderdelegates.RecyclerViewHolderClickListener
import com.tmdb.app.databinding.FragmentShowsInfoBinding
import com.tmdb.app.detail.ShowDetailFragment
import com.tmdb.app.home.model.MovieAndTvShowsInfo
import com.tmdb.app.home.model.decideOnState
import com.tmdb.app.home.model.getLoadingData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

/**
 * View to Display the list of popular movies
 */
@AndroidEntryPoint
class ShowsInfoFragament : Fragment(), RecyclerViewHolderClickListener<ContentModuleModel> {

    @Inject
    lateinit var showsInfoAdapter: ContentPagingAdapter

    @Inject
    lateinit var headerPagingLoadStateAdapter: PagingLoadStateAdapter

    @Inject
    lateinit var footerPagingLoadStateAdapter: PagingLoadStateAdapter

    private var _binding: FragmentShowsInfoBinding? = null
    private val viewModel: ShowsInfoViewModel by viewModels()

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
        binding.viewRecyclerview.adapter = showsInfoAdapter.withLoadStateFooter(
            footer = footerPagingLoadStateAdapter
        )
        binding.viewRecyclerview.layoutManager = GridLayoutManager(context, 2, VERTICAL, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                showsInfoAdapter.submitData(PagingData.getLoadingData())
                viewModel.getShowInfoFlow().distinctUntilChanged().collectLatest {
                    showResult(it)
                }

                viewModel.getShowsStateFlow().collectLatest {
                    updateState(it)
                }

                showsInfoAdapter.addLoadStateListener { loadState ->
                    loadState.decideOnState(
                        showsInfoAdapter,
                        showLoading = {
                            TmdbLogging.info(toString(), "recyler view is loading")
                        },
                        showEmptyState = { visible ->
                            TmdbLogging.info(
                                toString(),
                                "recyler view is loading" + visible
                            )
                        },
                        showError = { message ->
                            TmdbLogging.info(
                                toString(),
                                "recyler view is loading" + message
                            )
                        }
                    )
                }
            }
        }
    }

    private fun updateState(result: Result<PagingData<ContentModuleModel>>) {
        if (result is Result.Loading<*>) {
            lifecycleScope.launchWhenCreated {
                _binding?.let {
                    showsInfoAdapter.submitData(PagingData.getLoadingData())
                }
            }
        } else if (result is Result.Error<*>) {
            _binding?.let {
                it.viewError.setError(result) {
                    viewModel.getShowInfo(
                        ShowConfig.PopularMovieShowConfig()
                    )
                }
            }
        }
    }

    private fun showResult(modules: PagingContentModules) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(dataType: ContentModuleModel) {
        if (dataType is MovieAndTvShowsInfo) {

            if (dataType.id == null) {
                return
            }

            val bundle = Bundle()
            bundle.putInt(ShowDetailFragment.ARGUMENT_MOVIE_ID, dataType.id)
            findNavController().navigate(R.id.action_to_ShowDetailFragment, bundle)
        }
    }
}
