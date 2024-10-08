package me.blackjack.game

import me.blackjack.rule.Rules

object Deck {

    private val cards = mutableListOf<me.blackjack.game.Card>()

    val needsShuffle: Boolean
        get() = cards.size < Rules.cardDecks.value * 52 * Rules.shufflePercentage.value / 100

    fun shuffle() {
        cards.clear()
        repeat(Rules.cardDecks.value) { cards.addAll(me.blackjack.game.deck) }
        cards.shuffle()
    }

    fun draw() = cards.removeAt(0)

}