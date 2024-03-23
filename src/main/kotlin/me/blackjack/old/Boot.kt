package me.blackjack.old

import me.blackjack.old.menu.MainMenu
import me.blackjack.old.terminal.clear
import me.blackjack.old.terminal.emptyLine
import me.blackjack.old.terminal.printNice
import me.blackjack.old.terminal.terminal
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