package com.example.pokemonapp.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Display the notification
        NotificationUtils.showNotification(
            context, "Pokemon Movie Remainder", "Hi there " +
                    "your pokemon movie starring ${CachingUtils.getPokemon().description} is going to start in some minutes"
        )
    }
}