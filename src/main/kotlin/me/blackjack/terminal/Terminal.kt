package me.blackjack.terminal

import com.github.ajalt.mordant.terminal.Terminal

val terminal by lazy { Terminal() }

fun Terminal.emptyLine() = println(me.blackjack.terminal.finished(" ".repeat(info.width)))

fun Terminal.clear() = cursor.move {
    setPosition(0, 0)
    clearScreen()
}

fun Terminal.printNice(text: String) = println(me.blackjack.terminal.finished(text.centerForTerminal(this)))

fun Terminal.printNice(text: List<String>) {
    clear()

    val topEmptyLines = (info.height - text.size) / 2
    val bottomEmptyLines = info.height - text.size - topEmptyLines

    repeat(topEmptyLines) { emptyLine() }
    text.forEach { printNice(it) }
    repeat(bottomEmptyLines) { emptyLine() }
}

private fun String.centerForTerminal(terminal: Terminal): String {
    val ansiEscapeCodes = Regex("\u001B\\[[;\\d]*m")
    val length = ansiEscapeCodes.replace(this, "").length
    val padding = (terminal.info.width - length) / 2
    return " ".repeat(padding + if (length%2 == 0) 0 else 1) + this + " ".repeat(padding)
}