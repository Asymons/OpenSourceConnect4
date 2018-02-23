package ca.asymons.connect4

import android.util.Log
import java.util.*

/**
 * Created by Root on 2018-01-27.
 */
class CFourSolver {
    private val WIDTH = 7
    private val HEIGHT = 6
    private val columnOrder = Array(WIDTH) { 0 }
    private var nodeCount = 0
    private var bestColumn = 0
    private val MAX_NODE_COUNT = 1000000

    private fun negamax(state: CFourBoardBuffer, p: Char, alpha: Int, beta: Int): Int {
        nodeCount++
        var mAlpha = alpha
        if (nodeCount > MAX_NODE_COUNT) {
            Log.d("negamax", "Node count reached")
            return WIDTH * HEIGHT
        }
        if (state.checkTie()) {
            return 0
        }

        for (i in 0 until WIDTH) {
            if (state.canPlay(i) && state.isWinningMove(p, i)) {
                bestColumn = i
                return (WIDTH * HEIGHT + 1 - state.getMoves()) / 2
            }
        }

        val max = (WIDTH * HEIGHT - 1 - state.getMoves()) / 2
        if (beta > max && alpha >= max) {
            if (alpha >= max) return max
        }

        for (i in 0 until WIDTH) {
            if (state.canPlay(columnOrder[i])) {
                val state2 = CFourBoardBuffer(state)
                state2.pushPiece(p, columnOrder[i])

                val score = -negamax(state2, if (p == '1') '2' else '1', -beta, -alpha)
                if (score >= beta) {
                    bestColumn = columnOrder[i]
                    return score
                }
                if (score > alpha){
                    bestColumn = columnOrder[i]
                    mAlpha = score
                }
            }
        }

        return mAlpha
    }

    // solver with node count limit, can implement difficulty setting with node count.
    fun solve(state: CFourBoardBuffer, p: Char, weak: Boolean = true): Int {
        nodeCount = 0
        bestColumn = 0
        for (i in 0 until WIDTH) {
            columnOrder[i] = WIDTH / 2 + (1 - 2 * (i % 2)) * (i + 1) / 2
        }
        val beforeTime = System.currentTimeMillis()
        Log.d("Negamax", "Before Time: " + beforeTime)
        var negamaxVal = if (weak) {
            negamax(state, p, -1, 1)
        } else {
            negamax(state, p, -WIDTH * HEIGHT / 2, WIDTH * HEIGHT / 2)
        }
        Log.d("Negamax", "After Time: " + System.currentTimeMillis().toString())
        Log.d("Negamax", "Difference Time: " + (System.currentTimeMillis() - beforeTime).toString())

        Log.d("Negamax", "Final Value: " + negamaxVal)
        Log.d("best column", bestColumn.toString())
        negamaxVal = bestColumn
        return negamaxVal
    }

}