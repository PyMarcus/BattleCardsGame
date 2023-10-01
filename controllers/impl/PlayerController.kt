package br.com.ifmg.battlecardgame.controllers.impl

import br.com.ifmg.battlecardgame.controllers.IPlayer
import br.com.ifmg.battlecardgame.models.Card
import br.com.ifmg.battlecardgame.models.Player
import java.lang.Exception

class PlayerController(private val currentPlayer: Player) : IPlayer {

    public fun getCurrentPlayer(): Player {
        return this.currentPlayer
    }

    // ataca o inimigo com a posicao de carta escolhida no tabuleiro
    // verifica, conforme as regras, se a condição de ataque é possível
    override fun atack(playerEnemy: Player, myCardChoose: Int, cardEnemy: Int): Boolean {
        println(
                "Ataque do player${currentPlayer.getOption()} " +
                        "sobre player: ${playerEnemy.getOption()}"
        )
        val card: Card = playerEnemy.getCardsInGame(cardEnemy)
        println(
                "INFORMACOES DA CARTA player${playerEnemy.getOption()}: ${card.getName()} D:${card.getDefense()} " +
                        "A:${card.getAtack()} M ${card.getModeAtack()}"
        )
        println(
                "INFORMACOES DA CARTA player${currentPlayer.getOption()}: ${currentPlayer.getCardsInGame(myCardChoose).getName()} D:${currentPlayer.getCardsInGame(myCardChoose).getDefense()} " +
                        "A:${currentPlayer.getCardsInGame(myCardChoose).getAtack()} M ${currentPlayer.getCardsInGame(myCardChoose).getModeAtack()}"
        )
        if (currentPlayer.getCardsInGame(myCardChoose).getModeAtack()) {
            if (card.getDefense() <= currentPlayer.getCardsInGame(myCardChoose).getAtack()) {
                return true
            }
        } else {
            println(
                    "Esta carta ${currentPlayer.getCardsInGame(myCardChoose).getName()} nao esta em modo ataque."
            )
            throw (Exception("Acao invalida, modo incompativel"))
        }

        return false
    }

    // defende do inimigo com a posicao de carta escolhida no tabuleiro
    // verifica, conforme as regras, se a condição de ataque é possível
    override fun defend(playerEnemy: Player, myCardChoose: Int, enemyCard: Int): Boolean {
        println(
                "Defesa do player${currentPlayer.getOption()} " +
                        "sobre o player: ${playerEnemy.getOption()}"
        )
        val card: Card = playerEnemy.getCardsInGame(enemyCard)
        println(
                "INFORMACOES DA CARTA player${playerEnemy.getOption()}: ${card.getName()} D:${card.getDefense()} " +
                        "A:${card.getAtack()} M ${card.getModeAtack()}"
        )
        println(
                "INFORMACOES DA CARTA player${currentPlayer.getOption()}: ${currentPlayer.getCardsInGame(myCardChoose).getName()} D:${currentPlayer.getCardsInGame(myCardChoose).getDefense()} " +
                        "A:${currentPlayer.getCardsInGame(myCardChoose).getAtack()} M ${currentPlayer.getCardsInGame(myCardChoose).getModeAtack()}"
        )
        if (!currentPlayer.getCardsInGame(myCardChoose).getModeAtack() && card.getModeAtack()) {
            if (card.getAtack() <= currentPlayer.getCardsInGame(myCardChoose).getDefense()) {
                return true
            }
        } else {
            println(
                    "Esta carta: ${currentPlayer.getCardsInGame(myCardChoose).getName()} nao esta em modo defesa."
            )
            throw (Exception("Acao invalida, modo incompativel"))
        }
        return false
    }
}
