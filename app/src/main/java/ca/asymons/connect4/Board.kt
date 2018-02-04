package ca.asymons.connect4

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TableRow
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_board.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.padding
import org.jetbrains.anko.toast

class Board : AppCompatActivity(), BoardStateObserver {

    override fun update() {
        if(!(board.adapter as BoardTileAdapter).getGameStatus()){
            val build = AlertDialog.Builder(this)
            build.setMessage((board.adapter as BoardTileAdapter).getWinner() + " won! Play again?")
            build.setPositiveButton("Ok", { _, _ ->
                (board.adapter as BoardTileAdapter).resetGame()
                updateTurn()
            })
            build.setNegativeButton("Cancel", { _,_ ->
                finish()
            })

            build.setOnCancelListener {
                finish()
            }

            build.create().show()

        }

        updateTurn()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        supportActionBar!!.hide()

        when (intent.extras.getInt("mode")) {
            1 -> startSinglePlayer()
            2 -> startTwoPlayer()
            3 -> startMultiPlayer()
        }

        Log.d("Board", "Recorded Mode: " + intent.extras.getInt("mode"))

        btn_exit_game.setOnClickListener {
            finish()
        }

        btn_pause_game.setOnClickListener {
            val build = AlertDialog.Builder(this)
            build.setMessage("Game Paused <Currently does nothing>")
            build.setPositiveButton("Ok", { _, _ ->
            })
            build.setNegativeButton("Cancel", { _,_ ->
            })
            build.create().show()
        }

        btn_undo_move.setOnClickListener{
            (board.adapter as BoardTileAdapter).undo()
        }

        btn_solve_game.setOnClickListener {
            (board.adapter as BoardTileAdapter).solve()
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        (board.adapter as BoardTileAdapter).removeObserver(this)
    }

    private fun updateTurn(){
        if((board.adapter as BoardTileAdapter).getTurn().toString() == "1"){
            v_ply_one_container.backgroundColor = Color.parseColor(getString(R.string.colorCurrentTurn))
            v_ply_two_container.backgroundColor = Color.parseColor(getString(R.string.colorTransparent))
        }else if((board.adapter as BoardTileAdapter).getTurn().toString() == "2"){
            v_ply_one_container.backgroundColor = Color.parseColor(getString(R.string.colorTransparent))
            v_ply_two_container.backgroundColor = Color.parseColor(getString(R.string.colorCurrentTurn))
        }
    }

    private fun startSinglePlayer(){
        board.adapter = BoardTileAdapter(7,6, OnePlayerCFourTurnManager())
        board.layoutManager = GridLayoutManager(applicationContext, 7)
        board.setHasFixedSize(true)
        board.addItemDecoration(BoardAdapterDecorator(0))

        (board.adapter as BoardTileAdapter).addObserver(this)
        updateTurn()


    }

    private fun startTwoPlayer(){
        board.adapter = BoardTileAdapter(7,6, TwoPlayerCFourTurnManager())
        board.layoutManager = GridLayoutManager(applicationContext, 7)
        board.setHasFixedSize(true)
        board.addItemDecoration(BoardAdapterDecorator(0))

        (board.adapter as BoardTileAdapter).addObserver(this)
        updateTurn()
    }

    private fun startMultiPlayer(){
        // currently not implemented
    }
}
