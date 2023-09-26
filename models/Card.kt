package br.com.ifmg.battlecardgame.models

/*
* Card: Descrição das cartas
* 0 -> Nome
* 1 -> Descrição
* 2 -> Ataque
* 3 -> Defesa
* 4 -> Tipo
* 5 -> Modo (ataque/defesa)
* */
data class Card (
    private var name: String,
    private var description: String,
    private var atack: Int,
    private var defense: Int,
    private var category: String,
){
    private var modeAtack: Boolean = false

    public fun getName(): String{
        return this.name
    }

    public fun getDescription(): String{
        return this.description
    }

    public fun getAtack(): Int{
        return this.atack
    }

    public fun getDefense(): Int{
        return this.defense
    }

    public fun getCategory(): String{
        return this.category
    }

    public fun getModeAtack(): Boolean{
        return this.modeAtack
    }

    public fun setModeAtack(mode: Boolean){
        this.modeAtack = mode
    }
}