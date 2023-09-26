package br.com.ifmg.battlecardgame

import br.com.ifmg.battlecardgame.tools.Tools
import br.com.ifmg.battlecardgame.tools.Draw

class Main {
    companion object{
        fun start() {
            println(Draw.createMonster())
            println(Draw.createEquipament())
            println(Draw.gameLogo())
            println(Draw.winText())
            println(Draw.loseText())
            println(Draw.nextRound())
        }
    }
}

fun main(){
    Main.start()
}