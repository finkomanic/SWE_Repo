package application.model.game;

public enum Color {
	RED, BLUE, GREEN, YELLOW;

	/**
	 * Returns a color for given id.
	 * @param i Color-ID
	 * @return color
	 */
	public static Color getColor(int i) {
		switch (i) {
		case 0:
			return RED;
		case 1:
			return BLUE;
		case 2:
			return GREEN;
		case 3:
			return YELLOW;
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String toString() {
		switch (this) {
		case RED:
			return "red";
		case BLUE:
			return "blue";
		case GREEN:
			return "green";
		case YELLOW:
			return "yellow";
		default:
			throw new IllegalArgumentException();
		}
	}
}
