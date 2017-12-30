package ca.asymons.connect4

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import org.jetbrains.anko.find
import org.jetbrains.anko.image

/**
 * Created by Root on 2017-12-28.
 */
class BoardTileAdapter(private val length : Int, private val width : Int) : RecyclerView.Adapter<BoardTileAdapter.ViewHolder>() {

    private val data = CFourBoardState(length,width)
    private val observers = ArrayList<BoardStateObserver>()
    var turnManager = CFourTurnManager()

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v : View = LayoutInflater.from(parent?.context).inflate(R.layout.board_tile, parent!!, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return length * width
    }

    fun updatePosition(row : Int, column : Int, c : Char){
        val position = row * width + column
        data.getBoard()[position] = c
    }

    fun updatePosition(position : Int, c : Char){
        data.getBoard()[position] = c
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

    private fun refreshImages(piece : View, position : Int){
        Log.d("Board", "Setting Image at: " + position)
        when {
            data.getBoard()[position] == '0' -> piece.setBackgroundResource(R.drawable.ic_piece_black)
            data.getBoard()[position] == '1' -> piece.setBackgroundResource(R.drawable.ic_piece_blue)
            data.getBoard()[position] == '2' -> piece.setBackgroundResource(R.drawable.ic_piece_red)
        }
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {

        private val piece : ImageView = v.find(R.id.piece)

        fun bind(position: Int){
            refreshImages(piece, position)
            piece.setOnClickListener {
                val beforePieceChange = data.getBoard()[position]
                Log.d("Board", "Clicked: " + position)
                val a = data.pushPiece(turnManager.getTurn(), position % width)
                Log.d("Board", "Clicked: " + a)
                notifyItemChanged(a)
                if(a < length * width) turnManager.nextTurn()
                if(data.checkWin(a/width, a % width)){
                    data.resetBoard()
                    turnManager.setGameStatus(false)
                    notifyDataSetChanged()
                }
                Log.d("Board", "Check Win: " + data.checkWin(position/width, position % width))
                notifyObservers()
            }
        }

    }

}