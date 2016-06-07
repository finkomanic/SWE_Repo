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
	private int currentCategory;
	private String currentQuestion;
	private String possibleAnswers;
	private String currentAnswer;
	private String correctAnswer;
	private int collidedFigureID;

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
		
		/** Testcase */
		setPlayers(3);
		
		// p1
		board.release(currentPlayer);
		setNextPlayer();
		
		// p2
		board.release(currentPlayer);
		setNextPlayer();
		
		// p3
		board.release(currentPlayer);
		setNextPlayer();	
		
		// p1
		latestRoll = 1;
		moveFigure(0);
		
		// p2
		latestRoll = 2;
		moveFigure(0);
		
		// p3
		latestRoll = 1;
		moveFigure(0);
		
		// p1
		latestRoll = 13;
		moveFigure(0);

	    this.getCollision().setTestPoints(3, 2, 2, 2);
		
		board.release(1);
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
			collidedFigureID = board.getFigureID(collision.getID(), (getPositionsMove().get(i) + latestRoll));
			state = State.MOVE_COLLISION;
		}
	}
	
	/**
	 * Choose a category, to ask a question out of.
	 * @param i Category-ID (1-4)
	 */
	public void chooseCategory(int i) {
		currentCategory = i;
		currentQuestion = questions.getQuestion(i);
		possibleAnswers = questions.getPossibleAnswers(currentQuestion);
		correctAnswer = questions.getCorrectAnswer(currentQuestion);		
		state = State.CHOOSE_ANSWER;
	}
	
	/**
	 * Choose answer
	 * @param i Answer-id
	 */
	public void chooseAnswer(int i) {
		currentAnswer = questions.getAnswer(currentQuestion, i);
		if (currentAnswer == correctAnswer)
		{
			state = State.CORRECT_ANSWER;
			board.restore(collision.getID(), collidedFigureID);
			if (collision.addPoints(currentCategory) == false) {
				state = State.CHOOSE_OTHER_CATEGORY;
			}
		}
		else
		{
			collision.removePoints(currentCategory);
			if (collision.getID() != currentPlayer) {
				this.collision = players[currentPlayer];
				state = State.INCORRECT_ANSWER_COLLIDER;
			}
		}
	}
	
	/**
	 * Add points to given category-id.
	 * @param i Category-id
	 */
	public void chooseOtherCategory(int i) {
		if (collision.addPoints(i) == false) {
			state = State.CHOOSE_OTHER_CATEGORY;
		} else {
			setNextPlayer();
			state = State.CORRECT_ANSWER;
		}
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
	 * Returns the positions of every figure on the field, by the current player.
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
		return currentQuestion + " " + possibleAnswers;
	}

	/**
	 * Sets current player to the next player.
	 */
	private void setNextPlayer() {
		currentPlayer++;
		currentPlayer %= players.length;
	}
	
	/**
	 * Returns the winner if there is one already.
	 * @return The winner or null otherwise
	 */
	public Player getWinner() {
		Player winner = null; 
		
		for (Player p : players) {
			if (p.isWinner()) {
				winner = p;
			}
		}
		
		return winner;
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
