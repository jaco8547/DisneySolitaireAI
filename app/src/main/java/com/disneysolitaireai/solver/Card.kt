package com.disneysolitaireai.solver

enum class Suit { HEARTS, DIAMONDS, CLUBS, SPADES }
enum class Color { RED, BLACK }

data class Card(
    val rank: Int, // Ace=1 ... King=13
    val suit: Suit,
    val faceUp: Boolean
) {
    init { require(rank in 1..13) }
    val color: Color
        get() = if (suit == Suit.HEARTS || suit == Suit.DIAMONDS) Color.RED else Color.BLACK
}
