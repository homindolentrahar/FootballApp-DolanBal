package com.dumb.projects.kadefinalsubmission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dumb.projects.kadefinalsubmission.databinding.ListFavoriteItemMatchesBinding
import com.dumb.projects.kadefinalsubmission.model.MatchFavorites

class FavoriteMatchAdapter(private val listener: OnClickMatchFavorites) :
    ListAdapter<MatchFavorites, FavoriteMatchAdapter.FavoriteMatchHolder>(DiffcallbackMatchFavorites) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMatchHolder {
        return FavoriteMatchHolder(
            ListFavoriteItemMatchesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteMatchHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            listener.onClick(item)
        }
        holder.bind(item)
    }

    class FavoriteMatchHolder(private val binding: ListFavoriteItemMatchesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MatchFavorites) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    class OnClickMatchFavorites(private val listener: (item: MatchFavorites) -> Unit) {
        fun onClick(item: MatchFavorites) = listener(item)
    }

    companion object DiffcallbackMatchFavorites : DiffUtil.ItemCallback<MatchFavorites>() {
        override fun areItemsTheSame(oldItem: MatchFavorites, newItem: MatchFavorites): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MatchFavorites, newItem: MatchFavorites): Boolean {
            return oldItem.idEvent == newItem.idEvent
        }
    }
}