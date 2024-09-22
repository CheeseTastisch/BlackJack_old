package me.blackjack.rule.model

enum class ShufflePercentage(val value: Int, private val string: String) {

    THIRTY(30, "30%"),
    FORTY(40, "40%"),
    FIFTY(50, "50%"),
    SIXTY(60, "60%"),
    SEVENTY(70, "70%"),
    EIGHTY(80, "80%");

    override fun toString() = string

}