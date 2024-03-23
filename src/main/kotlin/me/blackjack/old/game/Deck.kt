package me.blackjack.old.game

import me.blackjack.old.rule.Rules

object Deck {

    private val cards = mutableListOf<me.blackjack.old.game.Card>()

    val needsShuffle: Boolean
        get() = cards.size < Rules.cardDecks.value * 52 * Rules.shufflePercentage.value / 100

    fun shuffle() {
        cards.clear()
        repeat(Rules.cardDecks.value) { cards.addAll(me.blackjack.old.game.deck) }
        cards.shuffle()
    }

    fun draw() = cards.removeAt(0)

}