package application.model.usecases;

import java.util.ArrayList;

import application.model.api.IModel;
import application.model.api.Subject;
import application.model.game.Game;
import application.model.game.Player;
import application.model.game.State;

/**
 * Provides System-operations.
 * 
 * @author Stefan
 *
 */
public class Fassade extends Subject implements IModel {

	private UseCaseController ucc;
	private Game game;

	public Fassade() {
		game = new Game();
		ucc = new UseCaseController(game);
	}
	
	/**
	 * Sets the amount of players
	 */
	public void setPlayers(int i) {
		if (game.getState() == State.SET_PLAYERS && i > 1 && i < 5) {
			notifyObservers(ucc.setPlayers(i));
		} else {
			notifyObservers(State.WRONG_INPUT);
		}
	}

	/**
	 * Rolls the dice
	 */
	public void rollDice() {
		if (game.getState() == State.FIRST_ROLL || game.getState() == State.FAILURE
				|| game.getState() == State.SUCCESS || game.getState() == State.MOVE
				|| game.getState() == State.RELEASE || game.getState() == State.CORRECT_ANSWER
				|| game.getState() == State.INCORRECT_ANSWER_CURRENT_PLAYER) {
			notifyObservers(ucc.rollDice());
		} else {
			notifyObservers(State.WRONG_INPUT);
		}
	}

	/**
	 * Choose figure to move
	 */
	public void chooseFigure(int i) {
		if (game.getState() == State.CHOOSE_FIGURE && i < game.getPositionsMove().size()) {
			notifyObservers(ucc.chooseFigure(i));
		} else {
			notifyObservers(State.WRONG_INPUT);
		}
	}

	/**
	 * Choose a category to ask questions out of
	 */
	public void chooseCategory(int i) {
		if ((game.getState() == State.MOVE_COLLISION || game.getState() == State.RELEASE_COLLISION 
				|| game.getState() == State.SUCCESS_COLLISION 
				|| game.getState() == State.INCORRECT_ANSWER_COLLIDER) && i < 4 && i > -1) {
			notifyObservers(ucc.chooseCategory(i));
		} else {
			notifyObservers(State.WRONG_INPUT);
		}
	}

	/**
	 * Choose the answer to a given question
	 */
	public void chooseAnswer(int i) {
		if (game.getState() == State.CHOOSE_ANSWER && i < 4) {
			notifyObservers(ucc.chooseAnswer(i));
		} else {
			notifyObservers(State.WRONG_INPUT);
		}
	}
	
	/**
	 * Choose other category to add the points to
	 */
	public void chooseOtherCategory(int i) {
		if (game.getState() == State.CHOOSE_OTHER_CATEGORY && i < 4 && i > -1) {
			notifyObservers(ucc.chooseOtherCategory(i));
		} else {
			notifyObservers(State.WRONG_INPUT);
		}		
	}
	
	public void exit() {
		System.exit(0);
	}

	public State getState() {
		return game.getState();
	}

	public int getLatestRoll() {
		return game.getLatestRoll();
	}

	public ArrayList<Integer> getLatestRolls() {
		return game.getLatestRolls();
	}

	public Player getCurrentPlayer() {
		return game.getCurrentPlayer();
	}

	public Player getPreviousPlayer() {
		return game.getPreviousPlayer();
	}

	public Player getCollision() {
		return game.getCollision();
	}

	public ArrayList<ArrayList<Integer>> getPositionsAll() {
		return game.getPositionsAll();
	}

	public int getPlayerAmount() {
		return game.getPlayerCount();
	}

	public Player[] getPlayers() {
		return game.getPlayers();
	}
	
	public String getQuestion() {
		return game.getQuestion();
	}
	
	public Player getWinner() {
		return game.getWinner();
	}
	
	public String getCategories() {
		return game.getCategories();
	}
}
