package Restructured.cards.io;

import java.util.List;

import Restructured.cards.engine.GameState;
import Restructured.cards.engine.Move;

public interface UI<S extends GameState, M extends Move> {

    void render(S state);

    M promptForMove(List<M> legalMoves);
}
