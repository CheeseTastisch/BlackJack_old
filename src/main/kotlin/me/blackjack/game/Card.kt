package me.blackjack.game

import com.github.ajalt.mordant.rendering.TextStyle
import me.blackjack.old.terminal.*

enum class CardSuit(val symbol: String, val color: TextStyle) {
    SPADES("♠", me.blackjack.terminal.rgb("AAAAAA")),
    HEARTS("♥", me.blackjack.terminal.rgb("FF3333")),
    DIAMONDS("♦", me.blackjack.terminal.rgb("FF3333")),
    CLUBS("♣", me.blackjack.terminal.rgb("AAAAAA"));
}

enum class CardRank(val symbol: String, val value: Int) {
    ACE("A", 11),
    KING("K", 10),
    QUEEN("Q", 10),
    JACK("J", 10),
    TEN("T", 10),
    NINE("9", 9),
    EIGHT("8", 8),
    SEVEN("7", 7),
    SIX("6", 6),
    FIVE("5", 5),
    FOUR("4", 4),
    THREE("3", 3),
    TWO("2", 2);
}

typealias Card = Pair<me.blackjack.old.game.CardSuit, me.blackjack.old.game.CardRank>

fun me.blackjack.old.game.Card.asString() = "${first.symbol}${second.symbol}"

val deck = me.blackjack.old.game.CardSuit.entries.flatMap { suit ->
    me.blackjack.old.game.CardRank.entries.map { rank -> me.blackjack.old.game.Card(suit, rank) }
}