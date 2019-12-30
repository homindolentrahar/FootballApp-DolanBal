package com.dumb.projects.kadefinalsubmission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dumb.projects.kadefinalsubmission.databinding.ListItemLeaguesBinding
import com.dumb.projects.kadefinalsubmission.model.LeagueLocal

class LeagueLocalAdapter(private val listener: OnClickLeagueLocal) :
    ListAdapter<LeagueLocal, LeagueLocalAdapter.LeagueLocalHolder>(DiffcallbackLeagueLocal) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeagueLocalHolder {
        return LeagueLocalHolder(
            ListItemLeaguesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LeagueLocalHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            listener.onClick(item)
        }
        holder.bind(item)
    }

    class LeagueLocalHolder(private val binding: ListItemLeaguesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LeagueLocal) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    class OnClickLeagueLocal(private val listener: (item: LeagueLocal) -> Unit) {
        fun onClick(item: LeagueLocal) = listener(item)
    }

    companion object DiffcallbackLeagueLocal : DiffUtil.ItemCallback<LeagueLocal>() {
        override fun areItemsTheSame(oldItem: LeagueLocal, newItem: LeagueLocal): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: LeagueLocal, newItem: LeagueLocal): Boolean {
            return oldItem.idLeague == newItem.idLeague
        }
    }

}