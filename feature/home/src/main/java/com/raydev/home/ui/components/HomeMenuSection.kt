package com.raydev.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.raihan.ui.card.CardMenu
import com.raydev.home.ui.HomeEvent
import com.raydev.shared.util.SharedDrawable

/**
 * @author Raihan Arman
 * @date 25/09/23
 */
@Composable
fun HomeMenuSection(
    modifier: Modifier = Modifier,
    onClick: (HomeEvent) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Top
    ) {
        CardMenu(title = "Doa dan Dzikir", image = SharedDrawable.baseline_menu_book_24) {
            onClick(HomeEvent.OnClickDoaAndDzikir)
        }
        CardMenu(title = "Jadwal Puasa", image = SharedDrawable.baseline_menu_book_24) {
            onClick(HomeEvent.OnClickJadwalPuasa)
        }
        CardMenu(title = "Ceramah Pendek", image = SharedDrawable.baseline_menu_book_24) {
            onClick(HomeEvent.OnClickCeramah)
        }
        CardMenu(title = "Buku", image = SharedDrawable.baseline_menu_book_24) {
            onClick(HomeEvent.OnClickBuku)
        }
    }
}
