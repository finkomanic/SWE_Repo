package application.model.usecases;

import application.model.game.Game;
import application.model.game.State;

public class UseCaseController {

	private Game game;

	public UseCaseController(Game game) {
		this.game = game;
	}

	/**
	 * Set amount of participating players.
	 * @param i Amount of players
	 * @return State of game
	 */
	public State setPlayers(int i) {
		game.setPlayers(i);
		return game.getState();
	}

	/**
	 * Rolls the dice.
	 * @return State of game
	 */
	public State rollDice() {
		game.rollDice();
		return game.getState();
	}

	/**
	 * Choose a figure to move.
	 * @param i Figure-ID
	 * @return State of game
	 */
	public State chooseFigure(int i) {
		game.moveFigure(i);
		return game.getState();
	}

	/**
	 * Choose category to ask a question from.
	 * @param i Category-ID
	 * @return State of game
	 */
	public State chooseCategory(int i) {
		game.chooseCategory(i);
		return game.getState();
	}

	/**
	 * Choose answer for given question.
	 * @param i Answer-ID
	 * @return State of game
	 */
	public State chooseAnswer(int i) {
		game.chooseAnswer(i);
		return game.getState();
	}
	
	/**
	 * Choose other category to add points to.
	 * @param i Category-ID
	 * @return State of game
	 */
	public State chooseOtherCategory(int i) {
		game.chooseOtherCategory(i);
		return game.getState();
	}
}
