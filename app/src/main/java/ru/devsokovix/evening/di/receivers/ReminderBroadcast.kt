package ru.devsokovix.evening.di.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.devsokovix.evening.data.entity.Film
import ru.devsokovix.evening.view.notifications.NotificationConstants
import ru.devsokovix.evening.view.notifications.NotificationHelper

class ReminderBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.getBundleExtra(NotificationConstants.FILM_BUNDLE_KEY)
        val film: Film = bundle?.get(NotificationConstants.FILM_KEY) as Film
        NotificationHelper.createNotification(context!!, film)
    }
}