package application.model.game;

import java.util.ArrayList;

/**
 * Represents the game.
 * 
 * @author Stefan
 */
public class Game {
	// all players
	private Player[] players;

	// current player-id
	private int currentPlayer;

	private Dice dice;
	private Board board;
	private Questions questions;
	
	// result of last throw
	private int latestRoll;

	// results of last throws (1-3)
	private ArrayList<Integer> latestRolls;

	// last striked player
	private Player collision;

	// current game-state
	private State state;
	
	// currently asked question
	private String question;
	private String possibleAnswers;
	private String correctAnswer;

	/**
	 * Constructs a new game.
	 */
	public Game() {
		dice = new Dice();
		board = new Board();
		questions = new Questions();
		latestRoll = 0;
		latestRolls = null;
		state = State.SET_PLAYERS;
	}

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
	public void rollDice() {
		latestRoll = 0;
		latestRolls = null;
		if (board.isHomeFull(currentPlayer)) {
			latestRolls = new ArrayList<Integer>();
			int roll = 0;
			for (int i = 0; i < 3 && roll != 6; i++) {
				roll = dice.roll();
				latestRolls.add(roll);
				if (roll == 6) {
					collision = board.releaseCollisionDetection(currentPlayer);
					board.release(currentPlayer);
					if (collision == null) {
						setNextPlayer();
						state = State.SUCCESS;
					} else {
						state = State.SUCCESS_COLLISION;
					}
				}
			}
			if (roll != 6) {
				setNextPlayer();
				state = State.FAILURE;
			}
		} else {
			latestRoll = dice.roll();
			if (latestRoll == 6) {
				if (board.isHomeEmpty(currentPlayer)) {
					state = State.CHOOSE_FIGURE;
				} else {
					collision = board.releaseCollisionDetection(currentPlayer);
					if (collision == null) {
						board.release(currentPlayer);
						state = State.RELEASE;
					} else {
						state = State.RELEASE_COLLISION;
					}
				}
			} else {
				state = State.CHOOSE_FIGURE;
			}
		}
	}

	/**
	 * Moves figure of current player for given figure-id.
	 * @param i Figure-ID
	 */
	public void moveFigure(int i) {
		collision = board.moveCollisionDetection(getPositionsMove().get(i), latestRoll);
		if (collision == null) {
			board.move(getPositionsMove().get(i), latestRoll);
			setNextPlayer();
			state = State.MOVE;
		} else {
			state = State.MOVE_COLLISION;
		}
	}
	
	/**
	 * Choose a category, to ask a question out of.
	 * @param i Category-ID (1-4)
	 */
	public void chooseCategory(int i) {
		question = questions.getQuestion(i);
		possibleAnswers = questions.getPossibleAnswers(question);
		correctAnswer = questions.getCorrectAnswer(question);		
		state = State.CHOOSE_ANSWER;
	}
	
	public void chooseAnswer(int i) {
		
	}

	/**
	 * Sets the amount of players and initializes the corresponding homebases and figures.
	 * @param num Amount of players
	 */
	public void setPlayers(int num) {
		players = new Player[num];
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player(i, Color.getColor(i));
		}
		board.setPlayers(players);
		currentPlayer = 0;
		state = State.FIRST_ROLL;
	}

	/**
	 * Returns amount of players.
	 * @return Amount of players
	 */
	public int getPlayerCount() {
		return players.length;
	}

	/**
	 * Return last striked player.
	 * @return Last striked player or null if there was no collision
	 */
	public Player getCollision() {
		return collision;
	}

	/**
	 * Returns the last result of the dice.
	 * @return The last result of the dice
	 */
	public int getLatestRoll() {
		return latestRoll;
	}

	/**
	 * Returns the last results of the dice.
	 * @return The last results of the dice
	 */
	public ArrayList<Integer> getLatestRolls() {
		return latestRolls;
	}

	/**
	 * Returns current player.
	 * @return Current player
	 */
	public Player getCurrentPlayer() {
		return players[currentPlayer];
	}

	/**
	 * Return previous player.
	 * @return Previous player
	 */
	public Player getPreviousPlayer() {
		return players[(currentPlayer - 1 + players.length) % players.length];
	}

	/**
	 * Returns the positions of every figure in game, by the current player.
	 * @return Positions of current players figures
	 */
	public ArrayList<Integer> getPositionsMove() {
		return board.getPositions(currentPlayer);
	}

	/**
	 * Returns each players figure-positions.
	 * @return Each players figure-positions
	 */
	public ArrayList<ArrayList<Integer>> getPositionsAll() {
		ArrayList<ArrayList<Integer>> ret = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < getPlayerCount(); i++) {
			ret.add(board.getPositions(i));
		}
		return ret;
	}

	/**
	 * Returns the players as array.
	 * @return The players
	 */
	public Player[] getPlayers() {
		return players;
	}

	/**
	 * Return current state.
	 * @return current state
	 */
	public State getState() {
		return state;
	}
	
	/**
	 * return current question.
	 * @return current question with possible answers
	 */
	public String getQuestion() {
		return question + " " + possibleAnswers;
	}

	/**
	 * Sets current player to the next player.
	 */
	private void setNextPlayer() {
		currentPlayer++;
		currentPlayer %= players.length;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		ArrayList<ArrayList<Integer>> positions = getPositionsAll();
		sb.append("Current state is <");
		sb.append(state + ">");
		for (int i = 0; i < players.length; i++) {
			sb.append(players[i]);
			sb.append(": Has ");
			sb.append(positions.get(i).size());
			sb.append(" of 3 figure in game");
			if (positions.get(i).size() > 0) {
				sb.append(" with following positions: ");
				for (int j = 0; j < positions.get(i).size(); j++) {
					sb.append(positions.get(i).get(j) + 1);
					sb.append(' ');
				}
			}
			sb.append('\n');
		}
		sb.append(players[currentPlayer]);
		sb.append("'s turn.");
		return sb.toString();
	}

}
