package com.disneysolitaireai.solver

data class GameState(
    val tableau: List<List<Card>>,
    val foundations: Map<Suit, Int>,
    val stock: List<Card>,
    val waste: List<Card>
) {
    fun isWon(): Boolean = Suit.entries.all { (foundations[it] ?: 0) == 13 }
}
