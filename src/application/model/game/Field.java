package application.model.game;

public class Field {
	// the figure currently positioned on this field
	private Figure figure;

	/**
	 * Constructs an empty field.
	 */
	public Field() {
		figure = null;
	}

	/**
	 * Constructs a field and occupies it with a given figure.
	 * @param figure Given figure
	 */
	public Field(Figure figure) {
		this.figure = figure;
	}

	public void setFigure(Figure figure) {
		this.figure = figure;
	}
	
	/**
	 * Sets current figure to null -> field gets empty. 
	 */
	public void removeFigure() {
		figure = null;
	}

	public Figure getFigure() {
		return figure;
	}
}
