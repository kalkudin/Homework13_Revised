package com.example.homework13_revised

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework13_revised.databinding.InputsContainerLayoutBinding

class CardRecycleAdapter :
    ListAdapter<ListContainer, CardRecycleAdapter.CardViewHolder>(CardDiffCallback()) {

    class CardDiffCallback : DiffUtil.ItemCallback<ListContainer>() {
        override fun areItemsTheSame(oldItem: ListContainer, newItem: ListContainer): Boolean {
            return oldItem.fields == newItem.fields
        }

        override fun areContentsTheSame(oldItem: ListContainer, newItem: ListContainer): Boolean {
            return oldItem == newItem
        }
    }
    inner class CardViewHolder(private val binding: InputsContainerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val fieldRecyclerView: RecyclerView = binding.cardsRecycleView
        fun bind(item: ListContainer) {
            val inputFieldRecycleAdapter = FieldRecycleAdapter(item.fields)

            fieldRecyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            fieldRecyclerView.adapter = inputFieldRecycleAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            InputsContainerLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
}