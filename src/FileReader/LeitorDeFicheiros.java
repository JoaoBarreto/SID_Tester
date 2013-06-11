package FileReader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.swing.JOptionPane;

import Test.QuestionAnswer;

public class LeitorDeFicheiros {

	private FileReader file;
	private BufferedReader br;
	private Random rand = new Random();
	private int numeroDeLinhas;
	
	
	public LeitorDeFicheiros(String path){
		try {
			file = new FileReader(path);
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "Erro: L28:LDF - Erro critico! \nNão foi encontrado o ficheiro TesteSID.txt.\nO programa irá encerrar.");
			System.exit(0);
		}
		try {
			if(file != null)
				this.br = new BufferedReader(file);
		
			numeroDeLinhas = count(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public QuestionAnswer getQuestionAndAnswer() {
		String question = "";
		String answer = "";
		boolean questionAndAnswerBuilt = true;

		int linhaDaPergunta = GeraNumeroDaLinha(numeroDeLinhas, rand);
		try {
			int contadorPergunta = 0;

			while ((question = br.readLine()) != "EOF" && questionAndAnswerBuilt) {
				answer = br.readLine();

				if (contadorPergunta == linhaDaPergunta)
					return new QuestionAnswer(question, answer);
				else
					contadorPergunta += 2;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return new QuestionAnswer("bug", "bug");
	}

	private int GeraNumeroDaLinha(int numeroDeLinhas, Random rand) {
		@SuppressWarnings("unused")
		int linhaDaResposta = 0;
		int linhaDaPergunta = 1;
		while(!isPar(linhaDaPergunta)){
			linhaDaPergunta = rand.nextInt(numeroDeLinhas);
			linhaDaResposta = linhaDaPergunta+1;
		}
		return linhaDaPergunta;
	}
	
	private boolean isPar(int number){
		if(number % 2 == 0)
			return true;
			
		else return false;
	}

	public void init() {
		getQuestionAndAnswer();
	}
	
	public int count(String filename) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
	}
	
	public void closeAll() {
		try {
			br.close();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}