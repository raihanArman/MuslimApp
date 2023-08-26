package com.raydev.navigation

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
sealed class Destination(protected val route: String, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    object SplashScreen : NoArgumentsDestination("Splash")
    object DashboardScreen : NoArgumentsDestination("dashboard")

    object BookmarkScreen : NoArgumentsDestination("bookmark")
    object ReadQuranScreen : Destination("read_quran", "surah_id") {
        const val SURAH_ID_KEY = "surah_id"
        operator fun invoke(id: Int): String = route.appendParams(
            SURAH_ID_KEY to "$id"
        )
    }

    object MangaDetailScreen : Destination("manga_detail", "manga_id") {
        const val MANGA_ID_KEY = "manga_id"
        operator fun invoke(id: String): String = route.appendParams(
            MANGA_ID_KEY to id
        )
    }

    object CharacterMangaScreen : Destination("character_manga", "manga_id") {
        const val MANGA_ID_KEY = "manga_id"
        operator fun invoke(id: String): String = route.appendParams(
            MANGA_ID_KEY to id
        )
    }

    object CharacterAnimeScreen : Destination("character_anime", "anime_id") {
        const val ANIME_ID_KEY = "anime_id"
        operator fun invoke(id: String): String = route.appendParams(
            ANIME_ID_KEY to id
        )
    }

    object CharacterDetailScreen : Destination("character_detail", "character_id") {
        const val CHARACTER_ID_KEY = "character_id"
        operator fun invoke(id: String): String = route.appendParams(
            CHARACTER_ID_KEY to id
        )
    }

    object AnimeByCategoryScreen : Destination("anime_category", *arrayOf("category_id", "category_name")) {
        const val CATEGORY_ID_KEY = "category_id"
        const val CATEGORY_NAME_KEY = "category_name"
        operator fun invoke(id: String, name: String): String = route.appendParams(
            CATEGORY_ID_KEY to id,
            CATEGORY_NAME_KEY to name
        )
    }

    object MangaByCategoryScreen : Destination("manga_category", *arrayOf("category_id", "category_name")) {
        const val CATEGORY_ID_KEY = "category_id"
        const val CATEGORY_NAME_KEY = "category_name"
        operator fun invoke(id: String, name: String): String = route.appendParams(
            CATEGORY_ID_KEY to id,
            CATEGORY_NAME_KEY to name
        )
    }

    object AnimeAllScreen : NoArgumentsDestination("anime_all")
    object MangaAllScreen : NoArgumentsDestination("manga_all")
    object SearchScreen : NoArgumentsDestination("search")
    object FavoriteScreen : NoArgumentsDestination("favorite")
    object QuoteScreen : NoArgumentsDestination("quote")
}


internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let { arg ->
            builder.append("/$arg")
        }
    }

    return builder.toString()
}