package br.com.ifmg.battlecardgame.controllers.impl

import br.com.ifmg.battlecardgame.controllers.AbstractArena
import br.com.ifmg.battlecardgame.models.Arena
import br.com.ifmg.battlecardgame.models.Card
import br.com.ifmg.battlecardgame.models.Player
import br.com.ifmg.battlecardgame.views.Draw
import br.com.ifmg.battlecardgame.tools.Tools
import java.util.Vector
import kotlin.system.exitProcess

class ArenaController:AbstractArena() {

    private var arena: Arena = Arena()
    private var cards = arena.getCards()
    private var deck: DeckController = DeckController(cards)
    private var indexNext: Int = 10
    private var newRoundP1: Boolean = false // checa se foi equipado uma carta no round vigente.
    private var newRoundP2: Boolean = false // checa se foi equipado uma carta no round vigente.
    private lateinit var playerOne: Player
    private lateinit var playerTwo: Player
    private lateinit var p1C: PlayerController
    private lateinit var p2C: PlayerController

    public fun getPlayerOne(): Player{
        this.playerOne.setOption(1)
        return this.playerOne
    }

    public fun getPlayerTwo(): Player{
        this.playerTwo.setOption(2)
        return this.playerTwo
    }

    override fun loopForever() {
        welcomeToGame()
        val choice: Array<Int> = choiceYourPlayer()
        val firstPlayer: Int = choice[0]
        firstRound(firstPlayer)
        showPlayersCards(playerOne.getCardsInGame(), playerTwo.getCardsInGame())
        setPlayerCardMode(playerOne)
        setPlayerCardMode(playerTwo)
        showPlayersCards(playerOne.getCardsInGame(), playerTwo.getCardsInGame())

        // com isso, a primeira rodada está pronta.
        // todos tem 5 cartas aleatórias, e ambos definirão modos de ataque para as
        // cartas monstro, os que tiverem monstro, no caso.
        // o loop, portanto, mantém o jogo funcionando
        this.createBanner()
        val playerOneController: PlayerController = PlayerController(playerOne)
        val playerTwoController: PlayerController = PlayerController(playerTwo)
        // variaveis auxilares, pensadas tardiamente :(
        this.p1C = playerOneController
        this.p2C = playerTwoController
        while (true){
            try {
                // verifica se o jogo pode ser finalizado e declara o vencedor
                this.finish()
                // jogador 1 sempre começa a disputa
                println("###".repeat(20))
                this.playerOnesTurn(playerOneController, playerTwoController)
                // agora, a vez do jogador 2
                println("###".repeat(20))
                this.playerTwosTurn(playerTwoController, playerOneController)

                // proximo round
                println("###".repeat(20))
                this.nextRound()
            }catch (e: Exception){
                continue
            }
        }
    }

