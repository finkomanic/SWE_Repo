package application;

import application.model.api.IModel;
import application.model.usecases.Fassade;
import application.view.ObserverConsole;

public class Main {
	public static void main(String[] args) {
		IModel model = new Fassade();
		new ObserverConsole(model);
	}
}
