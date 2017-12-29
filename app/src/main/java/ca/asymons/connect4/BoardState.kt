package ca.asymons.connect4

/**
 * Created by Root on 2017-12-26.
 */
interface BoardState {
    fun pushPiece(p : Char, col : Int): Int
    fun checkWin(row : Int, column : Int) : Boolean
    fun getBoard() : Array<Char>
    fun resetBoard()
}