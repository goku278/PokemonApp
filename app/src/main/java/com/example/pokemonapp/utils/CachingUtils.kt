package com.example.pokemonapp.utils

import com.example.pokemonapp.pokemonModel.Pokemon
import java.util.*

object CachingUtils {
    private var pokemonStack: Stack<Pokemon> = Stack<Pokemon>()
    var size: Int = 0
    private lateinit var pokemon: Pokemon
    /* private var isEmpty by Delegates.notNull<Boolean>()
     private var capacity by Delegates.notNull<Int>()*/

    fun setPokemonStack(pokemon: Pokemon) {
        pokemonStack.push(pokemon)
    }

    fun getPokemonStack(): Stack<Pokemon>? {
        return pokemonStack
    }

    fun getCapacity(): Int? {
        return 10
    }

    fun getIsStackEmpty(): Boolean? {
        return pokemonStack.isEmpty()
    }

    fun getSize(): Int? {
        return pokemonStack.size
    }

    fun setPokemon(it: Pokemon) {
        pokemon = it
    }

    fun getPokemon(): Pokemon {
        return pokemon
    }

}