package com.example.homeworkapp.recycler.data.repository

import com.example.homeworkapp.recycler.data.models.Playlist

class PlaylistRepository {
    fun getPinnedPlaylists() = listOf(
        Playlist(
            "Playlist №1",
            20,
            1,
            listOf(
                "https://avatars.yandex.net/get-music-content/5314916/ff5a2072.a.23135125-1/200x200",
                "https://avatars.yandex.net/get-music-content/4388221/9e83f8f0.a.18279676-1/200x200",
                "https://avatars.yandex.net/get-music-content/4384958/13cfa9c1.a.15985737-1/200x200"
            )
        ),
        Playlist(
            "Playlist №2",
            13,
            2,
            listOf(
                "https://avatars.yandex.net/get-music-content/2433821/4f80defa.a.10235868-1/200x200",
                "https://avatars.yandex.net/get-music-content/2808981/b2b73862.a.10235866-1/200x200",
                "https://avatars.yandex.net/get-music-content/2359742/134624a4.a.10235845-1/200x200"
            )
        ),
        Playlist(
            "Playlist №3",
            4,
            3,
            listOf(
                "https://avatars.yandex.net/get-music-content/98892/8e09891d.a.4872198-1/200x200",
                "https://avatars.yandex.net/get-music-content/3071110/49d46a02.a.11287633-1/200x200",
                "https://avatars.yandex.net/get-music-content/6447985/e9f45937.a.22750671-1/200x200"
            )
        ),
        Playlist(
            "Playlist №4",
            9,
            2,
            listOf(
                "https://avatars.yandex.net/get-music-content/2358262/571a8c7f.a.10233336-1/200x200",
                "https://avatars.yandex.net/get-music-content/103235/bc8ead69.a.5905756-1/200x200",
                "https://avatars.yandex.net/get-music-content/114728/3a4b1b96.a.5319432-1/200x200"
            )
        ),
        Playlist(
            "Fata Morgana №5",
            1,
            5,
            listOf(
                "https://avatars.yandex.net/get-music-content/2373979/46f05b89.a.9086864-1/200x200",
                "https://avatars.yandex.net/get-music-content/4467280/e54a9c7f.a.12950836-1/200x200",
                "https://avatars.yandex.net/get-music-content/192707/2dbef015.a.4998131-1/200x200"
            )
        )
    )
}