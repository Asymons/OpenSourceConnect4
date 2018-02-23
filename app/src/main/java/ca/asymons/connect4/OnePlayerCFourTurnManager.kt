package ca.asymons.connect4

/**
 * Created by Root on 2018-01-03.
 */
class OnePlayerCFourTurnManager : TurnManager {

    private val pieceA = '1'
    private val pieceB = '2'
    // for now, let first player always start
    private var actionPiece = pieceA
    private var gameStatus = true

    override fun nextTurn() {
        actionPiece = if(actionPiece == pieceA) pieceB else pieceA
    }

    override fun resetGame(){
        if(!gameStatus){
            actionPiece = pieceA
            gameStatus = true
        }
    }

    override fun setGameStatus(status : Boolean) {
        gameStatus = status
    }

    override fun getTurn() : Char = actionPiece

    override fun getGameStatus(): Boolean = gameStatus

    override fun isAi(): Boolean = actionPiece == pieceB
}