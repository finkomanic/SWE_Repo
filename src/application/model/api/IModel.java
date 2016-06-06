package application.model.api;

import java.util.ArrayList;

import application.model.game.Player;
import application.model.game.State;

public interface IModel extends ISubject {
	/**
	 * Set amount of players.
	 * @param i amount of players
	 */
	public void setPlayers(int i);

	/**
	 * Rolls the dice 1 to 3 times depending on the current situation. 
	 * <ul>
	 * a) Player has no figure on field -> 1-3 throws
	 * <ul>
     *    <li>1. result == 6</li>
     *    	<ul>
     *    		-> move figure on starting field
     *      	-> check for collisions
     *          -> next players turn or questioning
     *    	</ul>
     *    <li>2. Result != 6</li>
     *    	<ul>
     *    		-> next players turn
     *    	</ul>
     * </ul>
     * b) Player has figure on field, result < 6
	 * 		<ul>
     *    		-> choose figure to move
     *    	</ul>
     * c) Player has figure on field, result == 6
	 * 		<ul>
     *    		-> release new figure or move, if homebase is empty
     *    	</ul>
     * </ul>
	 */
	public void rollDice();

	/**
	 * Choose figure to move.
	 * @param i Figure index
	 */
	public void chooseFigure(int i);

	/**
	 * Choose category to ask questions of.
	 * 
	 * @param i Category index
	 */
	public void chooseCategory(int i);

	/**
	 * Choose answer for asked question.
	 * @param i Answer index
	 */
	public void chooseAnswer(int i);
	
	/**
	 * Choose other category, to add the points to.
	 * @param i Category-ID
	 */
	public void chooseOtherCategory(int i);

	/**
	 * Exit the game.
	 */
	public void exit();

	/**
	 * Return current state.
	 * @return current state
	 */
	public State getState();

	/**
	 * Return last result, for a single throw.
	 * @return Last result or 0 if there was no dice-rolling
	 */
	public int getLatestRoll();

	/**
	 * Return lsat results, for multiple throws (up to 3 times).
	 * @return Last results or null if there was no dice-rolling
	 */
	public ArrayList<Integer> getLatestRolls();

	/**
	 * Return current player.
	 * @return Current player
	 */
	public Player getCurrentPlayer();

	/** 
	 * Return previous turns' player.
	 * @return Previous turns' player
	 */
	public Player getPreviousPlayer();


	/**
	 * Returns previous collided player.
	 * @return Previous collided player or null if there was no collision
	 */
	public Player getCollision();

	/**
	 * Returns each players figure-positions.
	 * @return Each players figure-positions
	 */
	public ArrayList<ArrayList<Integer>> getPositionsAll();

	/**
	 * Returns amount of players.
	 * @return Amount of players
	 */
	public int getPlayerAmount();

	/**
	 * Returns the players as array.
	 * @return The players
	 */
	public Player[] getPlayers();
	
	/**
	 * Returns the current question.
	 * @return Current question and possible answers
	 */
	public String getQuestion();
}
