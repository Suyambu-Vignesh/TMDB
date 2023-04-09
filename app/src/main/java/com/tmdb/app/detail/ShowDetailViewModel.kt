package com.tmdb.app.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdb.app.core.principle.usecase.Result
import com.tmdb.app.detail.model.api.ShowDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for [ShowDetailFragment]
 */
@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    private val getShowDetailUseCase: GetShowDetailUseCase
) : ViewModel() {

    private val showsDetailStateFlow: MutableStateFlow<Result<ShowDetail>> =
        MutableStateFlow(Result.Loading())

    /**
     * Helper function to get the ShowDetail info
     *
     * @param showId - Show detail
     */
    internal fun getShowDetail(showId: Int) {
        viewModelScope.launch {
            showsDetailStateFlow.value = Result.Loading()
            getShowDetailUseCase.performTask(showId).distinctUntilChanged().collect {
                showsDetailStateFlow.value = it
            }
        }
    }

    internal fun getShowDetailStateFlow(): StateFlow<Result<ShowDetail>> {
        return showsDetailStateFlow
    }
}
