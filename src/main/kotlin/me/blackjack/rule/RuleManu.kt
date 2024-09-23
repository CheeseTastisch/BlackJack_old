package me.blackjack.rule

import com.github.ajalt.mordant.terminal.ConversionResult
import me.blackjack.terminal.*

object RuleManu {

    fun start() {
        while (true) {
            terminal.printNice(buildList {
                add("Settings")
                add("")
                add("Which rule do you want to change?")
                add("")

                Rules.values.forEachIndexed { index, it ->
                    add("${index + 1}: ${it.name} ${me.blackjack.terminal.rgb("#aaaaaa")("Current value: ${it.value}")}")
                }

                add("")
                add("q: Quit")

            })

            val input = readln()
            if (input == "q" || input == "quit" || input == "exit") return

            val index = input.toIntOrNull()?.minus(1)
            if (index == null || index !in Rules.values.indices) {
                terminal.printNice(listOf("Enter a valid option.", "", "Press enter to continue."))
                readln()
                continue
            }

            Rules.values[index].start()
        }
    }


}