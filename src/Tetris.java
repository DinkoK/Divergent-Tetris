/******************************************************************************
 * File : Tetris_Methods.java 
 * Team: "PERIPHERY"
 * Web: http://www.Softuni.bg 
 * Version:0.99
 ******************************************************************************/
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.net.URL;
import java.util.Random;

public class Tetris extends javax.swing.JPanel implements
		java.awt.event.KeyListener {
	public static void main(String[] args) throws Exception {
		javax.swing.JFrame window = new javax.swing.JFrame(
				"PERIPHERY DIVERGENTRIS");
		window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

		Tetris tetris = new Tetris();
		tetris.init();
		window.add(tetris);
		window.pack();
		window.setVisible(true);

		window.addKeyListener(tetris);

		generateAllTokens();
		tetris.gameOver = false;
		try {
			Thread.sleep(10); // GAME START DELAY
		} catch (Exception ignore) {
		}
		AudioClip clip = backgroundMusic();
		while (!tetris.gameOver) {
			tetris.addRisingToken(allTokens[indexOfCurrentToken]);
			tetris.checkRowCompletionAndPlaySound();
			indexOfCurrentToken++; // needed for next token preview, and
			if (tetris.gameOver) {
				clip.stop();
			}
		}
		tetris.printGameOver();
	}

	void init() { // creates the labels
		this.setPreferredSize(new java.awt.Dimension(420, 600));
		this.setBackground(java.awt.Color.LIGHT_GRAY);
		this.setLayout(null); // absolute coordinate system

		scoreLabel.setBounds(300, 50, 100, 30); // x,y,w,h (in pixels)
		this.add(scoreLabel);

		completedLinesLabel.setBounds(300, 75, 110, 30);
		this.add(completedLinesLabel);

		tokensLeftLabel.setBounds(300, 100, 110, 30);
		this.add(tokensLeftLabel);

		levelLabel.setBounds(300, 125, 100, 30);
		this.add(levelLabel);

		nextLabel.setBounds(300, 175, 300, 30);
		this.add(nextLabel);

		timeLabel.setBounds(300, 150, 100, 30);
		this.add(timeLabel);
	}

	public void paint(java.awt.Graphics gr) {
		super.paint(gr);

		for (int x = 0; x < occupied.length; x++)
			for (int y = 0; y < occupied[0].length; y++)
				if (occupied[x][y] == 1) {
					// draw cell
					gr.setColor(tokenBorderColor[cellColor[x][y]]);
					gr.fillRect(x * 30, y * 30, 30, 30);
					gr.setColor(tokenBodyColor[cellColor[x][y]]);
					gr.fillRect(x * 30 + 2, y * 30 + 2, 28, 28);
				} else {
					// erase cell
					gr.setColor(java.awt.Color.BLACK);
					gr.fillRect(x * 30, y * 30, 30, 30);
				}
	}

	private static AudioClip backgroundMusic() {
		URL url = Tetris.class.getResource("theme.au");
		AudioClip clip = Applet.newAudioClip(url);
		clip.loop();
		return clip;
	}

	static void generateAllTokens() {
		for (int i = 0; i < Byte.MAX_VALUE; i++) {
			allTokens[i] = (byte) (7 * Math.random());
		}
	}

	int[][] occupied = new int[10][20];
	static byte[] allTokens = new byte[128];
	static byte indexOfCurrentToken = 0;
	// static int counter = 128;

	int[][] cellColor = new int[10][20];

	Color[] tokenBodyColor = { new Color(255, 0, 0), // L
			new Color(0, 255, 0), // S
			new Color(0, 0, 255), // Z
			new Color(127, 127, 127), // J
			new Color(187, 163, 60), // T
			new Color(194, 61, 167), // O
			new Color(255, 255, 255) // I
	};
	static int[][][] xRotationArray = {
			{ { 0, 0, 1, 2 }, { 0, 0, 0, 1 }, { 2, 0, 1, 2 }, { 0, 1, 1, 1 } }, // L
			{ { 0, 0, 1, 1 }, { 1, 2, 0, 1 }, { 0, 0, 1, 1 }, { 1, 2, 0, 1 } }, // S
			{ { 1, 1, 0, 0 }, { 0, 1, 1, 2 }, { 1, 1, 0, 0 }, { 0, 1, 1, 2 } }, // Z
			{ { 0, 1, 2, 2 }, { 0, 1, 0, 0 }, { 0, 0, 1, 2 }, { 1, 1, 0, 1 } }, // J
			{ { 1, 0, 1, 2 }, { 1, 0, 1, 1 }, { 0, 1, 1, 2 }, { 0, 0, 1, 0 } }, // T
			{ { 0, 1, 0, 1 }, { 0, 1, 0, 1 }, { 0, 1, 0, 1 }, { 0, 1, 0, 1 } }, // O
			{ { 0, 1, 2, 3 }, { 0, 0, 0, 0 }, { 0, 1, 2, 3 }, { 0, 0, 0, 0 } } // I
	};
	static int[][][] yRotationArray = {
			{ { 0, 1, 0, 0 }, { 0, 1, 2, 2 }, { 0, 1, 1, 1 }, { 0, 0, 1, 2 } }, // L
			{ { 0, 1, 1, 2 }, { 0, 0, 1, 1 }, { 0, 1, 1, 2 }, { 0, 0, 1, 1 } }, // S
			{ { 0, 1, 1, 2 }, { 0, 0, 1, 1 }, { 0, 1, 1, 2 }, { 0, 0, 1, 1 } }, // Z
			{ { 0, 0, 0, 1 }, { 0, 0, 1, 2 }, { 0, 1, 1, 1 }, { 0, 1, 2, 2 } }, // J
			{ { 0, 1, 1, 1 }, { 0, 1, 1, 2 }, { 0, 0, 1, 0 }, { 0, 1, 1, 2 } }, // T
			{ { 0, 0, 1, 1 }, { 0, 0, 1, 1 }, { 0, 0, 1, 1 }, { 0, 0, 1, 1 } }, // O
			{ { 0, 0, 0, 0 }, { 0, 1, 2, 3 }, { 0, 0, 0, 0 }, { 0, 1, 2, 3 } } // I

	};

	// javax.swing.JLabel nextLabelText = new javax.swing.JLabel("NEXT");

	void drawCell(int x, int y, int color) {
		occupied[x][y] = 1;
		cellColor[x][y] = color;
	}

	void eraseCell(int x, int y) {
		occupied[x][y] = 0;
	}

	void drawToken(int x, int y, int[] xArray, int[] yArray, int color) {
		for (int i = 0; i < 4; i++) {
			drawCell(x + xArray[i], y + yArray[i], color);

		}
	}

	void eraseToken(int x, int y, int[] xArray, int[] yArray) {
		for (int i = 0; i < 4; i++) {
			eraseCell(x + xArray[i], y + yArray[i]);
		}
	}

	public boolean isValidPosition(int x, int y, int tokenNumber,
			int rotationNumber) {
		int[] xArray = xRotationArray[tokenNumber][rotationNumber];
		int[] yArray = yRotationArray[tokenNumber][rotationNumber];

		for (int i = 0; i < 4; i++) // loops over the token
		{
			int xCell = x + xArray[i];
			int yCell = y + yArray[i];

			// range check
			if (xCell < 0)
				return false;
			if (xCell >= 10)
				return false;
			if (yCell < 0)
				return false;
			if (yCell >= 20)
				return false;

			// occupancy check
			if (occupied[xCell][yCell] == 1)
				return false;
		}
		return true;
	}

	public void clearCompleteRow(int[] completed) {
		for (int blink = 0; blink < 5; blink++) {
			for (int i = 0; i < completed.length; i++) {
				if (completed[i] == 1) {
					for (int x = 0; x < 10; x++) {

						occupied[x][i] = 1 - occupied[x][i]; // switches the
																// array row
																// from occupied
																// to vacant

					}
				}
			}
			repaint();// this makes it blink
			try {
				Thread.sleep(50);
			} catch (Exception ignore) {
			}
		}
	}

	public void shiftUp(int[] completed) {
		for (int row = 0; row < completed.length; row++) {
			if (completed[row] == 1) {
				for (int y = row; y < 18; y++) {
					for (int x = 0; x < 10; x++) {
						occupied[x][y] = occupied[x][y + 1];
					}
				}
			}
		}
	}

	void checkRowCompletionAndPlaySound() {
		boolean isCompleted = false;
		AudioClip destroyRowSounds[] = new AudioClip[9];

		URL url1 = Tetris.class.getResource("button-1.wav");
		AudioClip clip1 = Applet.newAudioClip(url1);

		URL url2 = Tetris.class.getResource("button-2.wav");
		AudioClip clip2 = Applet.newAudioClip(url2);

		URL url3 = Tetris.class.getResource("button-3.wav");
		AudioClip clip3 = Applet.newAudioClip(url3);

		URL url4 = Tetris.class.getResource("button-4.wav");
		AudioClip clip4 = Applet.newAudioClip(url4);

		URL url5 = Tetris.class.getResource("button-5.wav");
		AudioClip clip5 = Applet.newAudioClip(url5);

		URL url6 = Tetris.class.getResource("button-6.wav");
		AudioClip clip6 = Applet.newAudioClip(url6);

		URL url7 = Tetris.class.getResource("button-7.wav");
		AudioClip clip7 = Applet.newAudioClip(url7);

		URL url8 = Tetris.class.getResource("button-8.wav");
		AudioClip clip8 = Applet.newAudioClip(url8);

		URL url9 = Tetris.class.getResource("button-09.wav");
		AudioClip clip9 = Applet.newAudioClip(url9);

		destroyRowSounds[0] = clip1;
		destroyRowSounds[1] = clip2;
		destroyRowSounds[2] = clip3;
		destroyRowSounds[3] = clip4;
		destroyRowSounds[4] = clip5;
		destroyRowSounds[5] = clip6;
		destroyRowSounds[6] = clip7;
		destroyRowSounds[7] = clip8;
		destroyRowSounds[8] = clip9;

		int[] complete = new int[20];
		for (int y = 0; y < 20; y++) // 20 rows
		{
			int filledCell = 0;
			for (int x = 0; x < 10; x++) // 10 columns
			{
				if (occupied[x][y] == 1)
					filledCell++;
				if (filledCell == 10) // row completed
				{
					complete[y] = 1;
					isCompleted = true;
				}
			}
		}

		clearCompleteRow(complete);
		if (isCompleted) {
			Random rand = new Random();
			destroyRowSounds[rand.nextInt(destroyRowSounds.length)].play();

		}
		shiftUp(complete);

		addScore(complete);
	}

	void addScore(int[] complete) {
		int bonus = 10; // score for the first completed line
		for (int row = 0; row < complete.length; row++) {
			if (complete[row] == 1) {
				lineCompleted += 1;
				score += bonus;
				bonus *= 2; // double the bonus for every additional line
			}
		}

		// advance level for every 3 completed lines
		level = lineCompleted / 3;
		if (level > 30) {
			lineCompleted = 0;
			level = 0;
		} // MAX LEVEL

		scoreLabel.setText("SCORE : " + score);
		levelLabel.setText("LEVEL : " + level);
	}

	boolean gameOver = false;

	public void addRisingToken(int tokenNumber) {
		int x = 5, y = 18;
		int rotationNumber;
		if (indexOfCurrentToken == 127) {
			printYouWin();
		}
		// tokenNumber = (int) (7 * Math.random());
		// System.out.println(tokenNumber);// Kosyo - predefines initial
		// orientation
		switch (tokenNumber) {
		case 0:
			rotationNumber = 2;
			break;
		case 1:
			rotationNumber = 3;
			break;
		case 2:
			rotationNumber = 1; // ok
			break;
		case 3:
			rotationNumber = 2; // ok
			break;
		default:
			rotationNumber = 0;
			break;
		}

		int color = tokenNumber;
		int[] xArray = xRotationArray[tokenNumber][rotationNumber];
		int[] yArray = yRotationArray[tokenNumber][rotationNumber];

		nextLabel.setText("NEXT" + nextTokenLabelText());
		if (!isValidPosition(x, y, tokenNumber, rotationNumber)) {
			gameOver = true;
			drawToken(x, y, xArray, yArray, color);
			repaint();
			return;
		}

		drawToken(x, y, xArray, yArray, color);
		repaint();

		int delay = 50; // ms
		int frame = 0;
		boolean reachFloor = false;
		while (!reachFloor) {
			try {
				Thread.sleep(delay);
			} catch (Exception ignore) {
			}
			eraseToken(x, y, xArray, yArray);

			// add keyboard control
			if (leftPressed
					&& isValidPosition(x - 1, y, tokenNumber, rotationNumber))
				x -= 1;
			if (rightPressed
					&& isValidPosition(x + 1, y, tokenNumber, rotationNumber))
				x += 1;
			if (upPressed
					&& isValidPosition(x, y - 1, tokenNumber, rotationNumber))
				y -= 1;
			if (downPressed
					&& isValidPosition(x, y + 1, tokenNumber, rotationNumber))
				y += 1;
			if (spacePressed
					&& isValidPosition(x, y, tokenNumber,
							(rotationNumber + 1) % 4)) {
				rotationNumber = (rotationNumber + 1) % 4;
				xArray = xRotationArray[tokenNumber][rotationNumber];
				yArray = yRotationArray[tokenNumber][rotationNumber];
				spacePressed = false;
			}

			int f = 20 - level; // fall for every 31 frames, this value is
								// decreased when level up, each frame is 50ms
			if (frame % f == 0)
				y -= 1;
			if (!isValidPosition(x, y, tokenNumber, rotationNumber)) // reached
																		// floor
			{
				reachFloor = true;
				y += 1; // restore position
				// Kosyo
				tokensLeftLabel.setText("TOKENS LEFT : "
						+ (126 - indexOfCurrentToken));
			}
			drawToken(x, y, xArray, yArray, color);
			repaint();
			frame++;
		}

	}

	public void printGameOver() {
		javax.swing.JLabel gameOverLabel = new javax.swing.JLabel("GAME OVER");
		gameOverLabel.setBounds(300, 300, 100, 30);
		// Dinko
		URL url = Tetris.class.getResource("Buzzer.au");
		AudioClip clip = Applet.newAudioClip(url);
		clip.play();
		add(gameOverLabel);
		repaint();
	}

	public void printYouWin() { // Kosyo

		javax.swing.JLabel youWinLabel = new javax.swing.JLabel("YOU WIN");
		youWinLabel.setBounds(300, 300, 100, 30);
		// Dinko
		URL url = Tetris.class.getResource("Buzzer.au");
		AudioClip clip = Applet.newAudioClip(url);
		clip.play();
		add(youWinLabel);
		repaint();
		gameOver = true;
	}

	public void keyPressed(java.awt.event.KeyEvent event) {
		// System.out.println(event);
		if (event.getKeyCode() == 37) // left arrow
		{
			leftPressed = true;
		}
		if (event.getKeyCode() == 39) // right arrow
		{
			rightPressed = true;
		}
		if (event.getKeyCode() == 40) // down arrow
		{
			downPressed = true;
		}
		if (event.getKeyCode() == 38) // up arrow
		{
			upPressed = true;
		}
		if (event.getKeyCode() == 32) // space
		{
			spacePressed = true;
		}
	}

	public void keyReleased(java.awt.event.KeyEvent event) {
		// System.out.println(event);

		if (event.getKeyCode() == 37) // left arrow
		{
			leftPressed = false;
		}
		if (event.getKeyCode() == 39) // right arrow
		{
			rightPressed = false;
		}
		if (event.getKeyCode() == 40) // down arrow
		{
			downPressed = false;
		}
		if (event.getKeyCode() == 38) // up arrow
		{
			upPressed = false;
		}
		if (event.getKeyCode() == 32) // space
		{
			spacePressed = false;
		}

	}

	public void keyTyped(java.awt.event.KeyEvent event) {
		// System.out.println(event);
	}

	javax.swing.JLabel scoreLabel = new javax.swing.JLabel("SCORE : 0");
	javax.swing.JLabel completedLinesLabel = new javax.swing.JLabel("LINES : 0"); // Kosyo
	javax.swing.JLabel levelLabel = new javax.swing.JLabel("LEVEL : 0");
	javax.swing.JLabel tokensLeftLabel = new javax.swing.JLabel(
			"TOKENS LEFT : 127");
	javax.swing.JLabel timeLabel = new javax.swing.JLabel("TIME : 0"); // Ani
	javax.swing.JLabel nextLabel = new javax.swing.JLabel("NEXT ");

	int score = 0; // score
	int lineCompleted = 0; // number of lines completed
	int level = 0;
	boolean leftPressed = false;
	boolean rightPressed = false;
	boolean downPressed = false;
	boolean spacePressed = false;
	boolean upPressed = false;

	Color[] tokenBorderColor = { new Color(0, 0, 0), // L
			new Color(0, 0, 0), // S
			new Color(0, 0, 0), // Z
			new Color(0, 0, 0), // J
			new Color(0, 0, 0), // T
			new Color(0, 0, 0), // O
			new Color(0, 0, 0) // I
	};

	String nextTokenLabelText() { // this one changes the color of the
		// label for next token
		Font nextPieceFont = new Font("Verdana", Font.BOLD, 25);// .BOLD();
		nextLabel.setFont(nextPieceFont);
		int currentToken = allTokens[indexOfCurrentToken + 1];
		String nextToken = "";
		switch (currentToken) {
		case 0:
			nextLabel.setForeground(tokenBodyColor[0]);
			// nextToken = "L-shaped";
			break;
		case 1:
			nextLabel.setForeground(tokenBodyColor[1]);
			// nextToken = "S-shaped";
			break;
		case 2:
			nextLabel.setForeground(tokenBodyColor[2]);
			// nextToken = "Z-shaped";
			break;
		case 3:
			nextLabel.setForeground(tokenBodyColor[3]);
			// nextToken = "J-shaped";
			break;
		case 4:
			nextLabel.setForeground(tokenBodyColor[4]);
			// nextToken = "T-shaped";
			break;
		case 5:
			nextLabel.setForeground(tokenBodyColor[5]);
			// nextToken = "Square";
			break;
		case 6:
			nextLabel.setForeground(tokenBodyColor[6]);
			// nextToken = "Bar";
			break;
		}
		return nextToken;
	}
}
