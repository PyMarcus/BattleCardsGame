package br.com.ifmg.battlecardgame.models

import br.com.ifmg.battlecardgame.tools.Tools

/*
* Arena é onde o game acontece,
* logo, ela comporta os dois jogadores e o baralho que será
* passado para ambos
* */
class Arena {
    private var cards: MutableList<Map<String, Card>> = Tools.getCards()
    private var playerOne: Player = Player(cards)
    private var playerTwo: Player = Player(cards)

    public fun getPlayerOne(): Player{
        return this.playerOne
    }

    public fun getPlayerTwo(): Player{
        return this.playerTwo
    }
}