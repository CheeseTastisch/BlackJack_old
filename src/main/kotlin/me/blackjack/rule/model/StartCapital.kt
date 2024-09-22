package me.blackjack.rule.model

enum class StartCapital(val value: Int, private val string: String) {

    HUNDRED(100, "100"),
    TWO_HUNDRED(200, "200"),
    FIVE_HUNDRED(500, "500"),
    THOUSAND(1000, "1.000"),
    FIVE_THOUSAND(5000, "5.000"),
    TEN_THOUSAND(10000, "10.000");

    override fun toString() = string

}