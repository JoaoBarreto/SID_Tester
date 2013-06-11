package Test;

import java.util.ArrayList;
import java.util.List;

import FileReader.LeitorDeFicheiros;
public class Test {

	private int numeroDePerguntas;
	private List<QuestionAnswer> baterieOfTests = new ArrayList<QuestionAnswer>();
	private String path;
	
	public Test (int numeroDePerguntas, String path){
		this.numeroDePerguntas = numeroDePerguntas;
		this.path = path;
	}
	
	public void initTestBatterie(){
		LeitorDeFicheiros leitor;
		for(int i = 0; i<numeroDePerguntas; i++){
			leitor = new LeitorDeFicheiros(path);
			baterieOfTests.add(leitor.getQuestionAndAnswer());
			leitor.closeAll();
		}
	}

	public List<QuestionAnswer> getListOfQuestions() {
		return baterieOfTests;
	}
}