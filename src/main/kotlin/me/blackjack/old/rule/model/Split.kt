package me.blackjack.old.rule.model

enum class Split(val string: String) {

    NO("No Split"),
    ONE("One Split"),
    TWO("Two Split"),
    ANY("Any Split");

    override fun toString() = string
}