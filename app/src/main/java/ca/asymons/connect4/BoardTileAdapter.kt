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
class BoardTileAdapter(private val length : Int, private val width : Int, private val turnManager: TurnManager) : RecyclerView.Adapter<BoardTileAdapter.ViewHolder>() {

    private val data = CFourBoardBuffer(length,width)
    private val observers = ArrayList<BoardStateObserver>()
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
        return length * width
    }


    private fun pushPiece(position : Int){
        Log.d("Board", "Clicked: " + position)
        Log.d("Board", "Column: " + (position % width))
        val a = data.pushPiece(turnManager.getTurn(), position % width)
        Log.d("Board", "Clicked: " + a)
        if(!onBind) notifyItemChanged(a)
        if(a < length * width) turnManager.nextTurn()
        if(a < length * width && data.checkWin(a/width, a % width)){
            data.resetBoard()
            turnManager.setGameStatus(false)
            if(!onBind) notifyDataSetChanged()
        }else if(a < length * width && data.checkTie()){
            data.resetBoard()
            turnManager.setGameStatus(false)
            if(!onBind) notifyDataSetChanged()
        }
        Log.d("Board", "Check Win: " + data.checkWin(position/width, position % width))
        notifyObservers()
    }

    private fun updateBoard(position : Int, piece : View){
        refreshImages(piece, position)
        piece.setOnClickListener {
            pushPiece(position)
            if(turnManager.isAi()){
                val random = Random()
                val newPos = random.nextInt(length * width)
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
        Log.d("Board", "Setting Image at: " + position)
        when {
            data.getBoard()[position] == '0' -> piece.setBackgroundResource(R.drawable.ic_piece_black)
            data.getBoard()[position] == '1' -> piece.setBackgroundResource(R.drawable.ic_piece_blue)
            data.getBoard()[position] == '2' -> piece.setBackgroundResource(R.drawable.ic_piece_red)
        }
        piece.minimumHeight = piece.width
        Log.d("Board", "Width: " + piece.width + " Height: " + piece.height + " Min Height: " + piece.minimumHeight + " Min Width: " + piece.minimumWidth)
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

}