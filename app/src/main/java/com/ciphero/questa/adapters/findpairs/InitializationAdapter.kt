package com.ciphero.questa.adapters.findpairs

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.ciphero.questa.model.FindPair

class InitializationAdapter {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FindPairGameAdapter

    fun setAdapter(findPairList: List<FindPair>) {
        adapter = FindPairGameAdapter(findPairList).also { recyclerView.adapter = it }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetAdapter() = adapter.notifyDataSetChanged()

    fun notifyItemChanged(position: Int) = adapter.notifyItemChanged(position)

    fun setItemClickListener(onClick: (FindPair, Int) -> Unit) {
        adapter.onClick = onClick
    }
}