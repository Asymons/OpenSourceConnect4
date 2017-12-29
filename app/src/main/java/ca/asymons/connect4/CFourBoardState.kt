package ca.asymons.connect4

/**
 * Created by Root on 2017-12-26.
 */
class CFourBoardState(private val length: Int, private val width: Int) : BoardState {

    private val state = Array(length * width){'0'}

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
        val i = row - 3
        val j = column - 3
        val p = state[row * width + column]
        var win = false
        win = win || checkRow(i,j,p)
        win = win || checkRow(i,j,p,enableRow = false)
        win = win || checkRow(i,j,p, enableColumn = false)
        return win
    }

    // is this necessary?
    override fun getBoard(): Array<Char> {
        return state
    }

//    private fun findMatchAroundPiece(row : Int, column : Int): Pair<Int, Int>{
//        val i = if(row == 0) row else row-1
//        val j = if(column == 0) column else column-1
//        for(a in 0..2){
//            if(row + a >= width) continue
//            for(b in 0 .. 2){
//                if(column + b >= length) continue
//                if(state[i][j] == state[i+a][j+b]) return Pair(i+a,j+b)
//            }
//        }
//        return Pair(length,width)
//    }

    private fun checkRow(row : Int, column : Int, p : Char,
                         enableRow : Boolean = true, enableColumn : Boolean = true) : Boolean{
        if(row >= length || column >= width || row < 0 || column < 0) return false
        var connected = 0
        for(a in 0 .. 6){
            if(row + a >= width || column + a >= length) break
            if(row + a < 0 || column + 0 < 0) continue
            if(state[(if(enableRow) row+a else row) * width + (if (enableColumn) column+a else column)] == p)
                connected++
            else connected = 0
            if(connected == 4) return true
        }
        return false
    }







}