package br.com.ifmg.battlecardgame.tools

import java.io.File
import java.io.InputStream
import br.com.ifmg.battlecardgame.models.Card
import java.util.Scanner


/*
* Cartas podem:
* 1) posicionar um monstro no tab
* 2) equipar o monstro
* 3) Ser descartada
* 4) Realizar ataque
* 5) Realizar defesa
*
**/
class Tools {
    companion object{
        private lateinit var cards: MutableList<Map<String, Card>> // category, Card

        // getCards alimenta a lista com as cartas para serem utilizadas pelos jogadores.
        public fun getCards() : MutableList<Map<String, Card>>{ // List<Map<String, Card>>
            var name: String = ""
            var description: String = ""
            var atack: Int = 0
            var defense: Int = 0
            var category: String = ""

            if(!::cards.isInitialized){
                var cardList = readCSVCards()
                // cada linha é separada por ,
                cardList.forEach{item -> item.split(",")}
                cards = mutableListOf()

                for(text in cardList){
                    val content = text.split(";")
                    name = content[0]
                    description = content[1]
                    atack = content[2].toInt()
                    defense = content[3].toInt()
                    category = content[4]
                    val card = mutableMapOf<String, Card>()
                    card[category] = Card(name, description, atack, defense, category)
                    cards.add(card)
                }
            }
            return this.cards
        }

        private fun readCSVCards(): List<String>{
            var basePath: String? = System.getProperty("user.dir")
            val streamDados:InputStream = File(basePath + "/app/src/main/java/br/com/ifmg/battlecardgame/resources/cartas.csv").inputStream()
            val leitorStream = streamDados.bufferedReader()
            return leitorStream.lineSequence()
                .filter { it.isNotBlank() }.toList()
        }

        // trata entrada da escolha de jogador
        fun entry(): Int {
            val scanner = Scanner(System.`in`)
            var input = 0
            println()
            while (true) {
                print("Escolha -> ")
                try {
                    input = scanner.nextInt()

                    if (input == 1 || input == 2) {
                        println("OK")
                        break
                    } else {
                        println("Invalid input. Please choose 1 or 2.")
                    }
                } catch (e: Exception) {
                    println("Invalid input. Please choose 1 or 2.")
                    scanner.nextLine() // Limpar o buffer de entrada após uma exceção
                }
            }

            return input
        }

        fun entryMode(): Int{
            val scanner = Scanner(System.`in`)
            var input = 0

            while (true) {
                print("Escolha a carta -> ")
                try {
                    input = scanner.nextInt()

                    if (input in 0..5) {  // so pode ter até 5 cartas em jogo, então.
                        println("OK")
                        break
                    } else {
                        println("Invalid input. Please choose 1 or 2.")
                    }
                } catch (e: Exception) {
                    println("Invalid input. Please choose 1 or 2.")
                    scanner.nextLine() // Limpar o buffer de entrada após uma exceção
                }
        }
            return input

        }
    }
}