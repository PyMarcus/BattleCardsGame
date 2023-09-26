package br.com.ifmg.battlecardgame.controllers

import br.com.ifmg.battlecardgame.models.Player
import br.com.ifmg.battlecardgame.tools.Draw

abstract class AbstractArena {
    open fun welcomeToGame(){
        println(Draw.gameLogo())
    }
    abstract fun loopForever()
    abstract fun choiceYourPlayer(): Array<Int>
    abstract fun firstRound(firstPlayerChoiced: Int)
    abstract fun win(player: Player)
    abstract fun lose(player: Player)
    abstract fun nextRound()
}