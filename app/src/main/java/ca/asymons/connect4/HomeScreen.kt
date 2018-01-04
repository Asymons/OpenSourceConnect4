package ca.asymons.connect4

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        val intent = Intent(applicationContext, Board::class.java)
        val bundle = Bundle()
        btn_one_player_mode.setOnClickListener {
            bundle.putInt("mode", 1)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        btn_two_player_mode.setOnClickListener {
            bundle.putInt("mode", 2)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        btn_multiplayer.setOnClickListener {
            val build = AlertDialog.Builder(this)
            build.setMessage("To be implemented.")
            build.setPositiveButton("Ok", { _, _ ->
            })
            build.setNegativeButton("Cancel", { _,_ ->
            })
            build.create().show()
        }
        supportActionBar!!.hide()


    }
}
