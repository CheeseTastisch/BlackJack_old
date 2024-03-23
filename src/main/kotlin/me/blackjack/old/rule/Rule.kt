package me.blackjack.old.rule

import com.github.ajalt.mordant.terminal.ConversionResult
import me.blackjack.old.terminal.clear
import me.blackjack.old.terminal.emptyLine
import me.blackjack.old.terminal.printNice
import me.blackjack.old.terminal.terminal
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class Rule<T : Enum<T>>(val name: String, default: T, private val values: List<T>): ReadOnlyProperty<Any?, T> {

    var value: T = default

    fun start() {
        while (true) {
            terminal.printNice(buildList {
                add("Settings")
                add("")
                add(name)
                add("")
                add("Select an option:")
                add("")
                values.forEachIndexed { index, t -> add("${index + 1}: $t") }
                add("")
                add("q: Quit")
            })

            val input = readln()
            if (input == "q" || input == "quit" || input == "exit") return

            val index = input.toIntOrNull()?.minus(1)
            if (index == null || index !in values.indices) {
                terminal.printNice(listOf("Enter a valid option.", "", "Press enter to continue."))
                readln()
                continue
            }

            value = values[index]
            return
        }
    }

    companion object {

        inline fun <reified T : Enum<T>> create(name: String, default: T) =
            Rule(name, default, enumValues<T>().toList())

    }

    override fun getValue(thisRef: Any?, property: KProperty<*>) = value

}