package com.tmdb.app.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tmdb.app.core.principle.model.PagingContentModules
import com.tmdb.app.core.shared.view.viewholderdelegates.ContentPagingAdapter
import com.tmdb.app.databinding.FragmentShowsInfoBinding

/**
 * View to Display the list of popular movies
 */

class ShowsInfoFragament : Fragment() {

    lateinit var showsInfoAdapter: ContentPagingAdapter

    private var _binding: FragmentShowsInfoBinding? = null
    private val viewModel: ShowInfoViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowsInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showResult(modules: PagingContentModules) {
        _binding?.let {
            it.viewProgress.visibility = View.GONE
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
}
