package application.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The buffer reads in the strings from the console.
 */
public class Buffer {
	private BufferedReader reader;

	/**
	 * Constructs a new buffer, that reads in the stings from system-input.
	 */
	public Buffer() {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	/**
	 * Returns the current read line.
	 * @return current read line or "\n" in case of an IOException.
	 */
	public String readLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			return "\n";
		}
	}
}
