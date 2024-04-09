package com.merabk.axaliapipokemonebi.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.merabk.axaliapipokemonebi.domain.model.PokemonMainPageModel
import com.merabk.axaliapipokemonebi.util.loadImageWithGlide
import com.merabk.axaliapipokemonebi.databinding.ItemTvBinding

class MainAdapter(
    private val movieItemClickListener: MovieItemClickListener
) : ListAdapter<PokedexListEntry, MainAdapter.TvViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder = TvViewHolder(
        ItemTvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ), movieItemClickListener
    )

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) =
        holder.bind(getItem(position))

    class TvViewHolder(
        private val binding: ItemTvBinding,
        private val movieItemClickListener: MovieItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(rvModel: PokedexListEntry) = with(binding) {
            val url = rvModel.imageUrl
            ivImage.loadImageWithGlide(url)
            tvTitle.text = rvModel.pokemonName
            tvVoteAverage.text = rvModel.number.toString()
            itemView.setOnClickListener {
                movieItemClickListener.onMovieItemClicked(rvModel.pokemonName)
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<PokedexListEntry>() {

            override fun areContentsTheSame(
                oldItem: PokedexListEntry,
                newItem: PokedexListEntry
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: PokedexListEntry,
                newItem: PokedexListEntry
            ): Boolean = oldItem.pokemonName == newItem.pokemonName
        }
    }

    interface MovieItemClickListener {
        fun onMovieItemClicked(movieId: String)
    }
}