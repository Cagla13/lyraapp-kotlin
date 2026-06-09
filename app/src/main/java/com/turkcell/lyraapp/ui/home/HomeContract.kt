package com.turkcell.lyraapp.ui.home

import com.turkcell.lyraapp.data.repository.Playlist
import com.turkcell.lyraapp.ui.base.UiEffect
import com.turkcell.lyraapp.ui.base.UiEvent
import com.turkcell.lyraapp.ui.base.UiState

object HomeContract {

    data class State(
        val moodPlaylists: List<Playlist> = emptyList(),
        val recentPlaylists: List<Playlist> = emptyList(),
        val isLoading: Boolean = false
    ) : UiState

    sealed interface Event : UiEvent {
        data class OnPlaylistClicked(val playlistId: String) : Event
        data object OnHomeTabClicked : Event
        data object OnSearchTabClicked : Event
        data object OnLibraryTabClicked : Event
        data object OnFavoritesTabClicked : Event
        data object OnProfileTabClicked : Event
    }

    sealed interface Effect : UiEffect {
        data class NavigateToPlaylistDetail(val playlistId: String) : Effect
        data class ShowError(val message: String) : Effect
        data object NavigateToSearch : Effect
        data object NavigateToLibrary : Effect
        data object NavigateToFavorites : Effect
        data object NavigateToProfile : Effect
    }
}