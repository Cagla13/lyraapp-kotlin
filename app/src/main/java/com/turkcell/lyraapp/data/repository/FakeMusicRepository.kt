package com.turkcell.lyraapp.data.repository

import kotlinx.coroutines.delay


data class Playlist(
    val id: String,
    val title: String,
    val subtitle: String = ""
)

class FakeMusicRepository {


    suspend fun getMoodPlaylists(): Result<List<Playlist>> {
        delay(1000)
        val list = listOf(
            Playlist("1", "Gece Sürüşü"),
            Playlist("2", "Sabah Kahvesi"),
            Playlist("3", "Neon Sokaklar"),
            Playlist("4", "Odaklan"),
            Playlist("5", "Derin Mavi"),
            Playlist("6", "Yaz Anıları")
        )
        return Result.success(list)
    }

    suspend fun getRecentlyPlayed(): Result<List<Playlist>> {
        delay(1200)
        val list = listOf(
            Playlist("101", "Neon Sokaklar", "Şehir Işıkları"),
            Playlist("102", "Derin Mavi", "Okyanus"),
            Playlist("103", "Yıldız Tozu", "Polaris")
        )
        return Result.success(list)
    }
}