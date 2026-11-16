package com.example.disneysolitaireai.solitaire

enum class Suit { CLUBS, DIAMONDS, HEARTS, SPADES }
data class Card(val rank: Int, val suit: Suit, val faceUp: Boolean = true) {
    init { require(rank in 1..13) }
    val isRed get() = suit == Suit.DIAMONDS || suit == Suit.HEARTS
    override fun toString() = "${listOf("?", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")[rank]}-${suit.name}"
}
data class GameState(
    val tableau: List<List<Card>>,
    val foundations: Map<Suit, Int> = Suit.values().associateWith { 0 },
    val stock: List<Card> = emptyList(),
    val waste: List<Card> = emptyList()
)
sealed class Move {
    data class TableauToTableau(val from: Int, val to: Int) : Move()
    data class TableauToFoundation(val column: Int) : Move()
    data class WasteToTableau(val column: Int) : Move()
    object WasteToFoundation : Move()
    object DrawStock : Move()
    data class Reveal(val column: Int) : Move()
}
