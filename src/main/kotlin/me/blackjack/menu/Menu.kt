package me.blackjack.menu

import com.github.ajalt.mordant.terminal.Terminal

abstract class Menu {

    abstract fun getContent(): List<String>

    open fun getActions(): Map<String, String> = emptyMap()

    abstract fun input(input: String): InputReaction

}