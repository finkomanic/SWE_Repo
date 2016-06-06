package application.model.game;

public class Player {

	private Color color;
	private int id;

	/**
	 * Constructs a player with a given id and color.
	 * @param id Player-ID
	 * @param color Color
	 */
	public Player(int id, Color color) {
		this.id = id;
		this.color = color;
	}

	public int getID() {
		return id;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public String toString() {
		return "Player " + (id + 1) + " (" + color + ")";
	}
}
