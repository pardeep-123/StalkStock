package com.stalkstock.driver.models

class CardModel {
    var cardImage: Int? = null
    var cardName: String? = null
    var cardSelect = false

    constructor()

    constructor(cardImage: Int, cardName: String, cardSelect: Boolean){
        this.cardImage = cardImage
        this.cardName = cardName
        this.cardSelect = cardSelect
    }
}