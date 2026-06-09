package com.turkcell.lyraapp.ui.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeContract.Effect.NavigateToPlaylistDetail -> onNavigateToDetail(effect.playlistId)
                is HomeContract.Effect.ShowError -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                else -> { /* İleride bottom nav yönlendirmeleri eklenecek */ }
            }
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Ana sayfa") },
                    label = { Text("Ana sayfa") },
                    selected = true,
                    onClick = { viewModel.onEvent(HomeContract.Event.OnHomeTabClicked) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Search, contentDescription = "Ara") },
                    label = { Text("Ara") },
                    selected = false,
                    onClick = { viewModel.onEvent(HomeContract.Event.OnSearchTabClicked) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.LibraryMusic, contentDescription = "Kütüphane") },
                    label = { Text("Kütüphane") },
                    selected = false,
                    onClick = { viewModel.onEvent(HomeContract.Event.OnLibraryTabClicked) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoriler") },
                    label = { Text("Favoriler") },
                    selected = false,
                    onClick = { viewModel.onEvent(HomeContract.Event.OnFavoritesTabClicked) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profil") },
                    label = { Text("Profil") },
                    selected = false,
                    onClick = { viewModel.onEvent(HomeContract.Event.OnProfileTabClicked) }
                )
            }
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "İyi akşamlar", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Ne dinlemek istersin?", style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Ne Dinlemek İstersin (İkili Izgara Görünümü)
                val chunkedMoods = state.moodPlaylists.chunked(2)
                items(chunkedMoods) { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (playlist in rowItems) {
                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 4.dp)
                                    .clickable { viewModel.onEvent(HomeContract.Event.OnPlaylistClicked(playlist.id)) },
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Text(
                                    text = playlist.title,
                                    modifier = Modifier.padding(16.dp),
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Son çalınanlar", style = MaterialTheme.typography.titleLarge)
                        TextButton(onClick = { /* Tümü action */ }) {
                            Text("Tümü", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Son Çalınanlar (Yatay Kaydırılabilir Liste)
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.recentPlaylists) { playlist ->
                            Card(
                                modifier = Modifier
                                    .width(140.dp)
                                    .height(140.dp)
                                    .clickable { viewModel.onEvent(HomeContract.Event.OnPlaylistClicked(playlist.id)) },
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(12.dp),
                                    verticalArrangement = Arrangement.Bottom
                                ) {
                                    Text(text = playlist.title, style = MaterialTheme.typography.titleMedium)
                                    if (playlist.subtitle.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = playlist.subtitle, style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}