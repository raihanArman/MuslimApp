package com.raydev.navigation

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
sealed class Destination(protected val route: String, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{$it}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    object SplashScreen : NoArgumentsDestination("Splash")
    object DashboardScreen : NoArgumentsDestination("dashboard")

    object BookmarkScreen : NoArgumentsDestination("bookmark")
    object DailyDuasScreen : NoArgumentsDestination("dailyduas")
    object ShortDakwahScreen : NoArgumentsDestination("short_dakwah")
    object DzikirPriorityScreen : NoArgumentsDestination("dzikir_priority")
    object ReadQuranScreen : Destination("read_quran", "surah_id", "verse_number") {
        const val SURAH_ID_KEY = "surah_id"
        const val VERSE_NUMBER = "verse_number"
        operator fun invoke(id: Int, verseNumber: Int): String = route.appendParams(
            SURAH_ID_KEY to "$id",
            VERSE_NUMBER to "$verseNumber"
        )
    }
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
