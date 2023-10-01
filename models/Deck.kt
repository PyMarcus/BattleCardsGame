package br.com.ifmg.battlecardgame.models

/*
* Modelo para o baralho disponível
* na arena
* */
class Deck(private var cards: MutableList<Map<String, Card>>) {
    public fun getCards(): MutableList<Map<String, Card>>{
        return this.cards
    }

    public fun setCards(ncards: MutableList<Map<String, Card>>){
        this.cards = ncards
    }
}