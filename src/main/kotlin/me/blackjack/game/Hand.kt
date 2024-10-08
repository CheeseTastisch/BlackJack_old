package me.blackjack.game

import kotlin.Double as KDouble
import me.blackjack.rule.Rules
import me.blackjack.rule.model.*

open class Hand(private val _cards: MutableList<me.blackjack.game.Card> = mutableListOf(), private val splits: Int = 0) {

    val cards: List<me.blackjack.game.Card> = _cards

    var doubled: Boolean = false
    var surrendered: Boolean = false

    val value: Int
        get() {
            var value = 0
            var aces = 0
           _cards.forEach {
                if (it.second == me.blackjack.game.CardRank.ACE) aces++
                value += it.second.value
            }

            repeat(aces) {
                if (value > 21) value -= 10
            }

            return value
        }

    val isBlackjack
        get() = splits == 0 && cards.size == 2 && value == 21

    val isTrippleSeven: Boolean
        get() = splits == 0 && cards.size == 3 && cards.all { it.second == me.blackjack.game.CardRank.SEVEN }

    val isBust
        get() = value > 21

    val canSplit: Boolean
        get() {
            if (_cards.size != 2) return false
            if (_cards[0].second.value != _cards[1].second.value) return false

            return when (Rules.split) {
                me.blackjack.rule.model.Split.NO -> false
                me.blackjack.rule.model.Split.ONE -> splits < 1
                me.blackjack.rule.model.Split.TWO -> splits < 2
                me.blackjack.rule.model.Split.ANY -> {
                    if (cards[0].second != me.blackjack.game.CardRank.ACE) return true

                    when (Rules.aceSplit) {
                        me.blackjack.rule.model.AceSplit.NO_SPLIT -> false
                        else -> true
                    }
                }
            }
        }

    val canDouble: Boolean
        get() {
            if (cards.size != 2) return false
            if (splits > 0 && !Rules.doubleAfterSplit.value) return false

            return when (Rules.double) {
                Double.NO -> false
                Double.SPECIFIC -> value == 9 || value == 10 || value == 11
                Double.ALL -> true
            }
        }

    val canHit: Boolean
        get() = !doubled && !surrendered && !isBust && value < 21

    private val doubleMultiplier
        get() = if (doubled) 2 else 1

    fun canSurrender(dealer: DealerHand): Boolean {
        if (splits > 0 && !Rules.surrenderAfterSplit.value) return false
        if (dealer.secondCardShown && dealer.value >= value) return false

        return when(Rules.surrender) {
            me.blackjack.rule.model.Surrender.NO -> false
            me.blackjack.rule.model.Surrender.EARLY_NO_ACE -> !dealer.secondCardShown && dealer.firstCard.second != me.blackjack.game.CardRank.ACE
            me.blackjack.rule.model.Surrender.EARLY -> !dealer.secondCardShown
            me.blackjack.rule.model.Surrender.LATE -> dealer.secondCardShown
            me.blackjack.rule.model.Surrender.ANY_NO_ACE -> dealer.firstCard.second != me.blackjack.game.CardRank.ACE
            me.blackjack.rule.model.Surrender.ANY -> true
        }
    }

    fun split(): Pair<Hand, Hand> {
        if (!canSplit) throw IllegalStateException("Cannot split hand")

        return Hand(mutableListOf(cards[0]), splits + 1) to Hand(mutableListOf(cards[1]), splits + 1)
    }

    fun hit() {
        if (!canHit) throw IllegalStateException("Cannot hit hand")

        _cards.add(Deck.draw())
    }

    fun getPayout(dealer: DealerHand): KDouble {
        if (surrendered) return -0.5 * doubleMultiplier

        if (isBust) return -1.0 * doubleMultiplier

        if (isBlackjack) {
            return if (dealer.isBlackjack) 0.0
            else Rules.payoutRate.value * doubleMultiplier
        }

        if (Rules.trippleSeven != TrippleSeven.NO_TRIPPLE_SEVEN && isTrippleSeven) {
            return if (dealer.isTrippleSeven) 0.0
            else when (Rules.trippleSeven) {
                TrippleSeven.LIKE_BLACKJACK -> Rules.payoutRate.value
                else -> Rules.trippleSeven.value
            } * doubleMultiplier
        }

        if (dealer.isBust) return Rules.push22.value * doubleMultiplier

        if (dealer.value > value) return -1.0 * doubleMultiplier
        if (value == dealer.value) return 0.0

        return 1.0 * doubleMultiplier
    }

}

class DealerHand : Hand() {

    val firstCard
        get() = cards[0]

    var secondCardShown: Boolean = false

    val mustHit: Boolean
        get() = value < 17 || (value == 17 && Rules.soft17 == Soft17.HIT)

}