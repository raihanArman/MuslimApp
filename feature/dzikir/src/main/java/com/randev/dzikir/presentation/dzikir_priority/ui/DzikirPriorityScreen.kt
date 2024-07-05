package com.randev.dzikir.presentation.dzikir_priority.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import com.raihan.ui.app_bar.AppBarCustom
import com.raihan.ui.helper.OnceLaunchedEffect
import com.raihan.ui.tile.TileDzikirPriority
import com.randev.dzikir.R
import com.randev.dzikir.presentation.dzikir_priority.viewmodel.DzikirPriorityViewModel
import com.raydev.navigation.AppNavigator
import com.raydev.navigation.Destination
import com.raydev.navigation.composable
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

/**
 * @author Raihan Arman
 * @date 30/06/24
 */
fun NavGraphBuilder.dzikirNavigation() = kotlin.run {
    composable(Destination.DzikirPriorityScreen) {
        val navigator: AppNavigator = get<AppNavigator>()
        DzikirPriorityScreen(navigator = navigator)
    }
}

@Composable
fun DzikirPriorityScreen(navigator: AppNavigator) {
    val viewModel: DzikirPriorityViewModel = getViewModel()
    val state by viewModel.uiState.collectAsState()

    OnceLaunchedEffect(
        viewModel = viewModel,
        effect = { viewModel.load() }
    ) {
        Scaffold(
            topBar = {
                AppBarCustom(
                    title = "Dzikir Pagi & Petang",
                    onBack = { navigator.tryNavigateBack() }
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding())
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                DzikirCard(
                                    modifier = Modifier.weight(1f),
                                    title = "Pagi",
                                    iconRes = R.drawable.ic_pagi
                                )
                                DzikirCard(
                                    modifier = Modifier.weight(1f),
                                    title = "Petang",
                                    iconRes = R.drawable.ic_petang,
                                )
                            }
                        }
                        item {
                            Text(
                                text = "Keutamaan",
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        state.data?.let { data ->
                            items(
                                items = data,
                                key = { item ->
                                    item.id
                                }
                            ) { dzikir ->
                                TileDzikirPriority(
                                    content = dzikir.content,
                                    translate = dzikir.translate
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun RowScope.DzikirCard(modifier: Modifier = Modifier, title: String, iconRes: Int) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = Color.Black,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
            )
        }
    }
}
