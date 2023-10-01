package br.com.ifmg.battlecardgame.controllers.impl

import br.com.ifmg.battlecardgame.controllers.IDeck
import br.com.ifmg.battlecardgame.models.Card
import br.com.ifmg.battlecardgame.models.Deck
import java.util.Vector

class DeckController(private var cards: MutableList<Map<String, Card>>) : IDeck {
    private var deck: Deck = Deck(cards)
    private val arenaCards: MutableList<Map<String, Card>> = this.deck.getCards()

    override fun shuffle(used: Vector<Int>): MutableList<Map<String, Card>> {
        for (i in used) {
            for (j in (0..this.arenaCards.size)) {
                if (i == j) {
                    this.arenaCards.removeAt(j)
                }
            }
        }
        arenaCards.shuffle()
        return arenaCards
    }

    override fun toDistribute(amount: Int): MutableList<Map<String, Card>> {
        return this.arenaCards.subList(0, amount)
    }

    public fun toDistributeNext(next: Int): Map<String, Card> {
        try {
            return this.arenaCards[next]
        } catch (e: Exception) {
            throw (Exception("Terminou o baralho"))
        }
    }

    // para a primeira vez, embaralha também.
    public fun shuffle() {
        this.cards.shuffle()
    }

    public fun finishCards(): Boolean {
        return this.arenaCards.isEmpty()
    }

    public fun setCards(ncards: MutableList<Map<String, Card>>) {
        this.cards = ncards
    }

    public fun getCards(): MutableList<Map<String, Card>> {
        return this.cards
    }
}
