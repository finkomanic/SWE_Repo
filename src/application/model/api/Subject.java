package application.model.api;

import java.util.ArrayList;
import java.util.Iterator;

import application.model.game.State;

public class Subject implements ISubject {
	private ArrayList<IObserver> observers = new ArrayList<IObserver>();

	public void attach(IObserver observer) {
		observers.add(observer);
	}

	public void detach(IObserver observer) {
		observers.remove(observer);
	}

	public void notifyObservers(State state) {
		Iterator<IObserver> it = observers.iterator();
		while (it.hasNext()) {
			((IObserver) it.next()).update(state);
		}
	}

}
