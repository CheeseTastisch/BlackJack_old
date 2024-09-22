package me.blackjack.rule.model

enum class AceSplit(val string: String) {

    ALLOW("Allow"),
    ONE_CARD("Only one card after split"),
    NO_SPLIT("No Split");

    override fun toString() = string

}