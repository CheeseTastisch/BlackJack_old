package me.blackjack.rule.model

enum class Surrender(private val string: String) {

    NO("No Surrender"),
    EARLY("Early Surrender"),
    EARLY_NO_ACE("Early Surrender (Not Ace)"),
    LATE("Late Surrender"),
    ANY_NO_ACE("Any Surrender (Not Ace)"),
    ANY("Any Surrender");

    override fun toString() = string
}