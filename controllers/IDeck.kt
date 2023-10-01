package br.com.ifmg.battlecardgame.controllers

import br.com.ifmg.battlecardgame.models.Card
import java.util.Vector

interface IDeck {
    // embaralha as cartas não utilizadas
    public fun shuffle(used: Vector<Int>): MutableList<Map<String, Card>>

    // entrega as cartas para os jogadores
    public fun toDistribute(amount: Int): MutableList<Map<String, Card>>
}