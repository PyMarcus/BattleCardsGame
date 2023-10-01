package br.com.ifmg.battlecardgame.views

import br.com.ifmg.battlecardgame.controllers.impl.ArenaController

/*
* Classe responsável por desenhar na tela os
* objetos do jogo, por exemplo, os players, os monstros
* e textos que serão representados.
* */
class Draw {
    companion object{
        private var monster: String = "                 \n" +
                                        "╰╮╰╮      ╭───╮\n" +
                                        "╰╮╰╮╰╮    │^_^│\n" +
                                        "   ╰╮╰────╯  ╭╯\n" +
                                        "    ╰┬╮ ╭─┬╮ │ \n" +
                                        "     ╰╰─╯ ╰╰─╯ "
        private var equipament: String = "      \n" +
                                        " ╭───╮ \n" +
                                        " ╞═●═╡ \n" +
                                        " ╰───╯ \n" +
                                        "       "
        private var playerOne: String = " ╭───╮ \n" +
                                        " ├■_■┤ \n" +
                                        "╭╰───╯╮\n" +
                                        "╰├───┤╯\n" +
                                        " │_|_│ "
        private var playerTwo: String = " ╭───╮ \n" +
                                        " ├'_'┤ \n" +
                                        "╭╰───╯╮\n" +
                                        "╰├───┤╯\n" +
                                        " │_|_│ "
        private var gameLogo: String = """JOSE GI OH - BATTLE CARDS GAME!!!"""
        private var winText: String = "YOU WIN!!!"
        private var loseText: String = "YOU LOSE!!!"
        private var nextRound: String = "NEXT ROUND!"

        public fun createMonster(): String{
            return monster
        }

        public fun createEquipament(): String{
            return equipament
        }

        public fun createPlayerOne(): String{
            return playerOne
        }

        public fun createPlayerTwo(): String{
            return playerTwo
        }

        public fun gameLogo(): String{
            return gameLogo
        }

        public fun winText(): String{
            return winText
        }

        public fun loseText(): String{
            return loseText
        }

        public fun nextRound(): String{
            return nextRound
        }

        // inicia o jogo, de fato
        public fun runAndPlay(){
            val arena: ArenaController = ArenaController()
            arena.loopForever()
        }
    }
}