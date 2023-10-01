package br.com.ifmg.battlecardgame.models

import br.com.ifmg.battlecardgame.tools.Tools

/*
* Arena é onde o game acontece,
* logo, ela comporta os dois jogadores e o baralho que será
* passado para ambos
* */
class Arena {
    private var cards: MutableList<Map<String, Card>> = Tools.getCards()

    public fun getCards(): MutableList<Map<String, Card>>{
        return this.cards
    }
}