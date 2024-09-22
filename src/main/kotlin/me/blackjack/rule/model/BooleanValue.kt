package me.blackjack.rule.model

enum class BooleanValue(val value: Boolean, private val string: String) {

    ALLOW(true, "Allow"),
    DISALLOW(false, "Disallow");

    override fun toString() = string

}