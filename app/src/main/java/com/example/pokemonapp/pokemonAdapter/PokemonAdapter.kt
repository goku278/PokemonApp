package com.example.pokemonapp.pokemonAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.pokemonModel.Pokemon

class PokemonAdapter(
    private val context: Context,
    private val dataList: List<Pokemon>,
    val listener: (Pokemon) -> Unit
) :
    RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.pokemon_item_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = dataList[position]
        holder.bind(dataItem)
        holder.itemView.setOnClickListener {
            listener(dataItem)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.tvPokemonText)
        private val imageView: ImageView = itemView.findViewById(R.id.ivPokemonImage)
        private val card: CardView = itemView.findViewById(R.id.cvPokemon)

        fun bind(dataItem: Pokemon) {
            textView.text = dataItem.description
            Glide.with(context).load(dataItem.image).fitCenter().placeholder(R.drawable.pokeball)
                .into(imageView)
        }
    }
}