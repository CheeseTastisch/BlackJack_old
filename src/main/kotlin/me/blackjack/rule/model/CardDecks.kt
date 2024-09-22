package me.blackjack.rule.model

enum class CardDecks(val value: Int, private val string: String) {

    ONE(1, "One deck"),
    TWO(2, "Two decks"),
    FOUR(4, "Four decks"),
    SIX(6, "Six decks"),
    EIGHT(8, "Eight decks");

    override fun toString() = string

}