package ca.asymons.connect4

/**
 * Created by Root on 2018-01-03.
 */
interface BoardBuffer {
    fun undo() : Pair<Int, Char>
    fun pushPiece(p : Char, col : Int): Int
    fun checkWin(row : Int, column : Int) : Boolean
    fun checkTie() : Boolean
    fun getBoard() : Array<Char>
    fun resetBoard()
    fun isHistoryEmpty() : Boolean
}