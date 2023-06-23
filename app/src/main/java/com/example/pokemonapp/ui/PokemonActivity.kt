package com.example.pokemonapp.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemonapp.R
import com.example.pokemonapp.detailedPokenon.DetailsActivity
import com.example.pokemonapp.pokemonAdapter.PokemonAdapter
import com.example.pokemonapp.pokemonModel.Pokemon
import com.example.pokemonapp.utils.CachingUtils
import kotlinx.android.synthetic.main.activity_pokemon.*

class PokemonActivity : AppCompatActivity() {
    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var list: ArrayList<Pokemon>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)

        llEmptyBox?.visibility = View.VISIBLE

        createNotificationChannel()

        setUI()

        setClicks()
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

    private fun setClicks() {
        ivBack?.setOnClickListener {
            onBackPressed()
        }

        btnPush?.setOnClickListener {
            var size = CachingUtils.getSize()
            var capacity = CachingUtils.getCapacity()
            if (size == capacity!! - 1) {
                Toast.makeText(
                    applicationContext,
                    "Stack capacity full, cannot add more Pokemon",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (size == 0) {
                    CachingUtils.setPokemonStack(list[0])
                }
                if (size != null) {
                    if (size > 0 && size <= capacity!! - 2) {
                        CachingUtils.setPokemonStack(list[size + 1])
                    }
                }
            }
            llEmptyBox?.visibility = View.GONE
            setAdapter(ArrayList())
            btnPush?.text = "PUSH [${CachingUtils.getSize()}]"
        }

        btnPop?.setOnClickListener {
            var isEmpty = CachingUtils.getIsStackEmpty()
            if (isEmpty == true) {
                svScroll?.isEnabled = false
                llEmptyBox?.visibility = View.VISIBLE
//                rvPokemon?.visibility = View.GONE
                Toast.makeText(
                    applicationContext,
                    "Stack is Empty, cannot pop more Pokemon",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (!isEmpty!!) {
                var pokemonStack = CachingUtils.getPokemonStack()
                pokemonStack?.pop()
                Log.d("PA", "pokemonStack -> $pokemonStack")
                setAdapter(ArrayList())
                pokemonAdapter.notifyDataSetChanged()
                if (pokemonStack!!.isEmpty()) {
                    svScroll?.isEnabled = false
                    llEmptyBox?.visibility = View.VISIBLE
//                rvPokemon?.visibility = View.GONE
                    Toast.makeText(
                        applicationContext,
                        "Stack is Empty, cannot pop more Pokemon",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                if (CachingUtils.getSize()!! > 0) {
                    btnPush?.text = "PUSH [${CachingUtils.getSize()}]"
                } else if (CachingUtils.getSize()!! == 0) {
                    btnPush?.text = "PUSH"
                }
            }
        }

        btnPeek?.setOnClickListener {
            if (!CachingUtils.getIsStackEmpty()!!) {
                var peek = CachingUtils.getPokemonStack()?.peek()
                Log.d("PA", "peek $peek")
                var list = ArrayList<Pokemon>()
                if (peek != null) {
                    list.add(peek)
                }
                setAdapter(list)
                pokemonAdapter.notifyDataSetChanged()

            } else {
                svScroll?.isEnabled = false
                llEmptyBox?.visibility = View.VISIBLE
//                rvPokemon?.visibility = View.GONE
                Toast.makeText(
                    applicationContext,
                    "Stack is Empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setAdapter(list: ArrayList<Pokemon>) {
        var pokemonStack = CachingUtils.getPokemonStack()
        if (list.isEmpty()) {
            list.addAll(pokemonStack!!)
            list.reverse()
        }
        pokemonAdapter = PokemonAdapter(applicationContext, list) {
//            Toast.makeText(applicationContext, "clicked on -> ${it.description}", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@PokemonActivity, DetailsActivity::class.java)
            CachingUtils.setPokemon(it)
            startActivity(intent)
        }
        rvPokemon.layoutManager = GridLayoutManager(applicationContext, 1)
        rvPokemon.adapter = pokemonAdapter
    }


    private fun setUI() {
        list = ArrayList<Pokemon>()
        addPokemon(list as ArrayList<Pokemon>)
    }

    private fun addPokemon(list: ArrayList<Pokemon>) {
        var p = Pokemon()
        p.image = R.drawable.pikachu
        p.description = "Pikachu"
        list.add(p)

        p = Pokemon()
        p.image = R.drawable.squirtle
        p.description = "Squirtle"
        list.add(p)

        p = Pokemon()
        p.image = R.drawable.charizard
        p.description = "Charizard"
        list.add(p)

        p = Pokemon()
        p.image = R.drawable.charmender
        p.description = "Charmender"
        list.add(p)

        p = Pokemon()
        p.image = R.drawable.empoleon
        p.description = "Empoleon"
        list.add(p)

        p = Pokemon()
        p.image = R.drawable.garchomp
        p.description = "Garchomp"
        list.add(p)

        p = Pokemon()
        p.image = R.drawable.lucario
        p.description = "Lucario"
        list.add(p)

        p = Pokemon()
        p.image = R.drawable.mew
        p.description = "Mew"
        list.add(p)

        p = Pokemon()
        p.image = R.drawable.palkia
        p.description = "palkia"
        list.add(p)

        p = Pokemon()
        p.image = R.drawable.snorlax
        p.description = "Snorlax"
        list.add(p)
    }
}