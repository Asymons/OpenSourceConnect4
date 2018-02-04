package ca.asymons.connect4

import android.util.Log
import java.util.*


/**
 * Created by Root on 2018-01-03.
 */
class CFourBoardBuffer : BoardBuffer {

    private var row = 0
    private var col =  0
    private var history = Stack<Pair<Int, Char>>()
    private var data = CFourBoardState(row,col)

    constructor(row : Int, col : Int){
        this.row = row
        this.col = col
        this.data = CFourBoardState(row,col)
        this.data.testInitialize()
    }

    constructor(buffer: CFourBoardBuffer){
        this.row = buffer.row
        this.col = buffer.col
        this.history = Stack()
        this.history.addAll(buffer.history)

        this.data = CFourBoardState(row,col)
        this.data.addAll(buffer.data)
    }

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

    fun isWinningMove(p: Char, col: Int): Boolean{
        return data.isWinningMove(p,col)
    }

    fun canPlay(column: Int): Boolean{
        return data.canPlay(column)
    }

    fun getMoves(): Int{
        return data.getMoves()
    }

    fun testInitialize() {
        data.testInitialize()
    }

    override fun toString(): String{
        val stringBuffer = StringBuffer()
        for(i in 0 until data.getBoard().size){
            if(i % row == 0) stringBuffer.append('\n')
            stringBuffer.append(data.getBoard()[i])
        }
        return stringBuffer.toString()
    }

}