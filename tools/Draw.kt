package br.com.ifmg.battlecardgame.tools

/*
* Classe responsável por desenhar na tela os
* objetos do jogo, por exemplo, os players, os monstros
* e textos que serão representados.
* */
class Draw {
    companion object{
        private var monster: String = "Monster:\n             \n" +
                                        "╰╮╰╮      ╭───╮\n" +
                                        "╰╮╰╮╰╮    │^_^│\n" +
                                        "   ╰╮╰────╯  ╭╯\n" +
                                        "    ╰┬╮ ╭─┬╮ │ \n" +
                                        "     ╰╰─╯ ╰╰─╯ "
        private var equipament: String = " Equipament: \n" +
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
            return this.monster
        }

        public fun createEquipament(): String{
            return this.equipament
        }

        public fun createPlayerOne(): String{
            return this.playerOne
        }

        public fun createPlayerTwo(): String{
            return this.playerTwo
        }

        public fun gameLogo(): String{
            return this.gameLogo
        }

        public fun winText(): String{
            return this.winText
        }

        public fun loseText(): String{
            return this.loseText
        }

        public fun nextRound(): String{
            return this.nextRound
        }
    }
}