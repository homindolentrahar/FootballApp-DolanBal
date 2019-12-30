package com.dumb.projects.kadefinalsubmission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dumb.projects.kadefinalsubmission.databinding.ListItemMatchesBinding
import com.dumb.projects.kadefinalsubmission.model.Match

class MatchAdapter(private val listener: OnClickMatch) :
    ListAdapter<Match, MatchAdapter.MatchHolder>(DiffcallbackMatch) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchHolder {
        return MatchHolder(
            ListItemMatchesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MatchHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            listener.onClick(item)
        }
        holder.bind(item)
    }

    class MatchHolder(private val binding: ListItemMatchesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Match) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    class OnClickMatch(private val listener: (item: Match) -> Unit) {
        fun onClick(item: Match) = listener(item)
    }

    companion object DiffcallbackMatch : DiffUtil.ItemCallback<Match>() {
        override fun areItemsTheSame(oldItem: Match, newItem: Match): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Match, newItem: Match): Boolean {
            return oldItem.idEvent == newItem.idEvent
        }
    }
}