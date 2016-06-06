package application.model.game;

/**
 * Represents the states.
 */
public enum State {

	/**
	 * Set the right amount of players
	 */
	SET_PLAYERS,

	/**
	 * First roll of the game is expected and this starts the initialization of the players
	 */
	FIRST_ROLL,

	/**
	 * Choose figure to move
	 */
	CHOOSE_FIGURE,

	/**
	 * Choose category to ask from
	 */
	CHOOSE_CATEGORY,

	/**
	 * Choose answer for question
	 */
	CHOOSE_ANSWER,

	/**
	 * 1-3 throws with a figure-movement following
	 */
	SUCCESS,

	/**
	 * 1-3 throws with a 6, but an occupied starting-field
	 */
	SUCCESS_COLLISION,

	/**
	 * 1-3 throws without success (-> no 6 / no figure to move)
	 */
	FAILURE,

	/**
	 * illegal user-input
	 */
	WRONG_INPUT,

	/**
	 * figure was moved
	 */
	MOVE,

	/**
	 * figure was moved, but collision happened
	 */
	MOVE_COLLISION,

	/**
	 * ask question with possible answers
	 */
	ASK_QUESTION,
	
	/**
	 * rolled a 6 and released a figure from home-base
	 */
	RELEASE,

	/**
	 * rolled a 6, but the starting-field is occupied
	 */
	RELEASE_COLLISION;

	@Override
	public String toString() {
		switch (this) {
		case SET_PLAYERS:
			return "set amount of players";
		case FIRST_ROLL:
			return "first roll";
		case CHOOSE_FIGURE:
			return "choose figure";
		case CHOOSE_CATEGORY:
			return "choose category";
		case CHOOSE_ANSWER:
			return "choose answer";
		case FAILURE:
			return "3 throws, without a 6";
		case SUCCESS:
			return "within 3 throws, rolled a 6";
		case SUCCESS_COLLISION:
			return SUCCESS + ", collision detected";
		case ASK_QUESTION:
			return "ask question";
		case RELEASE:
			return "1 throw, rolled a 6";
		case RELEASE_COLLISION:
			return RELEASE + ", but collision on starting-field";
		case MOVE:
			return "moved figure";
		case MOVE_COLLISION:
			return MOVE + ", collision";
		default:
			throw new IllegalArgumentException();
		}
	}

}
