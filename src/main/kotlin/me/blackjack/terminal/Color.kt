package me.blackjack.terminal

import com.github.ajalt.mordant.rendering.AnsiLevel
import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyle

fun rgb(hex: String, level: AnsiLevel = AnsiLevel.TRUECOLOR) = TextColors.rgb(hex, level)

val finished = TextStyle().finish()
fun TextStyle.finish() = this on me.blackjack.terminal.rgb("333333")