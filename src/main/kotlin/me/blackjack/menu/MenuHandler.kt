package me.blackjack.menu

import java.lang.RuntimeException

class MenuHandler(home: Menu) {

    private val stack = mutableListOf<Menu>(home)

    private val current
        get() = stack.last()

    private val canPop
        get() = stack.size > 1

    init {
        redraw()
        while (true) {
            try {
                handleInput(readln())
            } catch (_: RuntimeException) {}
        }
    }

    private fun redraw() = redraw(null)

    private fun redraw(message: String? = null) {
        printEscapeCode("2J") // clear terminal
        printEscapeCode("H") // move cursor to top left

        val content = current.getContent()
        val actions = current.getActions()

        val width = System.getenv("COLUMNS")?.toIntOrNull() ?: 80
        val height = System.getenv("LINES")?.toIntOrNull() ?: 24

        val usableHeight = height - actions.size - 2 - if (message?.isEmpty() == true) 0 else 3

        if (content.size > usableHeight) throw IllegalStateException("Content too large for terminal ($usableHeight lines)")
        if (content.any { it.length > width }) throw IllegalStateException("Content too large for terminal ($width characters)")

        val topEmptyLines = (usableHeight - content.size) / 2
        val bottomEmptyLines = usableHeight - content.size - topEmptyLines

        repeat(topEmptyLines) { println() }
        content.forEach { printCentered(it, width) }
        repeat(bottomEmptyLines) { println() }

        println()
        actions.forEach { (key, value) -> println("$key: $value") }
        println()

        if (message?.isNotEmpty() == true) {
            println()
            printCentered(message, width)
            println()
        }

        print("> ")
    }

    private fun printCentered(line: String, width: Int) {
        val padding = (width - line.length) / 2
        if (line.length % 2 == 0) print(" ".repeat(padding))
        else print(" ".repeat(padding + 1))
        print(line)
        print(" ".repeat(padding))
    }

    private fun printEscapeCode(code: String) = print("\u001B[$code")

    private fun handleInput(input: String) {
        when (val reaction = current.input(input)) {
            RedrawReaction -> redraw()
            is MessageReaction -> redraw(reaction.message)
            is MoveReaction -> {
                if (reaction.type == MoveReaction.MoveType.HOME) {
                    while (stack.size > 1) stack.removeLast()
                    return
                }

                if (reaction.type == MoveReaction.MoveType.POP_AND_PUSH || reaction.type == MoveReaction.MoveType.POP) {
                    if (canPop) stack.removeLast()
                    else throw IllegalStateException("Cannot pop menu")
                }

                if (reaction.type == MoveReaction.MoveType.PUSH || reaction.type == MoveReaction.MoveType.POP_AND_PUSH)
                    stack.add(reaction.menu ?: throw IllegalStateException("Cannot push null menu"))
            }
            EmptyReaction -> {}
        }
    }

}