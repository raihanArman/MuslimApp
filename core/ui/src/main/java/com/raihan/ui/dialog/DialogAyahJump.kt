package com.raihan.ui.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.raihan.ui.text_field.TextFieldCustom
import com.raydev.shared.model.Surah

/**
 * @author Raihan Arman
 * @date 29/08/23
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DialogAyahJump(
    modifier: Modifier = Modifier,
    listSurah: List<Surah>,
    onDismissDialog: () -> Unit,
    onPositiveClick: (Surah, Int) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)

    var focusedItemIndex by remember {
        mutableStateOf(-1)
    }

    var textMessage by remember {
        mutableStateOf("")
    }

    var textFieldValue by remember {
        mutableStateOf("")
    }

    var maxLength by remember {
        mutableStateOf(-1)
    }

    var textFieldPlaceHolderValue by remember {
        mutableStateOf("")
    }

    val doneScrolling by remember {
        derivedStateOf {
            !lazyListState.isScrollInProgress
        }
    }

    LaunchedEffect(key1 = doneScrolling) {
        if (doneScrolling) {
            val surah = listSurah[focusedItemIndex]
            maxLength = surah.ayahCount
            textFieldPlaceHolderValue = "1 - ${surah.ayahCount}"
            textMessage = "Masukkan nomor ayat antara 1 - ${surah.ayahCount}"
        }
    }

    Dialog(onDismissRequest = onDismissDialog) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Pergi ke Ayat",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                BoxListSurah(
                    state = lazyListState,
                    snapBehavior = snapBehavior,
                    listSurah = listSurah
                ) { indexPosition ->
                    focusedItemIndex = indexPosition
                }
                Text(text = textMessage)
                TextFieldCustom(
                    modifier = Modifier.width(150.dp),
                    value = textFieldValue,
                    onValueChange = { value ->
                        setValueAyahInput(value, maxLength) {
                            textFieldValue = value
                        }
                    },
                    textPlaceHolder = textFieldPlaceHolderValue,
                    keyboardType = KeyboardType.Number
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    TextButton(onClick = onDismissDialog) {
                        Text(text = "Batal")
                    }
                    TextButton(onClick = {
                        val currentIndex = focusedItemIndex - 1
                        if (currentIndex > -1 && textFieldValue.isNotEmpty()) {
                            onPositiveClick(
                                listSurah[currentIndex],
                                textFieldValue.toInt()
                            )
                        }
                    }) {
                        Text(text = "Pergi")
                    }
                }
            }
        }
    }
}

inline fun validationMaxLength(value: Int, maxValue: Int, onChanged: () -> Unit) {
    if (value <= maxValue) {
        onChanged()
    }
}

@Composable
fun BoxListSurah(
    state: LazyListState,
    snapBehavior: FlingBehavior,
    listSurah: List<Surah>,
    onFocusPosition: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .width(250.dp)
            .height(120.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            color = Color.Gray.copy(alpha = 0.3f),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
        }
        LazyColumn(
            state = state,
            flingBehavior = snapBehavior,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Spacer(modifier = Modifier.height(35.dp))
            }
            itemsIndexed(listSurah) { index, item ->
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .padding(horizontal = 10.dp)
                        .onGloballyPositioned {
                            onFocusPosition(index)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "${index + 1}.  ${item.name}")
                }
            }
            item {
                Spacer(modifier = Modifier.height(35.dp))
            }
        }
    }
}

inline fun setValueAyahInput(value: String, maxLength: Int, setValue: () -> Unit) {
    if (value.isNotEmpty()) {
        if (value.all { it.isDigit() }) {
            validationMaxLength(value.toInt(), maxLength) {
                setValue()
            }
        }
    } else {
        setValue()
    }
}
