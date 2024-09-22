package me.blackjack.game

import com.github.ajalt.colormath.model.RGB
import com.github.ajalt.mordant.rendering.TextStyle
import me.blackjack.old.terminal.*

class Game {

    private lateinit var dealerHand: DealerHand
    private val playersHands = mutableListOf<Hand>()

    fun setup() {
        me.blackjack.old.game.Bank.reset()
        Deck.shuffle()

        terminal.printNice(listOf("Welcome to Blackjack!", "", "Shuffling deck...", "", "Press enter to start."))
        readln()

        while (true) {
            if (Deck.needsShuffle) {
                Deck.shuffle()
                terminal.printNice(listOf("Shuffling deck...", "", "Press enter to continue."))
                readln()
            }

            if (me.blackjack.old.game.Bank.capital == 0) {
                terminal.printNice(listOf("You have no money left.", "", "Press enter to quit."))
                readln()
                return
            }

            if (me.blackjack.old.game.Bank.capital < me.blackjack.old.game.Bank.stake) me.blackjack.old.game.Bank.stake = me.blackjack.old.game.Bank.capital

            terminal.printNice(
                listOf(
                    "Your current balance is ${me.blackjack.old.game.Bank.capitalString}.",
                    "Current bet is ${me.blackjack.old.game.Bank.stakeString}.",
                    "",
                    "Enter a new bet, press enter to start the game or enter 'q' to quit."
                )
            )
            val result = readln()
            if (result == "q" || result == "quit" || result == "exit") return

            val newBet = result.toIntOrNull()
            if (newBet != null) {
                if (newBet > me.blackjack.old.game.Bank.availableCapital) {
                    terminal.printNice(listOf("You don't have enough money.", "", "Press enter to continue."))
                    readln()
                    continue
                }

                me.blackjack.old.game.Bank.stake = newBet
            } else start()
        }
    }

    private fun start() {
        me.blackjack.old.game.Bank.onHold += me.blackjack.old.game.Bank.stake

        playersHands.clear()

        dealerHand = DealerHand()
        playersHands.add(Hand())

        playersHands.forEach { it.hit() }
        dealerHand.hit()
        playersHands.forEach { it.hit() }
        dealerHand.hit()

        player()
        dealer()
        finishGame()
    }

    private fun player() {
        var currentHand = 0
        while (currentHand < playersHands.size) {
            val hand = playersHands[currentHand]

            if (hand.cards.size == 1) hand.hit()

            if (!hand.canHit) {
                printHands(
                    current = currentHand,
                    handInformation = "Hand ${currentHand + 1} is finished.",
                    generalInformation = "Press enter to continue."
                )
                readln()

                currentHand++
                continue
            }

            val actions = mutableListOf(PlayerAction.HIT, PlayerAction.STAND)

            if (hand.canDouble && me.blackjack.old.game.Bank.availableCapital >= me.blackjack.old.game.Bank.stake) actions.add(PlayerAction.DOUBLE)
            if (hand.canSplit && me.blackjack.old.game.Bank.availableCapital >= me.blackjack.old.game.Bank.stake) actions.add(PlayerAction.SPLIT)
            if (hand.canSurrender(dealerHand)) actions.add(PlayerAction.SURRENDER)


            printHands(current = currentHand, generalInformation = buildString {
                actions.forEachIndexed { index, action ->
                    if (index != 0) append(", ")
                    append("${action.letter}...${action.string}")
                }
            })

            var action = PlayerAction.valueOf(readln().getOrNull(0) ?: 'x')
            while (action == null || action !in actions) {
                printHands(current = currentHand, generalInformation = "Invalid action. Choose again.")
                action = PlayerAction.valueOf(readln().getOrNull(0) ?: 'x')
            }

            when (action) {
                PlayerAction.HIT -> hand.hit()
                PlayerAction.STAND -> currentHand++
                PlayerAction.DOUBLE -> {
                    hand.hit()
                    hand.doubled = true
                    me.blackjack.old.game.Bank.onHold += me.blackjack.old.game.Bank.stake
                }

                PlayerAction.SPLIT -> {
                    val (first, second) = hand.split()
                    playersHands[currentHand] = first
                    playersHands.add(currentHand + 1, second)
                    me.blackjack.old.game.Bank.onHold += me.blackjack.old.game.Bank.stake
                }

                PlayerAction.SURRENDER -> hand.surrendered = true
            }
        }
    }

