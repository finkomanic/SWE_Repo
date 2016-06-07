package application.model.game;

import java.util.HashMap;
import java.util.Random;

public class Questions {	
	private static int AMOUNT_OF_ANSWERS = 4;
	
	// 1st dimension represents categories, 2nd represents questions
	private static String[][] questions = new String[4][4];

	// key: question, value:3 possible answers + 1 right answer
	private static HashMap<String, String[]> answers = new HashMap<String, String[]>();
	
	public Questions() {
		initQuestions();
		initAnswers();
	}
	
	private void initQuestions() {
		// Sport
		questions[0][0] = "Who won the soccer world-championship in 2014?";
		questions[0][1] = "What's the duration of one quarter in an NBA match in minutes?";
		questions[0][2] = "In July of 2016 a world famous boxer died. Who was it?";
		questions[0][3] = "Where does the Olympia of 2016 take place?";

		// Politics
		questions[1][0] = "Who is the chancelor of Germany?";
		questions[1][1] = "What is the name of the building, the American president lives in during his regency?";
		questions[1][2] = "Who is the head of state in Russia?";
		questions[1][3] = "How long is one period of governance in Germany in years?";
		
		// Movies
		questions[2][0] = "Who is the director of Reservoir Dogs?"; // tarantino
		questions[2][1] = "How many Oscars did Titanic get?"; // 11
		questions[2][2] = "In which year Inception got released?"; // 2010
		questions[2][3] = "What is the name of Steven Spielbergs black-and-white-film about the second world war?"; // schindlers list
		
		// Music
		questions[3][0] = "Who is the drummer of Metallica?"; // lars ulrich
		questions[3][1] = "Which famous group was once known as The Quarrymen?";  //beatles
		questions[3][2] = "What was the name of AC / DC s lead singer who died in 1980?"; // Bon Scott
		questions[3][3] = "How many strings does a violin usually have?"; // 4
	}
	
	private void initAnswers() {
		answers.put(questions[0][0], new String[]{"England", "Brazil", "Germany", "Germany"});
		answers.put(questions[0][1], new String[]{"8", "10", "12", "12"});
		answers.put(questions[0][2], new String[]{"Muhammad Ali", "Sonny Liston", "Joe Frazier", "Muhammad Ali"});
		answers.put(questions[0][3], new String[]{"Berlin", "Rio de Janeiro", "Athene", "Rio de Janeiro"});

		answers.put(questions[1][0], new String[]{"Joachim Gauck", "Frank Walter Steinmeier", "Angela Merkel", "Angela Merkel"});
		answers.put(questions[1][1], new String[]{"The Pentagon", "The White House", "The Senat", "The White House"});
		answers.put(questions[1][2], new String[]{"Vladimir Putin", "Vodka Gorbatschow", "Petro Poroschenko", "Vladimir Putin"});
		answers.put(questions[1][3], new String[]{"3", "4", "5", "4"});
		
		answers.put(questions[2][0], new String[]{"Robert Rodriguez", "Quentin Tarantino", "Steven Spielberg", "Quentin Tarantino"});
		answers.put(questions[2][1], new String[]{"7", "9", "11", "11"});
		answers.put(questions[2][2], new String[]{"2008", "2010", "2012", "2010"});
		answers.put(questions[2][3], new String[]{"Shawshank Redemption", "Schindlers List", "12 Angry Men", "Schindlers List"});

		answers.put(questions[3][0], new String[]{"Kirk Hammet", "James Headfield", "Lars Ulrich", "Lars Ulrich"});
		answers.put(questions[3][1], new String[]{"The Doors", "Metallica", "The Beatles", "The Beatles"});
		answers.put(questions[3][2], new String[]{"Bon Jovi", "Bon Scott", "Bon Mike", "Bon Scott"});
		answers.put(questions[3][3], new String[]{"4", "5", "6", "4"});
	}
	
	/**
	 * Returns a random question with possible answers
	 * @param CatID
	 * @return Question
	 */
	public String getQuestion(int CatID) {
		if (CatID < 0 || CatID > 3) {
			throw new IllegalArgumentException();
		}
		
		Random rand = new Random();
		String question = questions[CatID][rand.nextInt(3 - 0 + 1) + 0];

		return question;
	}	
	
	public String getPossibleAnswers(String question) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < AMOUNT_OF_ANSWERS - 1; i++) {
			sb.append("(" + (i+1) + ")" + answers.get(question)[i] + " ");
		}
		
		return sb.toString();
	}
	
	public String getAnswer(String question, int i)
	{
		return answers.get(question)[i];
	}
	
	public String getCorrectAnswer(String question) {
		return answers.get(question)[AMOUNT_OF_ANSWERS - 1];
	}
}
