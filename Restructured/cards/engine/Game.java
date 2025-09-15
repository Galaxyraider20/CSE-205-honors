package Restructured.cards.engine;
import java.util.*;
public interface Game<S extends GameState, M extends Move> {
    S getGameState();

    int currentSeat();

    List<M> getLegalMoves();

    void apply(M move);

    boolean isTerminal();

}
