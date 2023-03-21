package com.tmdb.app.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tmdb.app.core.principle.model.PagingContentModules
import com.tmdb.app.core.principle.usecase.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * ViewModel for [ShowsInfoFragament]
 */
@HiltViewModel
class ShowsInfoViewModel @Inject constructor(
    private val getShowInfoUseCase: GetShowInfoUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val KEY_SHOW_CONFIG = "KEY_SHOW_CONFIG"
    }

    private val showsInfoStateFlow: MutableStateFlow<Result<PagingContentModules>> =
        MutableStateFlow(Result.Loading())

    private val showsInfoFlow by lazy {
        savedStateHandle.getLiveData(KEY_SHOW_CONFIG, ShowConfig.PopularMovieShowConfig()).asFlow()
            .flatMapLatest {
                showsInfoStateFlow.value =
                    Result.Loading<Result<PagingContentModules>>() as Result<PagingContentModules>
                getShowInfoUseCase.performTask(1)
            }.flatMapLatest {
                flow {
                    showsInfoStateFlow.value = it
                    emit(
                        if (it is Result.Response && it.data is PagingContentModules) {
                            it.data
                        } else {

                            it.failure
                            PagingData.empty()
                        }
                    )
                }
            }.cachedIn(viewModelScope)
    }

    /**
     * Will help to fetch the show info collection for [ShowConfig]
     */
    internal fun getShowInfo(showConfig: ShowConfig) {
        savedStateHandle[KEY_SHOW_CONFIG] = showConfig
    }

    /**
     * Will encapsulate the [MutableStateFlow] to [StateFlow]
     */
    internal fun getShowInfoFlow(): Flow<PagingContentModules> {
        return showsInfoFlow
    }

    /**
     * Will encapsulate the [MutableStateFlow] to [StateFlow]
     */
    internal fun getShowsStateFlow(): MutableStateFlow<Result<PagingContentModules>> {
        return showsInfoStateFlow
    }
}
