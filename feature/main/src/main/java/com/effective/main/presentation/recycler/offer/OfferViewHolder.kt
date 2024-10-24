package com.effective.main.presentation.recycler.offer

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.effective.core.domain.model.Offer
import com.effective.main.R


class OfferViewHolder(view: View, onItemClick: (Int) -> Unit): RecyclerView.ViewHolder(view) {
    private val offerIV = view.findViewById<ImageView>(R.id.offerImage)
    private val offerTitleTV = view.findViewById<TextView>(R.id.offerTitle)
    private val offerButton = view.findViewById<TextView>(R.id.offerButton)

    init {
        itemView.setOnClickListener {
            onItemClick(adapterPosition)
        }
    }

    fun onBind(offer: Offer) {
        if (offer.buttonDto != null) {
            offerButton.text = offer.buttonDto!!.text
            offerButton.visibility = View.VISIBLE
            offerTitleTV.maxLines = 2
        } else {
            offerTitleTV.maxLines = 3
        }
        offerTitleTV.text = offer.title

        if (offer.id == null) {
            offerIV.visibility = View.GONE
        } else {
            offerIV.visibility = View.VISIBLE
            when (offer.id) {
                "temporary_job" -> {
                    offerIV.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.dark_green))
                    offerIV.setImageResource(R.drawable.list)
                }
                "level_up_resume" -> {
                    offerIV.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.dark_green))
                    offerIV.setImageResource(R.drawable.star)
                }
                else -> {
                    offerIV.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.dark_blue))
                    offerIV.setImageResource(R.drawable.location)
                }
            }
        }
    }
}
