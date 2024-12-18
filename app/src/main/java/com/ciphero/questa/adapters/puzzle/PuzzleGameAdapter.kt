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

interface PuzzleMoveListener {
    fun onMovePuzzle(move: Int)
}

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_puzzle_game, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(cardList[position])

    override fun getItemCount(): Int = cardList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.img)
        fun bind(puzzle: Puzzle) {
            imageView.setImageResource(puzzle.img)
            itemView.setOnClickListener {
                if (canMoveStepGame(
                        adapterPosition,
                        emptyPosition,
                        levelConfig.spanGrid
                    )
                ) {
                    swapPuzzles(adapterPosition, emptyPosition)
                    animatedPuzzle(itemView)
                    emptyPosition = adapterPosition
                }
                if (!timerStarted) {
                    timer.startTimer(fragment)
                    timerStarted = true
                }
            }
        }
    }

    init {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
            0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                if (canMoveStepGame(
                        viewHolder.adapterPosition,
                        emptyPosition,
                        levelConfig.spanGrid
                    ) &&
                    canMoveStepGame(
                        target.adapterPosition,
                        emptyPosition,
                        levelConfig.spanGrid
                    )
                ) {
                    swapPuzzles(viewHolder.adapterPosition, target.adapterPosition)
                    emptyPosition = target.adapterPosition
                }
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun swapPuzzles(fromPosition: Int, toPosition: Int) {
        swapCardsGame(cardList, fromPosition, toPosition)

        notifyItemChanged(fromPosition)
        notifyItemChanged(toPosition)

        if (checkCard(cardList, winListPuzzle)) startDialogVictoryGamePuzzle(fragment)
    }

    fun canMoveStepGame(clickedPosition: Int, emptyPosition: Int, gridSize: Int): Boolean {
        val rowDiff = abs((clickedPosition / gridSize) - (emptyPosition / gridSize))
        val colDiff = abs((clickedPosition % gridSize) - (emptyPosition % gridSize))
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1)
    }

    private fun checkCard(cardList: List<Puzzle>, correctCard: List<Int>): Boolean =
        cardList.zip(correctCard).all { (card, correct) -> card.img == correct }

    private fun swapCardsGame(cardList: MutableList<Puzzle>, fromPosition: Int, toPosition: Int) {
        Collections.swap(cardList, fromPosition, toPosition)
        cardList[fromPosition].id = fromPosition
        cardList[toPosition].id = toPosition
    }
}