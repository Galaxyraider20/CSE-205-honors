package Restructured.cards.engine;
import java.util.List;

import Restructured.cards.io.UI;

public class GameController<S extends GameState, M extends Move> {
    
    private final Game<S,M> game;
    private final UI<S,M> ui;

    public GameController(Game<S, M> game, UI<S, M> ui) {
        this.game = game;
        this.ui = ui;
    }

    public void run()
    {
        while(!game.isTerminal())
        {
            S currentState = game.getGameState();

            ui.render(currentState);

            List<M> legalMoves = game.getLegalMoves();

            M chosenMove = ui.promptForMove(legalMoves);

            game.apply(chosenMove);
        }

        System.out.println("Game Over!");
        ui.render(game.getGameState());
    }
}


