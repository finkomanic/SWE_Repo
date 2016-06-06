package application.model.game;

import java.util.ArrayList;

public class Board {
	// Represents all fields of the board.
	private Field[] circle;

	/**
	 * Stores the home of each player. 
	 * 1st dimension: Player-ID
	 * 2nd dimension: corresponding starting field
	 */
	private Field[][] homes;

	/**
	 * Constructs a new board with 48 fields.
	 */
	public Board() {
		circle = new Field[48];
		for (int i = 0; i < 48; i++) {
			circle[i] = new Field();
		}
	}

	public void removeFigure() {
		return;
	}

	/**
	 * Moves a figure on given position for num fields.
	 * @param position Position, the figure is on
	 * @param num Num of fields, the figure has to move
	 */
	public void move(int position, int num) {
		Figure figure = circle[position].getFigure();
		circle[position].removeFigure();
		circle[(position + num) % 48].setFigure(figure);
	}

	/**
	 * Checks, if given move from position to position+num would result in a collision.
	 * @param position Position, the figure is on
	 * @param num Num of fields, the figure has to move
	 * @return The striked player or null, if no collision would occur.
	 */
	public Player moveCollisionDetection(int position, int num) {
		Player ret = null;
		if (circle[(position + num) % 48].getFigure() != null) {
			ret = circle[(position + num) % 48].getFigure().getOwner();
		}
		
		return ret;
	}

	/**
	 * Returns the field-position for given figure.
	 * @param figure Given figure
	 * @return Field-position
	 */
	private Integer getIndex(Figure figure) {
		Integer index = null;
		for (int i = 0; i < 48; i++) {
			if (circle[i].getFigure() == figure) {
				index = i;
			}
		}
		return index;
	}

	/**
	 * Releases a figure from the homebase to the field.
	 * @param currentPlayer Player-ID, that releases the figure
	 */
	public void release(int currentPlayer) {
		int index = -1;
		for (int i = 0; i < 3; i++) {
			if (homes[currentPlayer][i].getFigure() != null) {
				index = i;
			}
		}
		circle[currentPlayer * 12].setFigure(homes[currentPlayer][index].getFigure());
		homes[currentPlayer][index].removeFigure();
	}

	/**
	 * Returns the striked player, if the release would result in a collision.
	 * @param currentPlayer ID of the player, thats figure will be released
	 * @return Player-ID of the striked player or null, if no collision occured
	 */
	public Player releaseCollisionDetection(int currentPlayer) {
		Player ret = null;
		if (circle[currentPlayer * 12].getFigure() != null) {
			ret = circle[currentPlayer * 12].getFigure().getOwner();
		}
		return ret;
	}

	/**
	 * Moves a figure back to starting-field or homebase if it's occupied.
	 * @param figure Figure to move back
	 */
	public void restore(int currentPlayer, int figID) {
		Figure figure = circle[figID].getFigure();
		boolean ret = false;
		if (circle[currentPlayer * 12].getFigure() != null 
				&& circle[currentPlayer * 12].getFigure().getOwner().getID() == currentPlayer) {
			for (int i = 0; i < 3 && !ret; i++) {
				if (homes[figure.getOwner().getID()][i] == null) {
					homes[figure.getOwner().getID()][i].setFigure(figure);
					ret = true;
				}
			}
		} else {
			circle[currentPlayer * 12].setFigure(figure);
		}
	}

	/**
	 * Sets the players starting-fields and homebases.
	 * @param players Players to initialize
	 */
	public void setPlayers(Player[] players) {
		homes = new Field[players.length][3];
		for (int i = 0; i < players.length; i++) {
			circle[i * 12] = new Start(players[i]);
			for (int j = 0; j < 3; j++) {
				homes[i][j] = new Field(new Figure(players[i]));
			}
		}
	}

	/**
	 * Returns the positions of all figures of a given player.
	 * @param player ID of player
	 * @return positions
	 */
	public ArrayList<Integer> getPositions(int player) {
		ArrayList<Integer> positions = new ArrayList<Integer>();
		for (int i = 0; i < 48; i++) {
			if (circle[i].getFigure() != null && circle[i].getFigure().getOwner().getID() == player) {
				positions.add(i);
			}
		}
		return positions;
	}

	/**
	 * Returns if home of given player is full. 
	 * Gives information about whether the player has figures in game or not.
	 * @param playerNr ID of player
	 * @return True if the home is full or false otherwise
	 */
	public boolean isHomeFull(int playerNr) {
		boolean homeFull = true;
		for (int i = 0; i < 3; i++) {
			if (homes[playerNr][i].getFigure() == null) {
				homeFull = false;
			}
		}
		return homeFull;
	}

	/**
	 * Returns if home of given player is empty. 
	 * @param playerNr ID of player
	 * @return True if the home is empty or false otherwise
	 */
	public boolean isHomeEmpty(int playerNr) {
		boolean homeEmpty = true;
		for (int i = 0; i < 3; i++) {
			if (homes[playerNr][i] != null) {
				homeEmpty = false;
			}
		}
		return homeEmpty;
	}
}