    private fun dealer() {
        dealerHand.secondCardShown = true

        if (playersHands.all { it.surrendered || it.isBust || it.isBlackjack || it.isTrippleSeven }) {
            printHands(
                dealer = true,
                dealerInformation = "Dealer has nothing to do.",
                generalInformation = "Press enter to continue."
            )
            readln()
            return
        }

        while (dealerHand.mustHit) {
            if (playersHands.all { it.surrendered || it.isBust || it.isBlackjack || it.isTrippleSeven }) {
                printHands(dealerInformation = "Dealer has nothing to do. Press enter to continue.", dealer = true)
                readln()
                return
            }

            printHands(
                dealer = true,
                dealerInformation = "Dealer must hit.",
                generalInformation =
                if (playersHands.any { it.canSurrender(dealerHand) }) "Enter hand numbers to surrender that hand (separated by spaces)."
                else "Press enter to continue."
            )
            readln()
                .split(' ')
                .mapNotNull { it.toIntOrNull() }
                .map { it - 1 }
                .filter { it in playersHands.indices }
                .forEach { playersHands[it].surrendered = true }

            if (playersHands.all { it.surrendered || it.isBust || it.isBlackjack || it.isTrippleSeven }) {
                printHands(
                    dealer = true,
                    dealerInformation = "Dealer has nothing to do.",
                    generalInformation = "Press enter to continue."
                )
                readln()
                return
            }

            dealerHand.hit()
        }

        printHands(
            dealer = true,
            dealerInformation = if (dealerHand.isBust) "Dealer is bust." else "Dealer must stand.",
            generalInformation = "Press enter to continue."
        )
        readln()
    }

    private fun finishGame() {
        printHands(
            printWinLose = true,
            generalInformation = "Press enter to continue."
        )
        readln()
        me.blackjack.old.game.Bank.onHold = 0
    }

    private fun printHands(
        dealerInformation: String? = null,
        dealer: Boolean = false,
        current: Int? = null,
        handInformation: String? = null,
        generalInformation: String? = null,
        printWinLose: Boolean = false
    ) {
        terminal.printNice(buildList {
            add("Dealer")
            add(createHand(
                    dealerHand,
                    name = null,
                    current = dealer,
                    withWinLose = false,
                    onlyFirstCard = !dealerHand.secondCardShown
                ))
            add(me.blackjack.terminal.rgb("#ffffaa")(dealerInformation ?: ""))

            add("")
            add("")

            playersHands.forEachIndexed { index, hand ->
                add(createHand(
                    hand,
                    name = "Hand ${index + 1}",
                    current = current == index,
                    withWinLose = printWinLose,
                    onlyFirstCard = false
                ))
            }
            add(me.blackjack.terminal.rgb("#ffffaa")(handInformation ?: ""))

            add("")
            add(me.blackjack.terminal.rgb("#ffffaa")(generalInformation ?: ""))
        })
    }

    private fun createHand(
        hand: Hand,
        name: String?,
        current: Boolean,
        withWinLose: Boolean,
        onlyFirstCard: Boolean,
    ): String {
        val bg = TextStyle(
            bgColor = if (!onlyFirstCard && hand.isBust) RGB("#553333")
            else if (!onlyFirstCard && (hand.isBlackjack || hand.isTrippleSeven)) RGB("#335533")
            else if (!onlyFirstCard && current) RGB("#335555")
            else null
        )

        val textStyle = TextStyle(
            bold = !onlyFirstCard && (hand.isBlackjack || hand.isTrippleSeven),
            italic = !onlyFirstCard && hand.doubled,
            strikethrough = !onlyFirstCard && (hand.surrendered || hand.isBust)
        )

        val content = buildString {
            if (name != null) append("$name: ")

            if (onlyFirstCard) {
                append(hand.cards[0].first.color(hand.cards[0].asString()))
                repeat(hand.cards.size - 1) { append(" ??") }
            } else {
                hand.cards.forEach { append("${it.first.color(it.asString())} ") }
                append("(${hand.value}")
                if (hand.isBlackjack) append(", Blackjack")
                if (hand.isTrippleSeven) append(", Tripple Seven")
                if (hand.isBust) append(", Bust")
                if (hand.doubled) append(", Doubled")
                if (hand.surrendered) append(", Surrendered")

                append(")")
            }
        }

        val winLoseContent = buildString {
            if (withWinLose) {
                val payout = hand.getPayout(dealerHand)
                val string = me.blackjack.old.game.Bank.gameOver(payout)
                when {
                    payout > 0 -> append(me.blackjack.terminal.rgb("#aaffaa")(" Won $string"))
                    payout < 0 -> append(me.blackjack.terminal.rgb("#ffaaaaaa")(" Lost $string"))
                    else -> append(me.blackjack.terminal.rgb("#ffffaa")(" Push"))
                }
            }
        }

        return bg(" ${textStyle(content) + winLoseContent} ")
    }

    enum class PlayerAction(val letter: Char, val string: String) {

        HIT('h', "Hit"),
        STAND('s', "Stand"),
        DOUBLE('d', "Double"),
        SPLIT('p', "Split"),
        SURRENDER('r', "Surrender");

        companion object {

            fun valueOf(letter: Char) = entries.firstOrNull { it.letter == letter.lowercaseChar() }

        }

    }

}