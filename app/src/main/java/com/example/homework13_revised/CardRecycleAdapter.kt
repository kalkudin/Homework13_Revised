package com.example.homework13_revised

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework13_revised.databinding.InputsContainerLayoutBinding

class CardRecycleAdapter(private val cardList: List<ListContainer>) :
    RecyclerView.Adapter<CardRecycleAdapter.CardViewHolder>() {

    inner class CardViewHolder(private val binding: InputsContainerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val fieldRecyclerView: RecyclerView = binding.cardsRecycleView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            InputsContainerLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = cardList[position].fields
        val inputFieldRecycleAdapter = FieldRecycleAdapter(currentItem)

        holder.fieldRecyclerView.layoutManager =
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)

        holder.fieldRecyclerView.adapter = inputFieldRecycleAdapter
    }
}
