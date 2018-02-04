package ca.asymons.connect4

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import org.jetbrains.anko.find
import java.util.*

/**
 * Created by Root on 2017-12-28.
 */
class BoardTileAdapter(private val row: Int, private val col: Int, private val turnManager: TurnManager) : RecyclerView.Adapter<BoardTileAdapter.ViewHolder>() {

    private val data: CFourBoardBuffer = CFourBoardBuffer(row, col)
    private val observers = ArrayList<BoardStateObserver>()
    private val solver = CFourSolver()
    private var onBind : Boolean = false

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        onBind = true
        holder?.bind(position)
        onBind = false
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v : View = LayoutInflater.from(parent?.context).inflate(R.layout.board_tile, parent!!, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return row * col
    }

    fun solve() {
        solver.solve(data, turnManager.getTurn());
    }


    private fun pushPiece(position : Int){
//        Log.d("Board", "Clicked: " + position)
//        Log.d("Board", "Column: " + (position % row))
        val a = data.pushPiece(turnManager.getTurn(), position % row)
//        Log.d("Board", "Clicked: " + row)
        if(!onBind) notifyItemChanged(a)
        if(a < row * col) turnManager.nextTurn()
        if(a < row * col && data.checkWin(a / row, a % row)){
            data.resetBoard()
            turnManager.setGameStatus(false)
            if(!onBind) notifyDataSetChanged()
        }else if(a < row * col && data.checkTie()){
            data.resetBoard()
            turnManager.setGameStatus(false)
            if(!onBind) notifyDataSetChanged()
        }
//        Log.d("Board", "Check Win: " + data.checkWin(position / row, position % row))
        notifyObservers()
    }

    private fun updateBoard(position : Int, piece : View){
        refreshImages(piece, position)
        piece.setOnClickListener {
            pushPiece(position)
            // log for next option...
//            solver.solve(data, turnManager.getTurn())
            // this logic needs to be updated
            if(turnManager.isAi()){
                val random = Random()
                val newPos = random.nextInt(row * col)
                pushPiece(newPos)
            }
        }
    }

    fun addObserver(obs : BoardStateObserver){
        observers.add(obs)
    }

    fun removeObserver(obs : BoardStateObserver){
        observers.remove(obs)
    }

    fun notifyObservers(){
        for(obs in observers) obs.update()
    }

    fun undo(){
        if(!data.isHistoryEmpty()){
            turnManager.nextTurn()
            val removed = data.undo()
            notifyItemChanged(removed.first)
        }
    }

    fun getTurn() : Char{
        return turnManager.getTurn()
    }

    fun getWinner() : Char{
        turnManager.nextTurn()
        return turnManager.getTurn()
    }

    fun getGameStatus() : Boolean {
        return turnManager.getGameStatus()
    }

    fun resetGame() {
        turnManager.resetGame()
    }

    private fun refreshImages(piece : View, position : Int){
//        Log.d("Board", "Setting Image at: " + position)
//        Log.d("Current Board", data.getBoard().size.toString())
        if(position < data.getBoard().size){
            when {
                data.getBoard()[position] == '0' -> piece.setBackgroundResource(R.drawable.ic_piece_black)
                data.getBoard()[position] == '1' -> piece.setBackgroundResource(R.drawable.ic_piece_yellow)
                data.getBoard()[position] == '2' -> piece.setBackgroundResource(R.drawable.ic_piece_red)
            }
            piece.minimumHeight = piece.width
        }
//        Log.d("Board", "Width: " + piece.width + " Height: " + piece.height + " Min Height: " + piece.minimumHeight + " Min Width: " + piece.minimumWidth)
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {

        private val piece : ImageView = v.find(R.id.piece)

        init{
            v.minimumHeight = v.width
            piece.minimumHeight = piece.width
        }

        fun bind(position: Int){
            updateBoard(position, piece)
        }

    }

    init {
        data.testInitialize()
    }

}