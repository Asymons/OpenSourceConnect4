package ca.asymons.connect4

import android.util.Log

/**
 * Created by Root on 2017-12-26.
 */
class CFourBoardState(private val width: Int, private val length: Int) : BoardState {

    private val state = Array(length * width){'0'}
    private var moves = 0

    override fun updatePiece(pos: Int, p: Char) {
        state[pos] = p
    }
    override fun resetBoard() {
        state.fill('0')
    }

    override fun checkTie() : Boolean{
        for(a in 0 until width){
            if(state[a] == '0') return false
        }
        return true
    }

    override fun pushPiece(p : Char, col : Int) : Int{
        if(col >= width) return length * width
        if(state[col] != '0') return length * width
        var end : Int = state.size + col - width
        while(end >= 0) {
            if (state[end] == '0') {
                state[end] = p
                return end
            }
            end -= width
        }
        ++moves
        return length * width
    }

    fun modelPushPiece(p : Char, col : Int) : Int {
        if(col >= width) return length * width
        if(state[col] != '0') return length * width
        var end : Int = state.size + col - width
        while(end >= 0) {
            if (state[end] == '0') {
                return end
            }
            end -= width
        }
        ++moves
        return length * width
    }

    fun modelPushPieceAhead(state: Array<Char>, p : Char, col : Int) : Int{
        if(col >= width) return length * width
        if(state[col] != '0') return length * width
        var end : Int = state.size + col - width
        while(end >= 0) {
            if (state[end] == '0') {
                state[end] = p
                return end
            }
            end -= width
        }
        ++moves
        return length * width
    }


    // 21 checks total per move
    override fun checkWin(row : Int, column : Int) : Boolean{
        val p = state[row * width + column]
//        Log.d("BoardState", "Character being checked: " + p + " at position " + (row * width + column))
        if(p == '0') return false
        var win = false
        win = win || checkRowHorizontal(row,column,p)
//        Log.d("BoardState", "Horizontal win: " + checkRowHorizontal(row,column,p))
        win = win || checkRowVertical(row,column,p)
//        Log.d("BoardState", "Vertical win: " + checkRowVertical(row,column,p))
        win = win || checkRowDiagonal(row,column,p)
//        Log.d("BoardState", "Diagonal win: " + checkRowDiagonal(row,column,p))

        return win
    }

   fun checkWin(pos: Int) : Boolean{
        val row = pos / width
        val column = pos % width
        return checkWin(row,column)
    }

    // is this necessary?
    override fun getBoard(): Array<Char> {
        return state
    }


    private fun checkRowDiagonal(row : Int, column : Int, p : Char) : Boolean{
        val i = row - 3
        val j = column - 3

        var connected = 0
        for(a in 0 .. 6){
            if(i + a >= length || j + a >= width) break
            if(i + a < 0 || j + a < 0) continue
            if(state[(i + a) * width + j + a] == p)
                connected++
            else connected = 0
            if(connected >= 4) return true
        }
        connected = 0

        val k = row - 3
        val l = column + 3

        for(a in 0 .. 6){
            if(k + a >= length || l - a < 0) break
            if(k + a < 0 || l - a >= width) continue
            if(state[(k + a) * width + l - a] == p)
                connected++
            else connected = 0
            if(connected == 4) return true
//            Log.d("BoardState", "Connected State: " + connected + " at pos: " + ((k + a) * width + l - a) + " with char: " + p)
        }

        return false

    }

    private fun checkRowHorizontal(row : Int, column : Int, p : Char) : Boolean{
        if(row >= width || row < 0 || column >= length || column < 0) {
            return false
        }

        val colPosition = row - 3

        var connected = 0
        for(a in 0 .. 6){
            if(colPosition + a >= width) break
            if(colPosition + a < 0) continue
            if(state[row * width + colPosition + a] == p)
                connected++
            else connected = 0
            if(connected >= 4) return true
        }
        return false
    }


    private fun checkRowVertical(row : Int, column : Int, p : Char) : Boolean{
        if(row >= width || row < 0 || column >= length || column < 0) {
            return false
        }

        var connected = 0
        for(a in 0 .. 3){
            if(row + a >= length) break
            if(row + a < 0) break
            if(state[(row + a) * width + column] == p)
                connected++
            else connected = 0
            if(connected >= 4) return true
        }
        return false
    }



    private fun checkRow(row : Int, column : Int, p : Char,
                         enableRow : Boolean = true, enableColumn : Boolean = true) : Boolean{
        if(row >= length || column >= width || row < 0 || column < 0) return false
        var connected = 0
        for(a in 0 .. 6){
            if(row + a >= width || column + a >= length) break
            if(row + a < 0 || column + a < 0) continue
            if(state[(if(enableRow) row+a else row) * width + (if (enableColumn) column+a else column)] == p)
                connected++
            else connected = 0
            if(connected == 4) return true
        }
        return false
    }

    fun isWinningMove(p: Char, col: Int): Boolean{
        val end : Int = pushPiece(p, col)
//        Log.d("Winning Move Check", "End is $end")
        val result = checkWin(end)
        state[end] = '0'
        return result
    }

    fun canPlay(column: Int): Boolean{
        return column in 0..(width - 1) && state[column] == '0'
    }

    fun getMoves(): Int{
        return moves
    }

    fun addAll(other: CFourBoardState){
        for(i in 0 until other.getBoard().size){
            state[i] = other.getBoard()[i]
        }
    }

    fun testInitialize(){
        var c = '1'
//        for(i in 10 until state.size/2){
//            state[i] = c
//            c = if(c == '1') '2' else '1'
//        }

        c = '2'
        for(i in state.size/2 until state.size){
            state[i] = c
            c = if(c == '1') '2' else '1'
        }
    }






}