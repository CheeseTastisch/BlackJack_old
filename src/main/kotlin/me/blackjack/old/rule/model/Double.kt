package me.blackjack.old.rule.model

enum class Double(private val string: String) {

    NO("No Double"),
    SPECIFIC("Only 9/10/11"),
    ALL("All");

    override fun toString() = string

}