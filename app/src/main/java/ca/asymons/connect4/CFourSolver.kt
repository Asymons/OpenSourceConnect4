package ca.asymons.connect4

import android.util.Log

/**
 * Created by Root on 2018-01-27.
 */
class CFourSolver {
    private val WIDTH = 7
    private val HEIGHT = 6
    private val columnOrder = Array(WIDTH){0}
    var nodeCount = 0
    var bestColumn = 0

    private fun negamax(state: CFourBoardBuffer, p: Char, alpha: Int, beta: Int): Int{
        nodeCount++
        var mAlpha = alpha
        if(nodeCount > 5000000) {
            Log.d("negamax", "Node count reached")
            return 0
        }
        if(state.checkTie()) {
            Log.d("Negamax", "End State in Tie: \n" + state.toString())
            return 0
        }

        for(i in 0 until WIDTH){
            if(state.canPlay(i) && state.isWinningMove(p,i)){
                Log.d("Negamax", "End State in Win: \n" + state.toString())
                bestColumn = i
                return (WIDTH*HEIGHT+1 - state.getMoves())/2
            }
        }

        var max = (WIDTH*HEIGHT-1 - state.getMoves())/2
        if(beta > max && alpha >= max){
            if(alpha >= max) return max
        }

        for(i in 0 until WIDTH){
            if(state.canPlay(columnOrder[i])){
                val state2 = CFourBoardBuffer(state)
                state2.pushPiece(p, columnOrder[i])

                val score = -negamax(state2, if(p=='1') '2' else '1', -beta, -alpha)
                if(score >= beta) return score
                if(score > alpha) mAlpha = score
            }
        }

        return mAlpha
    }

    fun solve(state: CFourBoardBuffer, p: Char, weak: Boolean = true): Int{
        nodeCount = 0
        bestColumn = 0
        for (i in 0 until WIDTH){
            columnOrder[i] = WIDTH/2 + (1-2*(i%2))*(i+1)/2
        }

        val negamaxVal = if(weak){
            negamax(state,p,-1,1)
        }else{
            negamax(state,p,-WIDTH*HEIGHT/2,WIDTH*HEIGHT/2)
        }
        Log.d("Negamax", "Final Value: " + negamaxVal)
        Log.d("best column", bestColumn.toString())
        return negamaxVal
    }

}