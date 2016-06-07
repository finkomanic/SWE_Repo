package application.model.game;

public class Player {

	private Color color;
	private int id;
	
	// stores the points of this player for all 4 categories
	private int[] points;

	/**
	 * Constructs a player with a given id and color.
	 * @param id Player-ID
	 * @param color Color
	 */
	public Player(int id, Color color) {
		this.id = id;
		this.color = color;
		this.points = new int[]{0, 0, 0, 0};
	}

	public int getID() {
		return id;
	}

	public Color getColor() {
		return color;
	}
	
	public boolean addPoints(int catID) {
		boolean added = false;
		
		if (points[catID] < 3) {
			points[catID]++;
			added = true;
		}
		
		return added;
	}
	
	public void setTestPoints(int cat1, int cat2, int cat3, int cat4) {
		points[0] = cat1;
		points[1] = cat2;
		points[2] = cat3;
		points[3] = cat4;
	}
	
	public void removePoints(int catID) {
		if (points[catID] > 0) {
			points[catID]--;
		} else {
			for (int i = 0; i < points.length; i++) {
				if (points[i] > 0) {
					points[i]--;
					break;
				}
			}
		}
	}

	public String getPoints() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < points.length; i++)
		{
			sb.append("Cat-" + (i+1) + ":" + points[i]);
			if (i < points.length - 1) {
				sb.append(", ");
			}
		}
		
		sb.append("]");
		return sb.toString();
	}
	
	public String getNonMaxCategories() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < points.length; i++)
		{
			if (points[i] < 3) {
				sb.append("Cat-" + (i+1) + ":" + points[i]);
				if (i < points.length - 1) {
					sb.append(", ");
				}
			}
		}
		
		sb.append("]");
		return sb.toString();		
	}
	
	/**
	 * Determines, if all points in all categories are 3.
	 * @return True if this player has won the match, false otherwise
	 */
	public boolean isWinner() {
		boolean winner = true;
		
		for (int i = 0; i < points.length; i++) {
			if (points[i] < 3) {
				winner = false;
			}
		}
		
		return winner;
	}
	
	@Override
	public String toString() {
		return "Player " + (id + 1) + " (" + color + ")";
	}
}
