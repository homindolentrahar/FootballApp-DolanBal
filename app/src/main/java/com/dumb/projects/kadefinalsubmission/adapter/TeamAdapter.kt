package com.dumb.projects.kadefinalsubmission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dumb.projects.kadefinalsubmission.databinding.ListItemTeamsBinding
import com.dumb.projects.kadefinalsubmission.model.Team

class TeamAdapter(private val listener: OnClickTeam) :
    ListAdapter<Team, TeamAdapter.TeamHolder>(DiffcallbackTeam) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamHolder {
        return TeamHolder(
            ListItemTeamsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TeamHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            listener.onClick(item)
        }
        holder.bind(item)
    }

    class TeamHolder(private val binding: ListItemTeamsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Team) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    class OnClickTeam(private val listener: (item: Team) -> Unit) {
        fun onClick(item: Team) = listener(item)
    }

    companion object DiffcallbackTeam : DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem.idTeam == newItem.idTeam
        }
    }
}