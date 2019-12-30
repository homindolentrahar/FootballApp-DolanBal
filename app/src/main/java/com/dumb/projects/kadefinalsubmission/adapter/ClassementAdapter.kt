package com.dumb.projects.kadefinalsubmission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dumb.projects.kadefinalsubmission.databinding.ListItemClassementBinding
import com.dumb.projects.kadefinalsubmission.model.Classement

class ClassementAdapter(private val listener: OnClickClassement) :
    ListAdapter<Classement, ClassementAdapter.ClassementHolder>(DiffcallbackClassement) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassementHolder {
        return ClassementHolder(
            ListItemClassementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ClassementHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            listener.onClick(item)
        }
        holder.bind(item)
    }

    class ClassementHolder(private val binding: ListItemClassementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Classement) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    class OnClickClassement(private val listener: (classement: Classement) -> Unit) {
        fun onClick(classement: Classement) = listener(classement)
    }

    companion object DiffcallbackClassement : DiffUtil.ItemCallback<Classement>() {
        override fun areItemsTheSame(oldItem: Classement, newItem: Classement): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Classement, newItem: Classement): Boolean {
            return oldItem.teamName == newItem.teamName
        }
    }
}