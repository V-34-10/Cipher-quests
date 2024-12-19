package com.ciphero.questa.adapters.puzzle

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ciphero.questa.R
import com.ciphero.questa.model.Puzzle
import com.ciphero.questa.ui.games.dialogs.DialogsBaseGame.startDialogVictoryGamePuzzle
import com.ciphero.questa.ui.games.puzzle.PuzzleGameFragment
import com.ciphero.questa.ui.games.puzzle.image.GeneratorPuzzleImage
import com.ciphero.questa.ui.games.puzzle.timer.TimeBarAnimator
import com.ciphero.questa.utils.AnimatorManager.animatedPuzzle
import java.util.Collections
import kotlin.math.abs

class PuzzleGameAdapter(
    recyclerView: RecyclerView,
    private val cardList: MutableList<Puzzle>,
    context: Context,
    private var timer: TimeBarAnimator,
    private var fragment: PuzzleGameFragment
) : RecyclerView.Adapter<PuzzleGameAdapter.ViewHolder>() {

    private var emptyPosition: Int = cardList.size - 1
    private val puzzleImageSetup = GeneratorPuzzleImage(context)
    private val levelConfig = puzzleImageSetup.gridConfig
    private val winListPuzzle: List<Int> = levelConfig.victoryListPuzzle
    private var timerStarted = false

    init {
        setupItemTouchHelper(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_puzzle_game, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(cardList[position])

    override fun getItemCount(): Int = cardList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.img)
        fun bind(puzzle: Puzzle) {
            imageView.setImageResource(puzzle.img)
            itemView.setOnClickListener { handleItemClick(adapterPosition, itemView) }
        }
    }

    private fun handleItemClick(position: Int, itemView: View) {
        if (canMove(position)) performMove(position, itemView)
        startTimerIfNeeded()
    }

    private fun canMove(clickedPosition: Int): Boolean =
        isValidMove(clickedPosition, emptyPosition, levelConfig.spanGrid)

    private fun performMove(clickedPosition: Int, itemView: View) {
        swapPuzzles(clickedPosition, emptyPosition)
        animatedPuzzle(itemView)
        emptyPosition = clickedPosition
    }

    private fun startTimerIfNeeded() {
        if (!timerStarted) {
            timer.startTimer(fragment)
            timerStarted = true
        }
    }

    private fun swapPuzzles(fromPosition: Int, toPosition: Int) {
        swapCards(cardList, fromPosition, toPosition)
        notifyItemChanged(fromPosition)
        notifyItemChanged(toPosition)

        if (isGameWon()) startDialogVictoryGamePuzzle(fragment)
    }

    private fun isGameWon(): Boolean =
        cardList.zip(winListPuzzle).all { (card, correct) -> card.img == correct }

    private fun swapCards(list: MutableList<Puzzle>, from: Int, to: Int) {
        Collections.swap(list, from, to)
        list[from].id = from
        list[to].id = to
    }

    private fun setupItemTouchHelper(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
            0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                if (isValidMove(from, emptyPosition, levelConfig.spanGrid) &&
                    isValidMove(to, emptyPosition, levelConfig.spanGrid)
                ) {
                    swapPuzzles(from, to)
                    emptyPosition = to
                }
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun isValidMove(clicked: Int, empty: Int, gridSize: Int): Boolean {
        val diff = abs(clicked - empty)
        return (diff == 1 && clicked / gridSize == empty / gridSize) || (diff == gridSize)
    }
}