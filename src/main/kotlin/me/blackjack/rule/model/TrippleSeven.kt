package me.blackjack.rule.model

import kotlin.Double

enum class TrippleSeven(val value: Double, private val string: String) {

    LIKE_BLACKJACK(0.0, "Like Blackjack"),
    NO_TRIPPLE_SEVEN(0.0, "No Tripple Seven"),
    ONE_TO_ONE(1.0, "1:1"),
    FIVE_TO_SIX(1.2, "5:6"),
    THREE_TO_TWO(1.5, "3:2"),
    DOUBLE(2.0, "2:1");

    override fun toString() = string

}