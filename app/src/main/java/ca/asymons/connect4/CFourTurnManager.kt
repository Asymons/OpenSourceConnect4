package ca.asymons.connect4

/**
 * Created by Root on 2017-12-28.
 */
class CFourTurnManager : TurnManager {

    private val pieceA = '1'
    private val pieceB = '2'
    private var actionPiece = if(Math.random() > 0.5) pieceA else pieceB
    private var gameStatus = true

    override fun nextTurn() {
        actionPiece = if(actionPiece == pieceA) pieceB else pieceA
    }

    override fun getTurn() : Char {
        return actionPiece
    }

    override fun resetGame(){
        if(!gameStatus){
            actionPiece = if(Math.random() > 0.5) pieceA else pieceB
            gameStatus = true
        }
    }

    override fun setGameStatus(status : Boolean) {
        gameStatus = status
    }

    override fun getGameStatus(): Boolean {
        return gameStatus
    }
}