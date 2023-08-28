package com.raydev.prayer

object ReminderParams {
    const val KEY_HOURS = "hours"
    const val KEY_MINUTE = "minutes"
    const val KEY_REQUEST_CODE = "reqCode"
    const val KEY_ENABLE = "enable"
    const val KEY_MESSAGE = "message"
}

object NotificationAlarmConstants {
    const val VERBOSE_NOTIFICATION_CHANNEL_NAME =
        "Verbose WorkManager Notifications"
    const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
        "Shows notifications whenever work starts"
    const val NOTIFICATION_TITLE = "Reminder"
    const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
    const val NOTIFICATION_ID = 1
}