    // define regras e verificações da vez do player 1
    private fun playerOnesTurn(player: PlayerController, enemy: PlayerController){

        val option: Int = this.playerOneOptions()

        this.showPlayersCards(this.playerOne.getCardsInGame(),
        this.playerTwo.getCardsInGame())

        when(option){
            1 -> {
                println("Ao ataque")
                val a = enemy.getCurrentPlayer()
                val b = this.myCard()
                val c = this.enemyCard()
                val cardEnemy = this.playerTwo.getCardsInGame(c)
                try {
                    val success: Boolean =  player.atack(a,
                        b,
                        c)
                    if(success){
                        println("Player2 -> Pontuacao = ${this.playerTwo.getPunctuation()}")
                        this.playerTwo.setPunctuation(player.getCurrentPlayer().
                        getCardsInGame(b).getAtack()
                        )
                        println("Player2 -> Pontuacao apos ataque = ${this.playerTwo.getPunctuation()}")
                        this.playerTwo.setCardsDestroyed(cardEnemy)
                        this.finish()
                        this.startNewRound()
                    }else{
                        println("Player1 -> Pontuacao = ${this.playerOne.getPunctuation()}")
                        this.playerOne.setPunctuation(enemy.getCurrentPlayer().
                        getCardsInGame(c).getDefense()
                        )
                        println("Player1 -> Pontuacao apos ataque = ${this.playerOne.getPunctuation()}")
                        this.playerTwo.setCardsDestroyed(player.getCurrentPlayer().
                        getCardsInGame(b))
                        this.finish()
                        this.startNewRound()
                    }
                }catch (e: Exception){
                    this.playerOnesTurn(player, enemy)
                }
            }
            2 -> {
                println("Em defesa")
                val a = enemy.getCurrentPlayer()
                val b = this.myCard()
                val c = this.enemyCard()
                val cardEnemy = this.playerTwo.getCardsInGame(c)
                try {
                    val success: Boolean =  player.defend(a,
                        b,
                        c)
                    if(success){
                        println("1)Player2 -> Pontuacao = ${this.playerTwo.getPunctuation()}")
                        this.playerTwo.setPunctuation(player.getCurrentPlayer().
                        getCardsInGame(b).getAtack()
                        )
                        println("1)Player2 -> Pontuacao apos defesa = ${this.playerTwo.getPunctuation()}")
                        this.playerTwo.setCardsDestroyed(cardEnemy)
                        this.startNewRound()
                    }else {
                        println("1)Player1 -> Pontuacao = ${this.playerOne.getPunctuation()}")
                        this.playerOne.setPunctuation(
                            enemy.getCurrentPlayer().getCardsInGame(c).getDefense()
                        )
                        println("1)Player1 -> Pontuacao apos defesa = ${this.playerOne.getPunctuation()}")
                        this.playerOne.setCardsDestroyed(this.playerOne.getCardsInGame(b))
                        this.startNewRound()
                    }
                    this.finish()
                }catch (e: Exception){
                    this.playerOnesTurn(player, enemy)
                }
            }
            3 -> this.buildArmorP1(this.playerOne)
            4 -> {
                this.createBanner()
                this.playerOnesTurn(player, enemy)
            }
            5 -> {
                if(this.playerOne.getCardsOnHand().size > 0){
                    print("Escolher: ")
                    var option = readln().toInt()
                    var c = playerOne.getCardsOnHand()[option]
                    println()
                    print("Substituir a carta:")
                    option = readln().toInt()
                    var cc = this.playerOne.getCardsInGame()[option]
                    this.playerOne.setCardsDestroyed(cc)
                    this.playerOne.getCardsInGame().add(c)
                    this.playerOne.setCardOnHand(cc)
                    println("Troca realizada")
                    println("Modificar modo:")
                    println("1-Ataque 2-Defesa")
                    option = readln().toInt()
                    if(option == 1){
                        this.playerOne.getCardsInGame().last().setModeAtack(true)
                    }else{
                        this.playerOne.getCardsInGame().last().setModeAtack(false)
                    }
                    this.showPlayersCards(this.playerOne.getCardsInGame(), this.playerTwo.getCardsInGame())

                }else{
                    println("Sem cartas na mao ainda")
                }
                this.playerOnesTurn(player, enemy)
            }
            6 -> this.playerTwosTurn(this.p2C, this.p1C)
        }
    }

