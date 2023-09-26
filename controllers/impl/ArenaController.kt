package br.com.ifmg.battlecardgame.controllers.impl

import br.com.ifmg.battlecardgame.controllers.AbstractArena
import br.com.ifmg.battlecardgame.models.Arena
import br.com.ifmg.battlecardgame.models.Player
import br.com.ifmg.battlecardgame.tools.Draw
import br.com.ifmg.battlecardgame.tools.Tools

class ArenaController:AbstractArena() {

    private var arena: Arena = Arena()

    override fun loopForever() {
        welcomeToGame()
        val choice: Array<Int> = choiceYourPlayer()
        val firstPlayer: Int = choice[0]

        while (true){

        }
    }

    override fun choiceYourPlayer(): Array<Int> {
        var arr: Array<Int> = arrayOf()
        println("Choice: \nPlayer1: \n${Draw.createPlayerOne()}  \nPlayer2: \n${Draw.createPlayerTwo()}\n")
        val optionOne = Tools.entry()
        val optionTwo = Tools.entry()
        arr[0] = optionOne
        arr[1] = optionTwo

        return arr
    }

    override fun firstRound(firstPlayerChoiced: Int) {

    }

    override fun win(player: Player) {
        println(Draw.winText())
    }

    override fun lose(player: Player) {
        println(Draw.loseText())
    }

    override fun nextRound() {
        println(Draw.nextRound())
    }

}