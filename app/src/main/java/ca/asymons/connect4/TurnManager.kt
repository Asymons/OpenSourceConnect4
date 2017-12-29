package ca.asymons.connect4

/**
 * Created by Root on 2017-12-28.
 */
interface TurnManager {
    fun nextTurn()
    fun getTurn() : Char
}