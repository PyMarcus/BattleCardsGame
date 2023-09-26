package br.com.ifmg.battlecardgame.controllers.impl

import br.com.ifmg.battlecardgame.controllers.IPlayer
import br.com.ifmg.battlecardgame.models.Card
import br.com.ifmg.battlecardgame.models.Player

class PlayerController(private val currentPlayer: Player,
                       private var myCardChoiced: Int): IPlayer {

    // ataca o inimigo com a posicao de carta escolhida no tabuleiro
    // verifica, conforme as regras, se a condição de ataque é possível
    override fun atack(playerEnemy: Player, cardEnemy: Int): Boolean {
        println("Attack from player${currentPlayer.getOption()} " +
                "on player: ${playerEnemy.getOption()}")
        val card: Card = playerEnemy.getCardsInGame(cardEnemy)
        if(card.getDefense() <= currentPlayer.getCardsInGame(myCardChoiced).getAtack()){
            return true
        }
        return false
    }

    // defende do inimigo com a posicao de carta escolhida no tabuleiro
    // verifica, conforme as regras, se a condição de ataque é possível
    override fun defend(playerEnemy: Player, enemyCard: Int): Boolean {
        println("Defense of player${currentPlayer.getOption()} " +
                "from player: ${playerEnemy.getOption()}")
        val card: Card = playerEnemy.getCardsInGame(enemyCard)
        if(card.getAtack() <= currentPlayer.getCardsInGame(myCardChoiced).getDefense()){
            return true
        }
        return false
    }

}