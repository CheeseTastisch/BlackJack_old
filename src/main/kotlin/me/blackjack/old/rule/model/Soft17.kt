package me.blackjack.old.rule.model

enum class Soft17(val hit: Boolean, private val string: String) {

    STAY(false, "Stay"),
    HIT(true, "Hit");

    override fun toString() = string

}