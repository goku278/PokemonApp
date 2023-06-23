package com.example.pokemonapp.utils

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.pokemonapp.R
import com.example.pokemonapp.detailedPokenon.DetailsActivity

class AlarmReceiver : BroadcastReceiver() {

    private lateinit var mp: MediaPlayer

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_SHORT).show()
        val i = Intent(context, DetailsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, i, 0)

        val builder = NotificationCompat.Builder(context, "pokemon")
            .setSmallIcon(R.drawable.pokeball)
            .setContentTitle("Pokemon Movie Alarm")
            .setContentText("Your pokemon movie will start in few minutes")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123, builder.build())
    }

}