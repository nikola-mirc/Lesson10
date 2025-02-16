package exercises;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import examples.FileHelper;

public class Hangman extends KeyAdapter {

	Stack<String> puzzles = new Stack<String>();
	ArrayList<JLabel> boxes = new ArrayList<JLabel>();
	int lives = 9;
	JLabel livesLabel = new JLabel("" + lives, SwingConstants.CENTER);
	StringBuffer buffer = new StringBuffer();
	List<String> wordList = FileHelper.loadFileContentsIntoArrayList("resource/words.txt");
	Random r = new Random();

	public static void main(String[] args) {
		Hangman hangman = new Hangman();
		hangman.addPuzzles();
		hangman.createUI();
	}

	private void addPuzzles() {
		puzzles.push(wordList.get(r.nextInt(wordList.size())).toString());
	}

	JPanel panel = new JPanel();
	private String puzzle;

	private void createUI() {
		playDeathKnell();
		JFrame frame = new JFrame("June's Hangman");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.add(livesLabel);
		panel.setPreferredSize(new Dimension(1000, 120));
		loadNextPuzzle();
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.pack();
		frame.addKeyListener(this);
	}

	private void loadNextPuzzle() {
		removeBoxes();
		lives = 9;
		livesLabel.setFont(new Font("Calibri", Font.BOLD, 80));
		livesLabel.setText("" + lives);
		addPuzzles();
		puzzle = puzzles.pop().toLowerCase();
		buffer.setLength(puzzle.length());
		System.out.println("puzzle is now " + puzzle);
		createBoxes();
	}

	public void keyTyped(KeyEvent arg0) {
		System.out.println(arg0.getKeyChar());
		try {
			checkChar(arg0.getKeyChar());
			updateBoxesWithUserInput(arg0.getKeyChar());
			if (lives == 0) {
				playDeathKnell();
				loadNextPuzzle();
			}
		} catch (Exception e) {
			System.out.println("Invalid character. Starting a new game...");
			loadNextPuzzle();
		}
		
	}

	private boolean checkChar(char keyChar) throws Exception {
		boolean hasSpecial = false;
		String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.'";
		if (specialChars.contains(String.valueOf(keyChar))) {
			hasSpecial = true;
			throw new Exception("Special character entered by the user.");
			
		}
		return hasSpecial;
	}

	private char toLowerCase(char keyChar) {
		keyChar = Character.toLowerCase(keyChar);
		return keyChar;
	}

	private void updateBoxesWithUserInput(char keyChar) {
		boolean gotOne = false;
		keyChar = toLowerCase(keyChar);
		for (int i = 0; i < puzzle.length(); i++) {
			if (puzzle.charAt(i) == keyChar) {
				boxes.get(i).setText("" + keyChar);
				buffer.setCharAt(i, keyChar);
				if (buffer.toString().equals(puzzle)) {
					System.out.println("Congrats!");
					loadNextPuzzle();
				}
				gotOne = true;
			}
		}
		if (!gotOne)
			livesLabel.setText("" + --lives);
	}

	void createBoxes() {
		for (int i = 0; i < puzzle.length(); i++) {
			JLabel textField = new JLabel("_");
			textField.setFont(new Font("Calibri", Font.BOLD, 80));
			textField.setSize(1000, 200);
			boxes.add(textField);
			panel.add(textField);
		}
	}

	void removeBoxes() {
		for (JLabel box : boxes) {
			panel.remove(box);
		}
		boxes.clear();
	}

	public void playDeathKnell() {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("resource/funeral-march.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			Thread.sleep(1000);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
