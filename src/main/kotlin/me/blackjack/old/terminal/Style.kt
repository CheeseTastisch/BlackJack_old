package me.blackjack.old.terminal

import com.github.ajalt.mordant.rendering.TextStyles

val bold = TextStyles.bold
val dim = TextStyles.dim
val italic = TextStyles.italic
val underline = TextStyles.underline
val inverse = TextStyles.inverse
val strikethrough = TextStyles.strikethrough

val resetForeground = TextStyles.resetForeground
val resetBackground = TextStyles.resetBackground
val reset = TextStyles.reset

fun hyperlink(url: String) = TextStyles.hyperlink(url)