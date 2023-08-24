package com.raydev.shared.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * @author Raihan Arman
 * @date 21/08/23
 */
fun getCurrentDate(): String {
    val cal = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id"))
    return dateFormat.format(cal.time)
}