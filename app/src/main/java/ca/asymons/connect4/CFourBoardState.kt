package ca.asymons.connect4

import android.util.Log

/**
 * Created by Root on 2017-12-26.
 */
class CFourBoardState(private val length: Int, private val width: Int) : BoardState {

    private val state = Array(length * width){'0'}

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
        return length * width
    }


    // 21 checks total per move
    override fun checkWin(row : Int, column : Int) : Boolean{
        val p = state[row * width + column]
        Log.d("BoardState", "Character being checked: " + p + " at position " + (row * width + column))
        var win = false
        win = win || checkRowHorizontal(row,column,p)
        Log.d("BoardState", "Horizontal win: " + checkRowHorizontal(row,column,p))
        win = win || checkRowVertical(row,column,p)
        Log.d("BoardState", "Vertical win: " + checkRowVertical(row,column,p))
        win = win || checkRowDiagonal(row,column,p)
        Log.d("BoardState", "Diagonal win: " + checkRowDiagonal(row,column,p))

        return win
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
            if(connected == 4) return true
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
            Log.d("BoardState", "Connected State: " + connected + " at pos: " + ((k + a) * width + l - a) + " with char: " + p)
        }

        return false

    }

    private fun checkRowHorizontal(row : Int, column : Int, p : Char) : Boolean{
        val col = column - 3
        if(row >= length || row < 0) {
            return false
        }
        var connected = 0
        for(a in 0 .. 6){
            if(col + a >= width) break
            if(col + a < 0) continue
            if(state[row * width + col + a] == p)
                connected++
            else connected = 0
            if(connected == 4) return true
        }
        return false
    }


    private fun checkRowVertical(row : Int, column : Int, p : Char) : Boolean{
        if(column >= width || column < 0) {
            return false
        }
        var connected = 0
        for(a in 0 .. 3){
            if(row + a >= length) break
            if(row + a < 0) continue
            if(state[(row + a) * width + column] == p)
                connected++
            else connected = 0
            if(connected == 4) return true
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







}