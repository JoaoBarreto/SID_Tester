package Test;

import java.util.ArrayList;
import java.util.List;

public class QuestionAnswer {

	private String question;
	private String answer;
	private List<String> respostas = new ArrayList<String>();

	public QuestionAnswer(String question, String answer) {
		this.question = question;
		this.answer = answer;
		buildAnswer();
	}

	public String getQuestion() {
		return question;
	}

	public List<String> getAnswerList() {
		return respostas;
	}

	public void buildAnswer() {
		if (answer != "bug" && answer != null) {

			answer = answer.substring(1, answer.length() - 1);

			int chopBeginPos = 0;
			String temp = "";
			for (int i = 0; i < answer.length(); i++) {
				if (answer.charAt(i) == ',') {
					temp = answer.substring(chopBeginPos, i);
					chopBeginPos = i + 1;
					respostas.add(temp);
				}
			}
		}
	}
	
	public String toString(){
		StringBuilder qaString = new StringBuilder();
		qaString.append(getQuestion());
		
		for(String s : respostas){
			qaString.append(s);
		}
		
		return qaString.toString();
	}
}