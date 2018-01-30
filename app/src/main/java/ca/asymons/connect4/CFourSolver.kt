package ca.asymons.connect4

import android.util.Log

/**
 * Created by Root on 2018-01-27.
 */
class CFourSolver {
    private val WIDTH = 7
    private val HEIGHT = 6
    var nodeCount = 0
    var bestColumn = 0

    private fun negamax(state: CFourBoardBuffer, p: Char): Int{
        nodeCount++
        if(state.checkTie()) return 0
        Log.d("Negamax", "past tie")
        for(i in 0..WIDTH){
            if(state.canPlay(i) && state.isWinningMove(p,i))
                return (WIDTH*HEIGHT+1 - state.getMoves())/2
        }
        Log.d("Negamax", "past win")


        var bestScore = -WIDTH*HEIGHT

        Log.d("Negamax", "can play: " + state.canPlay(0))

        for(i in 0..WIDTH){
            if(state.canPlay(i)){
                val state2 = CFourBoardBuffer(state)
                val pos = state2.pushPiece(p, i)
                Log.d("Negamax", "New State: " + state2.getBoard()[pos])
                Log.d("Negamax", "Old State: " + state.getBoard()[pos])

                val score = -negamax(state2, if(p=='1') '2' else '1')
                if(score > bestScore){
                    bestScore = score
                    bestColumn = i
                    Log.d("Best Column: ", bestColumn.toString())
                }
            }
        }

        Log.d("Negamax", "returning best score")
        Log.d("Negamax", "best score: " + bestScore)

        return bestScore
    }

    fun solve(state: CFourBoardBuffer, p: Char): Int{
        nodeCount = 0
        bestColumn = 0
        val negamaxVal = negamax(state, p)
        Log.d("Negamax", "Final Value: " + negamaxVal)
        return negamaxVal
    }

}