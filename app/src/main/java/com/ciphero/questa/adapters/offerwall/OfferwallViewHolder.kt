package com.ciphero.questa.adapters.offerwall

import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciphero.questa.R
import com.ciphero.questa.model.Offerwall

class OfferwallViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val actionPrompt: Button = itemView.findViewById(R.id.start_btn_game)

    fun bindItemOfferwall(game: Offerwall, onGameClick: (Offerwall) -> Unit) {
        Glide.with(itemView.context).load(game.bgUrl).into(itemView.findViewById(R.id.background_block_game))
        Glide.with(itemView.context).load(game.fgUrl).into(itemView.findViewById(R.id.img_game))
        actionPrompt.text = game.play
        actionPrompt.setOnClickListener { onGameClick(game) }
    }
}