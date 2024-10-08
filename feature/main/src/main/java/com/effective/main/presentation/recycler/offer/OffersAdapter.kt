package com.effective.main.presentation.recycler.offer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.effective.core.domain.model.Offer
import com.effective.main.R

class OffersAdapter : RecyclerView.Adapter<OfferViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<Offer>() {
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean =
            oldItem.title == newItem.title
    }
    private val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_offer, parent, false)
        return OfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.onBind(item)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(offers: List<Offer>) {
        differ.submitList(offers)
    }
}