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
        if(!(board.adapter as BoardTileAdapter).turnManager.getGameStatus()){
            val build = AlertDialog.Builder(this)
            build.setMessage((board.adapter as BoardTileAdapter).turnManager.getTurn() + " lost. Play again?")
            build.setPositiveButton("Ok", { _, _ ->
                //TODO FIX THIS LATER, MAKE OK RESET
            })
            build.setNegativeButton("Cancel", { _,_ ->
                //TODO FIX THIS LATER, MAKE CANCEL GO TO HOME SCREEN
            })
            build.create().show()
            //TODO IF USER CLICKS OUTSIDE OF DIALOG, JUST RESET THE GAME FOR THEM
            (board.adapter as BoardTileAdapter).turnManager.resetGame()
            updateTurn()
        }

        updateTurn()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        board.adapter = BoardTileAdapter(7,6)
        board.layoutManager = GridLayoutManager(applicationContext, 6)
        board.setHasFixedSize(true)

        (board.adapter as BoardTileAdapter).addObserver(this)
        updateTurn()

    }

    override fun onDestroy() {
        super.onDestroy()
        (board.adapter as BoardTileAdapter).removeObserver(this)
    }

    private fun updateTurn(){
        if((board.adapter as BoardTileAdapter).turnManager.getTurn().toString() == "1"){
            v_ply_one_container.backgroundColor = Color.parseColor(getString(R.string.colorCurrentTurn))
            v_ply_two_container.backgroundColor = Color.parseColor(getString(R.string.colorTransparent))
        }else if((board.adapter as BoardTileAdapter).turnManager.getTurn().toString() == "2"){
            v_ply_one_container.backgroundColor = Color.parseColor(getString(R.string.colorTransparent))
            v_ply_two_container.backgroundColor = Color.parseColor(getString(R.string.colorCurrentTurn))
        }
    }
}
