package br.com.ifmg.battlecardgame.controllers

import br.com.ifmg.battlecardgame.models.Player

/*
* Contrato entre os players
* ambos podem atacar ou se defender
* caso o resultado seja efetivo, o retorno será true
* do contrário, false e resultará nas penalidades estabelecidas
* */
interface IPlayer {
    public fun atack(playerEnemy: Player, cardEnemy: Int): Boolean
    public fun defend(playerEnemy: Player, enemyCard: Int): Boolean
}