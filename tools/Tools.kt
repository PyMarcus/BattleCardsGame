package br.com.ifmg.battlecardgame.tools

import java.io.File
import java.io.InputStream
import br.com.ifmg.battlecardgame.models.Card


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
        public fun entry(): Int{
            var entry: Int = 0
            print(": ")
            while(entry != 1 || entry != 2){
                println("Please, choice 1 or 2!")
                try{
                    entry = readln().toInt()
                }catch (e: Exception){
                    continue
                }
            }
            return entry
        }

    }
}