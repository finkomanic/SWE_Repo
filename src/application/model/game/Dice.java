package application.model.game;

/**
 * Represents a dice with numbers 1 to 6.
 * 
 * @author Stefan
 */
public class Dice {
	/**
	 * Returns an integer between 1 and 6.
	 * @return Integer between 1-6
	 */
	public int roll() {
		return (int) (6 * Math.random() + 1);
	}
}
