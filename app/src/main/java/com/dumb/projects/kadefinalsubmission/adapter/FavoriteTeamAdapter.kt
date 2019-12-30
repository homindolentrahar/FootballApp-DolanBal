package com.dumb.projects.kadefinalsubmission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dumb.projects.kadefinalsubmission.databinding.ListFavoriteItemTeamsBinding
import com.dumb.projects.kadefinalsubmission.model.TeamFavorites

class FavoriteTeamAdapter(private val listener: OnClickTeamFavorites) :
    ListAdapter<TeamFavorites, FavoriteTeamAdapter.FavoriteTeamHolder>(DiffcallbackTeamFavorites) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTeamHolder {
        return FavoriteTeamHolder(
            ListFavoriteItemTeamsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteTeamHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            listener.onClick(item)
        }
        holder.bind(item)
    }

    class FavoriteTeamHolder(private val binding: ListFavoriteItemTeamsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TeamFavorites) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    class OnClickTeamFavorites(private val listener: (item: TeamFavorites) -> Unit) {
        fun onClick(item: TeamFavorites) = listener(item)
    }

    companion object DiffcallbackTeamFavorites : DiffUtil.ItemCallback<TeamFavorites>() {
        override fun areItemsTheSame(oldItem: TeamFavorites, newItem: TeamFavorites): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TeamFavorites, newItem: TeamFavorites): Boolean {
            return oldItem.idTeam == newItem.idTeam
        }
    }
}