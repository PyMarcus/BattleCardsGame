package br.com.ifmg.battlecardgame.models

import java.lang.Error

/*
* Player model:
* recebe as cartas , como uma lista de maps com categoria, card , em si
* contém ,fixo, o número total de cartas permitidas na mão
* contém o número inicial de pontos, 10k
* contém o número total de cartas no momento, inicio = 0.
* contém uma lista de cartas em jogo.
* contém uma lista de cartas que foram destruídas.
* */
class Player(var cards: MutableList<Map<String, Card>>) {
    private final val NUM_MAX_CARDS_BY_HAND: Int = 10
    private var punctuation: Int = 10000
    private var totalCards: Int = 0
    private var cardsInGame = mutableListOf<Card>()
    private var cardsDestroyed = mutableListOf<Card>()
    private var cardsOnHand = mutableListOf<Card>()
    private var option: Int = 0 // qual player 1 or 2

    public fun getPunctuation(): Int{
        return this.punctuation
    }

    public fun getCardsOnHand(): MutableList<Card>{
        return this.cardsOnHand
    }

    public fun setCardOnHand(card: Card){
        if(cardsOnHand.size <= 10){
            this.cardsOnHand.add(card)
        }else{
            // se estiver com muitas cartas na mao, remove uma delas para receber mais
            this.setCardsDestroyed(this.cardsOnHand.removeAt(0))
            this.cardsOnHand.add(card)
        }
    }

    public fun getCardsDestroyed(): MutableList<Card>{
        return this.cardsDestroyed
    }

    public fun setCardsDestroyed(card: Card){
        println("Adicionando ${card.getName()} a lista de cartas destruidas")
        this.totalCards--
        this.cardsInGame.remove(card)
        this.cardsDestroyed.add(card)
    }

    public fun setPunctuation(newPunctuation: Int){
        this.punctuation -= newPunctuation
    }

    public fun getTotalCards(): Int{
        return this.totalCards
    }

    public fun setTotalCards(newTotal: Int){
        this.totalCards += newTotal
    }

    public fun getCardsInGame(index: Int): Card{
        if(index in (0 until this.cardsInGame.size)){
            return this.cardsInGame[index]
        }
        throw Error("Escolha ($index) de carta incorreta tam:${this.getCardsInGame().size}")
    }

    public fun getCardsInGame(): MutableList<Card>{
        return this.cardsInGame
    }

    public fun setCardsInGame(subCards: MutableList<Map<String, Card>>):Unit {
        for(map in subCards){
            for((_, v) in map){
                this.cardsInGame.add(v)
            }
        }
    }

    public fun getOption(): Int{
        return this.option
    }

    public fun setOption(newOption: Int){
        this.option = newOption
    }

    public fun getOneCard(cardIndex: Int): Card{
        return this.cardsInGame[cardIndex]
    }

    public fun backCardToDeck(card: Card, cardIndex: Int){
        this.cardsInGame[cardIndex] = card
    }

}