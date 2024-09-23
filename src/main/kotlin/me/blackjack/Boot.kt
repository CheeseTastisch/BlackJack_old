package me.blackjack

import me.blackjack.menu.MainMenu
import me.blackjack.terminal.clear
import me.blackjack.terminal.emptyLine
import me.blackjack.terminal.printNice
import me.blackjack.terminal.terminal
import java.lang.RuntimeException

fun main() {
    try {
        MainMenu.start()
    } catch (e: RuntimeException) {
        terminal.clear()
        terminal.emptyLine()
        terminal.printNice("Thanks for playing!")
        terminal.printNice("Goodbye!")
        terminal.emptyLine()
    }
}