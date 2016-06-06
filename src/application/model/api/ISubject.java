package application.model.api;

import application.model.game.State;

public interface ISubject {
	/**
	 * Registers an observer.
	 * @param observer
	 */
	public void attach(IObserver observer);

	/**
	 * Removes an observer.
	 * @param observer
	 */
	public void detach(IObserver observer);

	/**
	 * Notifies all currently attached observers with current state.
	 * @param state Current state
	 */
	public void notifyObservers(State state);

}
