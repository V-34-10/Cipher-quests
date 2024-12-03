package com.ciphero.questa.adapters.findpairs

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ciphero.questa.R
import com.ciphero.questa.model.FindPair

class FindPairViewHolder(itemView: View, private val onClick: (Int) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    private val pairImage: ImageView = itemView.findViewById(R.id.find_pair)
    fun bind(findPair: FindPair, position: Int) {
        pairImage.setImageResource(if (findPair.flip) findPair.img else R.drawable.find_0)
        itemView.setOnClickListener { onClick(position) }
    }
}