package application.model.api;

import application.model.game.State;

public interface IObserver {

	/** 
	 * Update this observer with a state.
	 * @param state State (likely the current one)
	 */
	public void update(State state);
}
