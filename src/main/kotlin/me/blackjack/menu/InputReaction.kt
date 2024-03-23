package me.blackjack.menu

sealed interface InputReaction

class MessageReaction(val message: String) : InputReaction

class MoveReaction(val type: MoveType = MoveType.PUSH, val menu: Menu? = null) : InputReaction {

    enum class MoveType {
        PUSH,
        POP_AND_PUSH,
        POP,
        HOME,
    }

}

data object RedrawReaction : InputReaction

data object EmptyReaction : InputReaction