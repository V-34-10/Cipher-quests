package com.ciphero.questa.adapters.findpairs

import android.annotation.SuppressLint
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciphero.questa.databinding.FragmentFindPairGameBinding
import com.ciphero.questa.model.FindPair

class InitializationAdapter(var binding: FragmentFindPairGameBinding) {
    private val recyclerView: RecyclerView by lazy {
        binding.sceneFindGame.apply {
            layoutManager = GridLayoutManager(context, 3)
        }
    }
    private var adapter: FindPairGameAdapter? = null

    fun setAdapter(findPairList: List<FindPair>) {
        adapter = FindPairGameAdapter(findPairList).also { recyclerView.adapter = it }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetAdapter() = adapter?.notifyDataSetChanged()

    fun notifyItemChanged(position: Int) = adapter?.notifyItemChanged(position)

    fun setItemClickListener(onClick: (FindPair, Int) -> Unit) {
        adapter?.onClick = onClick
    }
}