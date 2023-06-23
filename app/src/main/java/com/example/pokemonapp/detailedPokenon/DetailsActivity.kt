package com.example.pokemonapp.detailedPokenon

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.pokemonModel.Pokemon
import com.example.pokemonapp.utils.CachingUtils
import com.example.pokemonapp.utils.NotificationUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.details_activity.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private lateinit var pokemon: Pokemon

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    val ALARM_REQ_CODE = 100

    //    private lateinit var timePicker: TimePicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)

        createNotificationChannel()

        setUI()

        setClicks()
    }

    private fun setClicks() {
        add.setOnClickListener {
            val bottomSheet = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
            bottomSheet.setContentView(view)

            // Set behavior to expand to full height
            val parentLayout = view.parent as View
            bottomSheetBehavior = BottomSheetBehavior.from(parentLayout)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

            val btnPurchase = view.findViewById<Button>(R.id.btnPurchase)
            val etDonateMoney = view.findViewById<EditText>(R.id.etDonateMoney)
            val cbBuyPokemonMovie = view.findViewById<CheckBox>(R.id.cbBuyPokemonMovie)
            val llPurchaseMovieTicket = view.findViewById<LinearLayout>(R.id.llPurchaseMovieTicket)
            val llAfterPurchase = view.findViewById<LinearLayout>(R.id.llAfterPurchase)
            val ivPokemon2 = view.findViewById<ImageView>(R.id.ivPokemon2)

            val calendar = view.findViewById<FloatingActionButton>(R.id.calendar)

            calendar?.setOnClickListener {
                val bottomSheet2 = BottomSheetDialog(this)
                val view2 = layoutInflater.inflate(R.layout.bottom_sheet_alarm, null)
                bottomSheet.setContentView(view2)

                // Set behavior to expand to full height
                val parentLayout = view2.parent as View
                bottomSheetBehavior = BottomSheetBehavior.from(parentLayout)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

                val timePicker1 = view2.findViewById<TimePicker>(R.id.timePicker)
                val setAlarmButton = view2.findViewById<Button>(R.id.setAlarmButton)

                println("timepicker -> $timePicker1")

                setAlarmButton?.setOnClickListener {
                    NotificationUtils.scheduleNotification(
                        this,
                        timePicker1.hour,
                        timePicker1.minute
                    )
                    Toast.makeText(
                        applicationContext,
                        "Remainder set successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
                bottomSheet2.show()
//                bottomSheet2.dismiss()
            }



            Glide.with(applicationContext).load(pokemon.image)
                .placeholder(R.drawable.pokeball)
                .into(ivPokemon2)

            btnPurchase?.setOnClickListener {
                if (cbBuyPokemonMovie?.isChecked == false) {
                    Toast.makeText(
                        applicationContext,
                        "Please buy this movie to proceed !",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                if (etDonateMoney?.text.toString().isNullOrEmpty()) {
                    etDonateMoney?.error = "Please donate some money to proceed"
                    return@setOnClickListener
                }

                llPurchaseMovieTicket?.visibility = View.GONE
                llAfterPurchase?.visibility = View.VISIBLE
            }

            bottomSheet.show()
        }

        add?.backgroundTintList = resources.getColorStateList(R.color.red, theme)
        calendar?.backgroundTintList = resources.getColorStateList(R.color.red, theme)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "pokemonMovieRemainderChannel"
            val description = "Channel For Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("pokemon", name, importance)
            channel.description = description

            val notificationManager = getSystemService(
                NotificationManager::class.java
            )

            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setUI() {
        pokemon = CachingUtils.getPokemon()
        Glide.with(applicationContext)
            .load(pokemon.image)
            .into(ivPokemon)

        tvDetails?.text = pokemon.description + "\n\n" +
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.\n" +
                "\n" +
                "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                "\n" +
                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)."
    }
}