package application.model.game;

/**
 * Represents a starting field in front of the players homebase.
 * @author Stefan
 *
 */
public class Start extends Field {
	private Player owner;

	/**
	 * Constructs a new starting field.
	 * @param owner Owner of field
	 */
	public Start(Player owner) {
		super();
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}
}
