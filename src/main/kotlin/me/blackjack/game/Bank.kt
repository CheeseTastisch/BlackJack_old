package me.blackjack.game

import me.blackjack.rule.Rules
import java.text.NumberFormat

object Bank {

    var capital: Int = 0
        private set

    val capitalString: String
        get() = NumberFormat.getInstance().format(capital)

    val availableCapital: Int
        get() = capital - onHold

    var stake: Int = 50

    val stakeString: String
        get() = NumberFormat.getInstance().format(stake)

    var onHold: Int = 0

    fun reset() {
        capital = Rules.startCapital.value
        stake = 50
    }

    fun gameOver(rate: Double): String {
        val change = (stake * rate).toInt()
        capital += change

        return NumberFormat.getInstance().format(if (change < 0) change * -1 else change)
    }
}