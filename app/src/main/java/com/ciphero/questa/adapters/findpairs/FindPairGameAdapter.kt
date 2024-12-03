package com.ciphero.questa.adapters.findpairs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ciphero.questa.R
import com.ciphero.questa.model.FindPair

class FindPairGameAdapter(private val findPairList: List<FindPair>) :
    RecyclerView.Adapter<FindPairViewHolder>() {
    var onClick: ((FindPair, Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindPairViewHolder =
        FindPairViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_find_pair, parent, false)
        ) { position -> onClick?.invoke(findPairList[position], position) }

    override fun onBindViewHolder(holder: FindPairViewHolder, position: Int) =
        holder.bind(findPairList[position], position)

    override fun getItemCount(): Int = findPairList.size
}