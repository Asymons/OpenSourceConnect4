package ca.asymons.connect4

import java.util.*


/**
 * Created by Root on 2018-01-03.
 */
class CFourBoardBuffer(row : Int, col : Int) : BoardBuffer {

    private val history = Stack<Pair<Int, Char>>()
    private val data = CFourBoardState(row,col)

    override fun pushPiece(p: Char, col: Int): Int {
        val pos = data.pushPiece(p,col)
        history.add(Pair(pos, p))
        return pos
    }

    override fun checkWin(row: Int, column: Int): Boolean {
        return data.checkWin(row,column)
    }

    override fun checkTie(): Boolean {
        return data.checkTie()
    }

    override fun getBoard(): Array<Char> {
        return data.getBoard()
    }

    override fun resetBoard() {
        history.clear()
        return data.resetBoard()
    }
    override fun undo() : Pair<Int, Char> {
        val removed = history.pop()
        data.updatePiece(removed.first, '0')
        return removed
    }

    override fun isHistoryEmpty() : Boolean{
        return history.isEmpty()
    }
}