    // define regras e verificações da vez do player 2
    private fun playerTwosTurn(player: PlayerController, enemy: PlayerController){
        val option: Int = this.playerTwoOptions()

        this.showPlayersCards(this.playerOne.getCardsInGame(),
            this.playerTwo.getCardsInGame())

        when(option){
            1 -> {
                println("Em ataque")
                val a = enemy.getCurrentPlayer()
                val b = this.myCard()
                val c = this.enemyCard()
                val cardEnemy = this.playerOne.getCardsInGame(c)
                try {
                    val success: Boolean =  player.atack(a,
                        b,
                        c)
                    if(success){
                        println("2)Player1 -> Pontuacao = ${this.playerOne.getPunctuation()}")
                        this.playerOne.setPunctuation(player.getCurrentPlayer().
                        getCardsInGame(b).getAtack()
                        )
                        println("2)Player1 -> Pontuacao apos ataque = ${this.playerOne.getPunctuation()}")
                        this.playerOne.setCardsDestroyed(cardEnemy)
                        this.startNewRound()
                    }else{
                        println("2)Player2 -> Pontuacao = ${this.playerTwo.getPunctuation()}")
                        this.playerTwo.setPunctuation(enemy.getCurrentPlayer().
                        getCardsInGame(c).getDefense()
                        )
                        println("2)Player2 -> Pontuacao apos ataque = ${this.playerTwo.getPunctuation()}")
                        this.playerTwo.setCardsDestroyed(player.getCurrentPlayer().
                        getCardsInGame(b))
                        this.startNewRound()
                    }
                    this.finish()
                }catch (e: Exception){
                    this.playerTwosTurn(player, enemy)
                }
            }
            2 -> {
                println("Em defesa")
                val a = enemy.getCurrentPlayer()
                val b = this.myCard()
                val c = this.enemyCard()
                val cardEnemy = this.playerOne.getCardsInGame(c)
                try {
                    val success: Boolean =  player.defend(a,
                        b,
                        c)
                    if(success){
                        println("Player1 -> Pontuacao = ${this.playerOne.getPunctuation()}")
                        this.playerOne.setPunctuation(player.getCurrentPlayer().
                        getCardsInGame(b).getAtack()
                        )
                        println("Player1 -> Pontuacao apos defesa = ${this.playerOne.getPunctuation()}")
                        this.playerOne.setCardsDestroyed(cardEnemy)
                        this.startNewRound()
                    }else {
                        println("Player2 -> Pontuacao = ${this.playerTwo.getPunctuation()}")
                        this.playerTwo.setPunctuation(
                            enemy.getCurrentPlayer().getCardsInGame(c).getDefense()
                        )
                        println("Player2 -> Pontuacao apos defesa = ${this.playerTwo.getPunctuation()}")
                        this.playerTwo.setCardsDestroyed(this.playerTwo.getCardsInGame(b))
                        this.startNewRound()
                    }
                }catch (e: Exception){
                    this.playerTwosTurn(player, enemy)
                }
                this.finish()
            }
            3 -> this.buildArmorP2(this.playerTwo)
            4 -> {
                this.createBanner()
                this.playerTwosTurn(player, enemy)
            }
            5 -> {
                if(this.playerTwo.getCardsOnHand().size > 0){
                    println("Cartas na mão")
                    for((i, card) in playerTwo.getCardsOnHand().withIndex()){
                        println("$i) ${card.getName()} A:${card.getAtack()} D:${card.getDefense()}")
                    }
                    print("Escolher: ")
                    var option = readln().toInt()
                    var c = playerTwo.getCardsOnHand()[option]
                    println()
                    print("Substituir a carta:")
                    option = readln().toInt()
                    var cc = this.playerTwo.getCardsInGame()[option]
                    this.playerTwo.setCardsDestroyed(cc)
                    this.playerTwo.getCardsInGame().add(c)
                    this.playerTwo.setCardOnHand(cc)
                    println("Troca realizada")
                    println("Modificar modo:")
                    println("1-Ataque 2-Defesa")
                    option = readln().toInt()
                    if(option == 1){
                        this.playerTwo.getCardsInGame().last().setModeAtack(true)
                    }else{
                        this.playerTwo.getCardsInGame().last().setModeAtack(false)
                    }
                    this.showPlayersCards(this.playerOne.getCardsInGame(), this.playerTwo.getCardsInGame())
                }
                else{
                    println("Sem cartas na mao, ainda")
                }
                this.playerTwosTurn(player, enemy)

            }
            6 -> this.playerOnesTurn(this.p1C, this.p2C)
        }
    }

