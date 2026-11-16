package com.example.disneysolitaireai.solitaire

object KlondikeSolver {
    fun legalMoves(s: GameState): List<Move> {
        val out = mutableListOf<Move>()
        s.tableau.forEachIndexed { i, col ->
            val top = col.lastOrNull()
            if (top != null && top.faceUp) {
                if (s.foundations[top.suit] == top.rank - 1) out += Move.TableauToFoundation(i)
                s.tableau.forEachIndexed { j, dest ->
                    if (i != j && canPlace(top, dest.lastOrNull())) out += Move.TableauToTableau(i, j)
                }
            } else if (top != null && !top.faceUp) out += Move.Reveal(i)
        }
        val wasteTop = s.waste.lastOrNull()
        if (wasteTop != null) {
            if (s.foundations[wasteTop.suit] == wasteTop.rank - 1) out += Move.WasteToFoundation
            s.tableau.indices.forEach { if (canPlace(wasteTop, s.tableau[it].lastOrNull())) out += Move.WasteToTableau(it) }
        }
        if (s.stock.isNotEmpty()) out += Move.DrawStock
        return out.distinct()
    }
    private fun canPlace(card: Card, target: Card?): Boolean =
        if (target == null) card.rank == 13 else target.faceUp && target.rank == card.rank + 1 && target.isRed != card.isRed

    // Ranking only: actual state transitions and full stock-cycle search remain TODO.
    fun score(move: Move, state: GameState): Int = when (move) {
        is Move.Reveal -> 100
        is Move.TableauToFoundation, Move.WasteToFoundation -> 80
        is Move.TableauToTableau, is Move.WasteToTableau -> 40
        Move.DrawStock -> 10
    }
    fun chooseMove(state: GameState): Move? = legalMoves(state).maxByOrNull { score(it, state) }
}
