package com.ciphero.questa.adapters.offerwall

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ciphero.questa.R
import com.ciphero.questa.model.Offerwall

class OfferwallAdapter(private val onClickList: (Offerwall) -> Unit) :
    RecyclerView.Adapter<OfferwallViewHolder>() {
    private var offerwallList: List<Offerwall> = emptyList()

    companion object {
        private const val VIEW_TYPE_LIST = 0
        private const val VIEW_TYPE_GRID = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (offerwallList.size > 3) VIEW_TYPE_GRID else VIEW_TYPE_LIST
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateOfferwallList(newListGames: List<Offerwall>) {
        offerwallList = newListGames
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferwallViewHolder {
        val layoutId = if (viewType == VIEW_TYPE_GRID) {
            R.layout.game_offerwall_item_grid
        } else {
            R.layout.game_offerwall_item
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return OfferwallViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferwallViewHolder, position: Int) =
        holder.bindItemOfferwall(offerwallList[position], onClickList)

    override fun getItemCount(): Int = offerwallList.size
}