    // equipa a carta escolhida pelo player, podendo aumentar a defesa ou o ataque
    private fun buildArmorP1(player: Player){
        println("Escolha um equipamento: ")
        var options: ArrayList<Int> = arrayListOf()
        for((index, card) in player.getCardsInGame().withIndex()){
            if(card.getCategory() == "equipamento"){
                println("$index - ${card.getName()} A: ${card.getAtack()} D:${card.getDefense()}")
                options.add(index)
            }
        }
        if(!options.isEmpty()){
            while(true){
                try{
                        if(!this.newRoundP1){
                            print("\nEscolha: ")
                            val o = readln().toInt()
                            if(o in options){
                                val c = player.getOneCard(o)
                                println()
                                print("Equipar a carta número: ")
                                val oo = readln().toInt()
                                println("P1: Informacoes da carta antes: ${player.getCardsInGame(oo).getName()} A:${player.getCardsInGame(oo).getAtack()} D:${player.getCardsInGame(oo).getDefense()}")
                                if(c.getAtack() != 0){
                                    player.getCardsInGame(oo).setAttack(c.getAtack())
                                }else{
                                    player.getCardsInGame(oo).setDefense(c.getDefense())
                                }
                                println("P1: Informacoes da carta depois: ${player.getCardsInGame(oo).getName()} A:${player.getCardsInGame(oo).getAtack()} D:${player.getCardsInGame(oo).getDefense()}")
                                println("Player1 Equipado")
                                this.newRoundP1 = true
                                player.setCardsDestroyed(c) // a carta de equipamento é destruida, ao ser equipada
                                this.showPlayersCards(this.playerOne.getCardsInGame(),
                                    this.playerTwo.getCardsInGame())
                                this.playerOnesTurn(this.p1C, this.p2C)
                                break
                            }else{
                                println("Opcao invalida")
                                this.playerOnesTurn(this.p1C, this.p2C)
                                break
                            }
                    }else {
                            println("Voce ja equipou uma carta neste round")
                            this.playerOnesTurn(this.p1C, this.p2C)
                            break
                        }
                }catch (e: Exception){
                    continue
                }
            }
        }
        else{
            println("Voce nao tem cartas equipamentos")
            this.playerOnesTurn(this.p1C, this.p2C)
        }
    }

    // equipa a carta escolhida pelo player, podendo aumentar a defesa ou o ataque
    private fun buildArmorP2(player: Player){
        println("Escolha um equipamento: ")
        var options: ArrayList<Int> = arrayListOf()
        for((index, card) in player.getCardsInGame().withIndex()){
            if(card.getCategory() == "equipamento"){
                println("$index - ${card.getName()} A: ${card.getAtack()} D:${card.getDefense()}")
                options.add(index)
            }
        }
        if(!options.isEmpty()){
            while(true){
                try{
                    if(!this.newRoundP2){
                        print("\nEscolha: ")
                        val o = readln().toInt()
                        if(o in options){
                            val c = player.getOneCard(o)
                            print("Equipar a carta número: ")
                            val oo = readln().toInt()
                            println("P2: Informacoes da carta antes: ${player.getCardsInGame(oo).getName()} A:${player.getCardsInGame(oo).getAtack()} D:${player.getCardsInGame(oo).getDefense()}")
                            if(c.getAtack() != 0){
                                player.getCardsInGame(oo).setAttack(c.getAtack())
                                player.setCardsDestroyed(c) // a carta de equipamento é destruída, ao ser equipada
                            }else{
                                player.getCardsInGame(oo).setDefense(c.getDefense())
                                player.setCardsDestroyed(c) // a carta de equipamento é destruida, ao ser equipada
                            }
                            println("P2: Informacoes da carta depois: ${player.getCardsInGame(oo).getName()} A:${player.getCardsInGame(oo).getAtack()} D:${player.getCardsInGame(oo).getDefense()}")
                            println("Player2 Equipado")
                            this.newRoundP2 = true
                            this.playerTwosTurn(this.p2C, this.p1C)
                            break
                        }else{
                            println("Opcao invalida")
                            this.playerTwosTurn(this.p2C, this.p1C)
                            break
                        }
                    }else{
                        println("Voce ja equipou uma carta neste round")
                        this.playerTwosTurn(this.p2C, this.p1C)
                        break
                    }
                }catch (e: Exception){
                    continue
                }
            }
        }
        else{
            println("Voce nao tem cartas equipamentos")
            this.playerTwosTurn(this.p2C, this.p1C)
        }
    }

    private fun startNewRound(){
        this.newRoundP1 = false
        this.newRoundP2 = false
    }

    private fun myCard(): Int{
        do{
            println("Usar carta: ")
            try{
                val choice: Int = readln().toInt()
                if(choice !in (0..4)){
                    continue
                }
                return choice
            }catch (e: Exception){
                continue
            }
        }while (true)
    }

    private fun enemyCard(): Int{
        do{
            println("Contra a carta inimiga: ")
            try{
                val choice: Int = readln().toInt()
                if(choice !in (0..4)){
                    continue
                }
                return choice
            }catch (e: Exception){
                continue
            }
        }while (true)
    }

