package application.model.game;

public class Figure {

	private Player owner;

	/**
	 * Constructs a new figure with given Player as it's owner.
	 * @param owner
	 */
	public Figure(Player owner) {
		this.owner = owner;
	}

	/**
	 * Returns owner of this figure.
	 * @return owner of this figure
	 */
	public Player getOwner() {
		return owner;
	}
}
