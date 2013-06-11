package GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import Test.QuestionAnswer;
import Test.Test;

public class mainGui {

	//-----------------
	private String path = "";
	private int numeroDePerguntas = 20;
	//-----------------
	
	private JFrame windowFrame;
	private JLabel lb_infoPergunta;
	private JTextPane lb_pergunta;
	private JPanel pn_checkboxes;
	private JCheckBox checkBox;
	
	//-----------------
	private int contadorDePerguntas = 1;
	private Test testeSid;
	//-----------------
	private List<String> listOfCorrectAnswers = new LinkedList<String>();
	private List<JCheckBox> checkBox_list = new LinkedList<JCheckBox>();

	private int respostas = 0;
	private boolean respostaCorrectaFlag;
	private int respostasCorrectas = 0;
	private boolean modoAprendizagem = false;
	private String versionControl = "SID Test Simulator V.1\nIGE 11-06-2013\nDeveloped by: Jo�o Barreto\nwww.joaobarreto.pt";
	private String title = "While True Systems - SID Test Simulator V.1";
	private String helpModeState = "On";
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainGui window = new mainGui();
					window.windowFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainGui() {
		initialize();
		buildTestSystem();
	}
	
	private void buildTestSystem() {
		openFile();
		testeSid = new Test(numeroDePerguntas , path);
		testeSid.initTestBatterie();
		getNextQuestion();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		windowFrame = new JFrame();
		windowFrame.setResizable(false);
		windowFrame.setTitle(title + " " + " - Pergunta n� " + contadorDePerguntas + "/");
		windowFrame.setBounds(100, 100, 770, 397);
		windowFrame.setLocationRelativeTo(null);
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.getContentPane().setLayout(null);
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 764, 21);
		windowFrame.getContentPane().add(menuBar);
		
		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		final JMenuItem menuItemHelpMode = new JMenuItem("Help Mode " + helpModeState);
		menuFile.add(menuItemHelpMode);
		menuItemHelpMode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (helpModeState == "Off") {
					setHelpMode("On");
					modoAprendizagem = false;
					menuItemHelpMode.setText("Help Mode " + helpModeState);
				} else {
					setHelpMode("Off");
					modoAprendizagem = true;
					menuItemHelpMode.setText("Help Mode " + helpModeState);
				}
			}

		});
		
		JMenuItem menuItemFechar = new JMenuItem("Fechar");
		menuFile.add(menuItemFechar);
		menuItemFechar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		JMenu menuAbout = new JMenu("About");
		menuBar.add(menuAbout);
		
		JMenuItem sobreMenu = new JMenuItem("Sobre");
		menuAbout.add(sobreMenu);
		sobreMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, versionControl);
			}
		});
		
		
		
		lb_infoPergunta = new JLabel("Pergunta n\u00BA: " + contadorDePerguntas + "/" + numeroDePerguntas);
		lb_infoPergunta.setBounds(26, 60, 420, 14);
		windowFrame.getContentPane().add(lb_infoPergunta);
		
		lb_pergunta = new JTextPane();
		lb_pergunta.setBounds(25, 85, 700, 85);
		lb_pergunta.setEditable(false);
		windowFrame.getContentPane().add(lb_pergunta);
		
		pn_checkboxes = new JPanel();
		pn_checkboxes.setLayout(new javax.swing.BoxLayout(pn_checkboxes, javax.swing.BoxLayout.Y_AXIS));
		pn_checkboxes.setBounds(25, 181, 700, 125);
		
		JButton btnNewButton = new JButton("Next question");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkBox_list.clear();
				
				listOfCorrectAnswers.clear();
				respostas = 0;
				if(!((contadorDePerguntas+1) > numeroDePerguntas)){
					contadorDePerguntas++;
					
					getNextQuestion();
					if(respostaCorrectaFlag){
						respostasCorrectas++;
						respostaCorrectaFlag = false;
					}
				}
				else
					testResult();
			}
		});
		btnNewButton.setBounds(229, 327, 132, 23);
		windowFrame.getContentPane().add(btnNewButton);

		JButton resetButton = new JButton("Reset answers");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetAnswers();
			}
		});
		resetButton.setBounds(371, 327, 132, 23);
		windowFrame.getContentPane().add(resetButton);
	}
	
	private void getNextQuestion() {
		pn_checkboxes.removeAll();
		pn_checkboxes.updateUI();
		
		lb_infoPergunta.setText("Pergunta n\u00BA: " + contadorDePerguntas + "/" + numeroDePerguntas);
		windowFrame.setTitle(title + " - Pergunta n� " + contadorDePerguntas);
		
		QuestionAnswer newQuestionAndAnswer = testeSid.getListOfQuestions().get(contadorDePerguntas-1);
		lb_pergunta.setText(newQuestionAndAnswer.getQuestion());
		buildAnswerSystem(newQuestionAndAnswer.getAnswerList());
		
		if(modoAprendizagem)
			imprimeRespostas();
	}

	private void buildAnswerSystem(List<String> list) {
		
		for (int i = 0; i < list.size(); i++) {
			String newChoice = list.get(i);
			if(newChoice.contains("<Correct>")){
				newChoice = correctRemover(newChoice);
				listOfCorrectAnswers.add(newChoice);
			}
			
			checkBox = new JCheckBox(newChoice);
			
			checkBox.addItemListener(new ItemListener() {
				String teste = checkBox.getText();
				
				public void itemStateChanged(ItemEvent e) {
					
					if(e.getStateChange() == 1){
						respostas++;
						if(listOfCorrectAnswers.contains(teste) && respostas == listOfCorrectAnswers.size())
							respostaCorrectaFlag = true;
					}
					else 
						respostas--;
				}
			});
			checkBox_list.add(checkBox);
		}
		buildCheckBoxAnswers();
	}
	
	
	public void buildCheckBoxAnswers(){
		for(JCheckBox checkBox : checkBox_list)
			pn_checkboxes.add(checkBox);
		
		windowFrame.getContentPane().add(pn_checkboxes);
	}
	// --------------- TOOLS -------------------------------------------------

	private void resetAnswers() {
		for(JCheckBox c : checkBox_list)
			c.setSelected(false);
	}
	
	private String correctRemover(String oldString){
		String newString = "";
		for (int i = 0; i < oldString.length(); i++) {
			if(oldString.charAt(i) == '<'){
				newString = oldString.substring(0, i);
			}
		}
		return newString;
	}
	
	private void imprimeRespostas() {
		System.out.println("-------------- PERGUNTA n� " +  contadorDePerguntas + " ---------------------------");
		for(String s : listOfCorrectAnswers)				
			System.out.println("Resposta(s) correcta(s): " + s);
		System.out.println("--------------------------------------------------------");
	}
	
	private void testResult() {
		if(respostasCorrectas >= 10)
			JOptionPane.showMessageDialog(null, "Parab�ns! Tiveste " + trespostasCorrectas + ".";
		else
			JOptionPane.showMessageDialog(null, "Bad luck... tiveste " + respostasCorrectas + ".");
		System.exit(0);
	}
	
	private void openFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("./"));
		int actionDialog = chooser.showSaveDialog(null);
		if (actionDialog == JFileChooser.APPROVE_OPTION)
			path = chooser.getSelectedFile().getAbsolutePath();
	}
	private void setHelpMode(String helpMode) {
		helpModeState = helpMode;
	}

}
