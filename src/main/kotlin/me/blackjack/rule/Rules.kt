package me.blackjack.rule

import me.blackjack.rule.model.*
import me.blackjack.rule.model.Double

object Rules {

    private val _cardDecks = Rule.create("Decks", CardDecks.SIX)
    private val _shufflePercentage = Rule.create("Shuffle Percentage", ShufflePercentage.SIXTY)
    private val _startCapital = Rule.create("Start capital", me.blackjack.rule.model.StartCapital.THOUSAND)
    private val _payoutRate = Rule.create("Blackjack Payout-Rate", PayoutRate.THREE_TO_TWO)
    private val _trippleSeven = Rule.create("Tripple-7-Charlie", TrippleSeven.LIKE_BLACKJACK)
    private val _push22 = Rule.create("Push-22 Payout-Rate", PayoutRate.ONE_TO_ONE)
    private val _soft17 = Rule.create("Dealer hit on 17", Soft17.STAY)
    private val _split = Rule.create("Split", me.blackjack.rule.model.Split.TWO)
    private val _aceSplit = Rule.create("Ace Split", me.blackjack.rule.model.AceSplit.ONE_CARD)
    private val _double = Rule.create("Double", Double.ALL)
    private val _doubleAfterSplit = Rule.create("Double after split", BooleanValue.ALLOW)
    private val _surrender = Rule.create("Surrender", me.blackjack.rule.model.Surrender.ANY_NO_ACE)
    private val _surrenderAfterSplit = Rule.create("Surrender after split", BooleanValue.ALLOW)

    val cardDecks by _cardDecks
    val shufflePercentage by _shufflePercentage
    val startCapital by _startCapital
    val payoutRate by _payoutRate
    val trippleSeven by _trippleSeven
    val push22 by _push22
    val soft17 by _soft17
    val split by _split
    val aceSplit by _aceSplit
    val double by _double
    val doubleAfterSplit by _doubleAfterSplit
    val surrender by _surrender
    val surrenderAfterSplit by _surrenderAfterSplit

    val values = listOf(
        _cardDecks,
        _shufflePercentage,
        _startCapital,
        _payoutRate,
        _trippleSeven,
        _push22,
        _soft17,
        _split,
        _aceSplit,
        _double,
        _doubleAfterSplit,
        _surrender,
        _surrenderAfterSplit
    )

}