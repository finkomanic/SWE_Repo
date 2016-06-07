package application.view;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import application.model.api.IModel;
import application.model.api.IObserver;
import application.model.game.Player;
import application.model.game.State;

/**
 * This class represents the view of the game and provides all necessary output
 * to the console.
 * 
 * @author Stefan
 * 
 */
public class ObserverConsole implements IObserver {
	private IModel model;
	private Buffer buffer;
	private static int NUM_OF_PLAYERS = 3;
	
	// used for thread-activities
	private Semaphore sem;

	/**
	 * Constructs a new console.
	 */
	public ObserverConsole(IModel model) {
		this.buffer = new Buffer();
		this.sem = new Semaphore(0);
		this.model = model;
		this.model.attach(this);
		initComponents();
	}

	/**
	 * Initialize game with 3 players and read in user-input.
	 */
	private void initComponents() {
		model.setPlayers(NUM_OF_PLAYERS);
		getInput();
	}

	/**
	 * Starts the right thread for given input.
	 */
	private void getInput() {
		while (true) {
			switch (buffer.readLine()) {
			case "w":
				new Thread() {
					public void run() {
						System.err.println("Thread: " + getName() + " running");
						model.rollDice();
					}
				}.start();
				break;
			case "1":
				new Thread() {
					public void run() {
						System.err.println("Thread: " + getName() + " running");
						if (model.getState().name().contains("COLLISION")) {
							model.chooseCategory(0);
						} else if (model.getState().name().contains("CHOOSE_ANSWER")) {
							model.chooseAnswer(0);
						} else if (model.getState().name().contains("CHOOSE_OTHER_CATEGORY")) {
							model.chooseOtherCategory(0);
						} else {
							model.chooseFigure(0);
						}
					}
				}.start();
				break;
			case "2":
				new Thread() {
					public void run() {
						System.err.println("Thread: " + getName() + " running");
						if (model.getState().name().contains("COLLISION")) {
							model.chooseCategory(1);
						} else if (model.getState().name().contains("CHOOSE_ANSWER")) {
							model.chooseAnswer(1);
						} else if (model.getState().name().contains("CHOOSE_OTHER_CATEGORY")) {
							model.chooseOtherCategory(1);
						} else {
							model.chooseFigure(1);
						}
					}
				}.start();
				break;
			case "3":
				new Thread() {
					public void run() {
						System.err.println("Thread: " + getName() + " running");
						if (model.getState().name().contains("COLLISION")) {
							model.chooseCategory(2);
						} else if (model.getState().name().contains("CHOOSE_ANSWER")) {
							model.chooseAnswer(2);
						} else if (model.getState().name().contains("CHOOSE_OTHER_CATEGORY")) {
							model.chooseOtherCategory(2);
						} else {
							model.chooseFigure(2);
						}
					}
				}.start();
				break;
			case "4":
				new Thread() {
					public void run() {
						System.err.println("Thread: " + getName() + " running");
						if (model.getState().name().contains("COLLISION")) {
							model.chooseCategory(3);
						} else if (model.getState().name().contains("CHOOSE_OTHER_CATEGORY")) {
							model.chooseOtherCategory(3);
						}
					}
				}.start();
				break;
			case "x":
				new Thread() {
					public void run() {
						System.err.println("Thread: " + getName() + " running");
						model.exit();
					}
				}.start();
				break;
			default:
				this.update(State.WRONG_INPUT);
				break;
			}
			try {
				sem.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (model.getWinner() != null) {
				this.getScoreboard();
				System.out.println("Game is over. " + model.getWinner() + " has won the game!");
				System.exit(0);
			}
		}
	}

	public void update(State state) {
		ArrayList<Integer> latestRolls = model.getLatestRolls();
		Player collision = model.getCollision();
		System.out.println();
		switch (state) {
		case SET_PLAYERS:
			System.out.println("The game got initialized.");
			System.out.println("Please set the amount of players (2-4)");
			break;
		case FIRST_ROLL:
			System.out.println("Amount of players: " + model.getPlayerAmount());
			break;
		case FAILURE:
			for (int i : latestRolls) {
				System.out.println("You rolled a " + i + "!");
			}
			System.out
					.println("None of your figures moved to the starting-field.");
			break;
		case SUCCESS:
			for (int i : latestRolls) {
				System.out.println("You rolled a " + i + "!");
			}
			System.out
					.println("One of your figures moved to the starting-field.");
			break;
		case SUCCESS_COLLISION:
			for (int i : latestRolls) {
				System.out.println("You rolled a " + i + "!");
			}
			System.out
					.println("One of your figures moved to the starting-field.");
			System.out.println("You collided with " + collision.getColor() + "!");
			System.out.println(collision.getColor() + " has to choose a category for the question! ((1)Sport (2)Politics (3)Movies (4)Music)");
			break;
		case CHOOSE_FIGURE:
			System.out.println("You rolled a " + model.getLatestRoll() + "!");
			System.out.println("Choose the figure to move!");
			break;
		case CHOOSE_ANSWER:
			// print possible answers
			System.out.println("Question for " + collision.getColor() + ": " + model.getQuestion());
			System.out.println("Choose your answer!");
			break;
		case CORRECT_ANSWER:
			System.out.println("Your answer was correct!");
			break;
		case CHOOSE_OTHER_CATEGORY:
			System.out.println("Your answer was correct but you are expert in this category!");
			System.out.println("Choose another category to add the points to! " + collision.getNonMaxCategories());
			break;
		case MOVE:
			System.out.println("Your figure moved for " + model.getLatestRoll() + " fields.");
			break;
		case MOVE_COLLISION:
			System.out.println("Your figure moved for " + model.getLatestRoll() + " fields.");
			System.out.println("You collided with " + collision.getColor() + "!");
			System.out.println(collision.getColor() +" has to choose a category for the question! ((1)Sport (2)Politics (3)Movies (4)Music)");
			break;
		case RELEASE:
			System.out.println("You rolled a " + model.getLatestRoll() + "!");
			System.out
					.println("One of your figures moved to the starting-field.");
			break;
		case RELEASE_COLLISION:
			System.out.println("You rolled a " + model.getLatestRoll() + "!");
			System.out
					.println("One of your figures moved to the starting-field.");
			System.out.println("You collided with " + collision.getColor() + "!");
			System.out.println(collision.getColor() +" has to choose a category for the question! ((1)Sport (2)Politics (3)Movies (4)Music)");
			break;
		default:
			System.out.println("The command has to fit to current state!");
			break;
		}
		System.out.println(getScoreboard());
		System.out.println("Please provide the next action:");
		sem.release();
	}

	private String getScoreboard() {
		StringBuilder sb = new StringBuilder();
		Player[] players = model.getPlayers();
		ArrayList<ArrayList<Integer>> positions = model.getPositionsAll();
		sb.append("Current state: ");
		sb.append(model.getState());
		sb.append('\n');
		for (int i = 0; i < model.getPlayerAmount(); i++) {
			sb.append(players[i]);
			sb.append(": Has ");
			sb.append(positions.get(i).size());
			sb.append(" of 3 figures in game");
			if (positions.get(i).size() > 0) {
				sb.append(" with following positions: ");
				for (int j = 0; j < positions.get(i).size(); j++) {
					sb.append(positions.get(i).get(j) + 1);
					sb.append(' ');
				}
			}
			sb.append(" || ");
			sb.append(players[i].getPoints());
			sb.append('\n');
		}
		sb.append(">> It's " + model.getCurrentPlayer());
		sb.append("'s turn.");
		return sb.toString();
	}
}
