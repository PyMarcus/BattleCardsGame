package br.com.ifmg.battlecardgame.models

/*
* Player model:
* recebe as cartas , como uma lista de maps com categoria, card , em si
* contém ,fixo, o número total de cartas permitidas na mão
* contém o número inicial de pontos, 10k
* contém o número total de cartas no momento, inicio = 0.
* contém uma lista de cartas em jogo.
* contém uma lista de cartas que foram destruídas.
* */
class Player(private var cards: MutableList<Map<String, Card>>) {
    private final val NUM_MAX_CARDS_BY_HAND: Int = 10
    private var punctuation: Int = 10000
    private var totalCards: Int = 0
    private lateinit var cardsInGame: MutableList<Card>
    private lateinit var cardsDestroyed: MutableList<Card>
    private var option: Int = 0

    public fun getPunctuation(): Int{
        return this.punctuation
    }

    public fun setPunctuation(newPunctuation: Int){
        this.punctuation = newPunctuation
    }

    public fun getTotalCards(): Int{
        return this.totalCards
    }

    public fun setTotalCards(newTotal: Int){
        this.totalCards = newTotal
    }

    public fun setOption(newOption: Int){
        this.option = newOption
    }
}