    private fun playerOneOptions(): Int{
        while(true){
            println("Escolha uma opção:")
            println("1 - Atacar jogador 2")
            println("2 - Defender do jogador 2")
            println("3 - Equipar monstro")
            println("4 - Verificar pontuação")
            println("5 - Trazer carta da mão para o tabuleiro")
            println("6 - Passar a vez")
            println("0 - Sair do jogo")
            print("->")
            val option = readln().toIntOrNull() ?: -1
            if (option == 0){
                println("Player2 Venceu devido a desistencia do Player1")
                exitProcess(0)
            }
            println()
            if(option !in (0..7)){
                continue
            }

            return option
        }
    }

    private fun playerTwoOptions(): Int{
        while(true){
            println("Escolha uma opção:")
            println("1 - Atacar jogador 1")
            println("2 - Defender do jogador 1")
            println("3 - Equipar monstro")
            println("4 - Verificar pontuação")
            println("5 - Trazer carta da mão para o tabuleiro")
            println("0 - Sair do jogo")
            print("->")
            val option = readln().toIntOrNull() ?: -1
            if (option == -1){
                println("Player1 Venceu devido a desistencia do Player2")
                exitProcess(0)
            }
            println()
            if(option !in (0..7)){
                continue
            }

            return option
        }
    }

    override fun choiceYourPlayer(): Array<Int> {
        val arr: Array<Int> = arrayOf(2, 3)
        println("Choice: \nPlayer1: \n${Draw.createPlayerOne()}  \nPlayer2: \n${Draw.createPlayerTwo()}\n")
        val optionOne = Tools.entry()
        var optionTwo = 0
        if(optionOne == 1)
             optionTwo = 2
        else
             optionTwo = 1
        arr[0] = optionOne
        arr[1] = optionTwo

        return arr
    }

    // no primeiro round, os jogadores recebem 5 cartas aleatoria
    override fun firstRound(firstPlayerChoiced: Int){
        this.deck.shuffle()
        val firstDistribuition: MutableList<Map<String, Card>> = this.deck.toDistribute(10)
        if(firstPlayerChoiced == 1) {
            val f = firstDistribuition.subList(0, 5)
            this.playerOne = Player(f)
            this.playerOne.setCardsInGame(f)
            this.playerOne.setTotalCards(5)
            val s = firstDistribuition.subList(5, 10)
            this.playerTwo = Player(s)
            this.playerTwo.setCardsInGame(s)
            this.playerTwo.setTotalCards(5)
            playerOne.setOption(1)
            playerTwo.setOption(2)
        }
        if(firstPlayerChoiced == 2) {
            val f = firstDistribuition.subList(5, 10)
            this.playerOne = Player(f)
            this.playerOne.setTotalCards(5)
            this.playerOne.setCardsInGame(f)
            val s = firstDistribuition.subList(0, 5)
            this.playerTwo = Player(s)
            this.playerTwo.setTotalCards(5)
            this.playerTwo.setCardsInGame(s)
            playerOne.setOption(2)
            playerTwo.setOption(1)
        }
        println("Players has been created!")
        val index: Vector<Int> = used(firstDistribuition)
        this.deck.setCards(this.deck.shuffle(index))
    }

    override fun win(player: Player) {
        println(Draw.winText())
    }

    override fun lose(player: Player) {
        println(Draw.loseText())
    }

    // inicia o novo round, resetando os modos das cartas
    override fun nextRound() {
        this.newRoundP1 = false
        this.newRoundP2 = false
        println(Draw.nextRound())
        this.createBanner()
        setPlayerCardMode(this.playerOne)
        setPlayerCardMode(this.playerTwo)

        // distribuir nova carta aos jogadores
        try {
            val mapCard = this.deck.toDistributeNext(this.indexNext).values.toList()[0]
            this.indexNext++
            val mapCard2 = this.deck.toDistributeNext(this.indexNext).values.toList()[0]
            this.indexNext++
            println("Carta ${mapCard.getName()} adicionada ao baralho do player1")
            println("Carta ${mapCard2.getName()} adicionada ao baralho do player2")
            this.playerOne.setCardOnHand(mapCard)
            this.playerTwo.setCardOnHand(mapCard2)
            if(this.playerOne.getCardsInGame().size < 5){
                this.playerOne.getCardsInGame().add(mapCard)
            }
            if(this.playerTwo.getCardsInGame().size < 5){
                this.playerTwo.getCardsInGame().add(mapCard)
            }
        }catch (e: Exception){
            finish()
        }

    }

