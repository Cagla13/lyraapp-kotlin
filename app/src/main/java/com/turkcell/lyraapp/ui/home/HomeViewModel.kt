package com.turkcell.lyraapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.lyraapp.data.repository.FakeMusicRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = FakeMusicRepository()

    private val _state = MutableStateFlow(HomeContract.State())
    val state: StateFlow<HomeContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeContract.Effect>()
    val effect: SharedFlow<HomeContract.Effect> = _effect.asSharedFlow()

    init {
        fetchHomeData()
    }

    fun onEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnPlaylistClicked -> emitEffect(HomeContract.Effect.NavigateToPlaylistDetail(event.playlistId))
            is HomeContract.Event.OnHomeTabClicked -> { /* Zaten Ana Sayfadayız */ }
            is HomeContract.Event.OnSearchTabClicked -> emitEffect(HomeContract.Effect.NavigateToSearch)
            is HomeContract.Event.OnLibraryTabClicked -> emitEffect(HomeContract.Effect.NavigateToLibrary)
            is HomeContract.Event.OnFavoritesTabClicked -> emitEffect(HomeContract.Effect.NavigateToFavorites)
            is HomeContract.Event.OnProfileTabClicked -> emitEffect(HomeContract.Effect.NavigateToProfile)
        }
    }

    private fun fetchHomeData() {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val moodsResult = repository.getMoodPlaylists()
            val recentsResult = repository.getRecentlyPlayed()

            _state.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    moodPlaylists = moodsResult.getOrNull() ?: emptyList(),
                    recentPlaylists = recentsResult.getOrNull() ?: emptyList()
                )
            }

            moodsResult.exceptionOrNull()?.let { error ->
                emitEffect(HomeContract.Effect.ShowError(error.message ?: "Müzikler yüklenemedi."))
            }
            recentsResult.exceptionOrNull()?.let { error ->
                emitEffect(HomeContract.Effect.ShowError(error.message ?: "Son çalınanlar yüklenemedi."))
            }
        }
    }

    private fun emitEffect(effect: HomeContract.Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}