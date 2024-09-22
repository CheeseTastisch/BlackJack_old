package me.blackjack.menu

import com.github.ajalt.mordant.terminal.ConversionResult
import me.blackjack.old.game.Game
import me.blackjack.old.rule.RuleManu
import me.blackjack.old.terminal.*
import org.apache.commons.lang3.StringUtils

object MainMenu {

    fun start() {
        while (true) {
            terminal.printNice(listOf(
                "Blackjack!",
                "",
                "What do you want to do?",
                "p: Play",
                "s: Settings",
                "q: Quit"
            ))

            when (readln()) {
                "p", "play" -> {
                    val game = Game()
                    game.setup()
                }
                "s", "settings" -> RuleManu.start()
                "q", "quit", "exit" -> {
                    terminal.clear()
                    terminal.emptyLine()
                    terminal.printNice("Thanks for playing!")
                    terminal.printNice("Goodbye!")
                    terminal.emptyLine()
                    return
                }
                else -> {
                    terminal.printNice(listOf("Enter a valid option.", "Press enter to continue."))
                    readln()
                }
            }
        }
    }

}