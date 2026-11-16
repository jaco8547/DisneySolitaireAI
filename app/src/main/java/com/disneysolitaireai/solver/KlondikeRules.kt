package com.disneysolitaireai.solver

/**
 * Rule predicates for standard Klondike.
 * This is a pure local rules layer; it does not yet infer moves from screenshots.
 */
object KlondikeRules {
    fun canPlaceOnTableau(moving: Card, destinationTop: Card?): Boolean {
        if (!moving.faceUp) return false
        if (destinationTop == null) return moving.rank == 13
        return destinationTop.faceUp &&
            destinationTop.color != moving.color &&
            destinationTop.rank == moving.rank + 1
    }

    fun canPlaceOnFoundation(card: Card, foundationTopRank: Int): Boolean {
        return card.faceUp && card.rank == foundationTopRank + 1
    }
}
