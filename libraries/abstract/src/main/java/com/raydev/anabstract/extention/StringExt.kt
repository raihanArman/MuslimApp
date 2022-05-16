package com.raydev.anabstract.extention

import android.os.Build
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun String.toDate() : String {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
        val date = LocalDate.parse(this, formatter)

        val newFormatter = DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH)
        return date.format(newFormatter)
    } else {
        val parser = SimpleDateFormat("yyyy-MM-dd") // yyyy-MM-dd'T'HH:mm:ss
        val formatter = SimpleDateFormat("dd MMM")
        return  formatter.format(parser.parse(this))
    }

}

fun String.toNumberPhoneFormat(): String{
    val phoneFull = this.substring(0)
    return "+62$phoneFull"
}

fun Date.toDateFormatDaysFullname(): String{
    return DateFormat.format("EEEE, dd MMMM yyyy", this).toString()
}

fun Date.toDateFormatApiParameter(): String{
    return DateFormat.format("yyyy/MM/dd", this).toString()
}