    // identifica as cartas usadas e retorna vector com as posicoes
    private fun used(miniDeck: MutableList<Map<String, Card>>): Vector<Int>{
        val vector: Vector<Int> = Vector<Int>()

        for((index, card) in this.cards.withIndex()){
            for((_, miniCard) in miniDeck.withIndex()){
                if(miniCard == card){
                    vector.add(index)
                }
            }
        }
        return vector
    }

    private fun createBanner(){
        this.finish()
        println("---".repeat(20))
        println("PLAYER1: ${this.playerOne.getPunctuation()} PONTOS")
        println("PLAYER2: ${this.playerTwo.getPunctuation()} PONTOS")
        println("---".repeat(20))
    }

    private fun showPlayersCards(cardP1: MutableList<Card>,
                                 cardP2: MutableList<Card>){
        println("CARTAS JOGADOR1: ")
        for((index, card) in cardP1.withIndex()){
            if(card.getCategory() == "monstro"){
                println("$index) ${card.getName()}")
                println("ATAQUE ${card.getAtack()} DEFESA ${card.getDefense()}")
                if(card.getModeAtack())
                    println("EM MODO DE ATAQUE")
                else
                    println("EM MODO DE DEFESA")
                println(Draw.createMonster())
            }else if(card.getCategory() == "equipamento"){
                println("$index) ${card.getName()}")
                println("ATAQUE ${card.getAtack()} DEFESA ${card.getDefense()}")
                println(Draw.createEquipament())
            }
        }
        println("-----------------------------------------")
        println("CARTAS JOGADOR2: ")
        for((index, card) in cardP2.withIndex()){
            if(card.getCategory() == "monstro"){
                println("$index) ${card.getName()}")
                println("ATAQUE ${card.getAtack()} DEFESA ${card.getDefense()}")
                if(card.getModeAtack())
                    println("EM MODO DE ATAQUE")
                else
                    println("EM MODO DE DEFESA")
                println(Draw.createMonster())
            }else if(card.getCategory() == "equipamento"){
                println("$index) ${card.getName()}")
                println("ATAQUE ${card.getAtack()} DEFESA ${card.getDefense()}")
                println(Draw.createEquipament())
            }
        }
    }

    // apenas as cartas monstro podem ter seu modo alternado.
    private fun setPlayerCardMode(player: Player){
        var opcao: Int = 0
        var opcaoI: Int = 0

        println("CARTAS MONSTRO EM JOGO DO JOGADOR ${player.getOption()}: ")
        for((index, card) in player.getCardsInGame().withIndex()){
            if(card.getCategory() == "monstro"){
                print("$index - ${card.getName()}\t")
                val c: Card = player.getOneCard(index)
                println("\nSELECIONE O MODO: ")
                print("1 -> ATAQUE - 2 -> DEFESA: ")
                opcao = Tools.entry()
                when(opcao){
                    1 -> c.setModeAtack(true)
                    2 -> c.setModeAtack(false)
                }
                player.backCardToDeck(c, index)
            }
        }
        println()
    }

    /*
       finaliza o jogo, caso a pontuação chegue a zero
       ou se faltar cartas no baralho, o que tiver mais pontos será considerado
       o vencedor do jogo.
     */
    private fun finish(){
        if(this.playerOne.getPunctuation() <= 0){
            println("PLAYER2: ")
            println(Draw.winText())
            println(Draw.createPlayerOne())
            exitProcess(0)
        }else if(this.playerTwo.getPunctuation() <= 0) {
            println("PLAYER1: ")
            println(Draw.winText())
            println(Draw.createPlayerTwo())
            exitProcess(0)
        }
        if(this.deck.getCards().isEmpty()){
            if(this.playerOne.getPunctuation() > this.playerTwo.getPunctuation()){
                println("PLAYER1: ")
                println(Draw.winText())
                println(Draw.createPlayerOne())
                exitProcess(0)
            }else{
                println("PLAYER2: ")
                println(Draw.winText())
                println( Draw.createPlayerTwo())
                exitProcess(0)
            }
        }
    }

}