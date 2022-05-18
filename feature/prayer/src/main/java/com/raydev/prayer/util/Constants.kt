package com.raydev.prayer


object ReminderParams{
    const val KEY_HOURS = "hours"
    const val KEY_MINUTE = "minutes"
    const val KEY_REQUEST_CODE = "reqCode"
    const val KEY_ENABLE = "enable"
}

object NotificationAlarmConstants{
    @JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
        "Verbose WorkManager Notifications"
    const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
        "Shows notifications whenever work starts"
    @JvmField val NOTIFICATION_TITLE: CharSequence = "Reminder"
    const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
    const val NOTIFICATION_ID = 1
}