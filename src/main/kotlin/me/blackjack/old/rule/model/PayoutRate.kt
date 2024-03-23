package me.blackjack.old.rule.model

import kotlin.Double

enum class PayoutRate(val value: Double, private val string: String) {

    ONE_TO_ONE(1.0, "1:1"),
    FIVE_TO_SIX(1.2, "5:6"),
    THREE_TO_TWO(1.5, "3:2"),
    DOUBLE(2.0, "2:1");

    override fun toString() = string

}