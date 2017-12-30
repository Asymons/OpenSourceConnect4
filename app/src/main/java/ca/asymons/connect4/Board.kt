package ca.asymons.connect4

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
import org.jetbrains.anko.padding
import org.jetbrains.anko.toast

class Board : AppCompatActivity(), BoardStateObserver {

    override fun update() {
        if(!(board.adapter as BoardTileAdapter).turnManager.getGameStatus()){
            val build = AlertDialog.Builder(this)
            build.setMessage((board.adapter as BoardTileAdapter).turnManager.getTurn() + " lost. Play again?")
            build.setPositiveButton("Ok", { _, _ ->
                (board.adapter as BoardTileAdapter).turnManager.resetGame()
            })
            build.setNegativeButton("Cancel", { _,_ ->

            })

            build.create().show()
        }
        v_current_turn.text = (board.adapter as BoardTileAdapter).turnManager.getTurn().toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        board.adapter = BoardTileAdapter(7,6)
        board.layoutManager = GridLayoutManager(applicationContext, 6)
        board.setHasFixedSize(true)

        (board.adapter as BoardTileAdapter).addObserver(this)
        v_current_turn.text = (board.adapter as BoardTileAdapter).turnManager.getTurn().toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        (board.adapter as BoardTileAdapter).removeObserver(this)
    }
}
