package bobreedhere.Checkers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CheckersGui extends JPanel {
	// private List<Piece> Pieces = new ArrayList<Piece>();
	private Piece[][] pieces = new Piece[8][8];
	BufferedImage board = null, crown = null;
	
	private static final int boardDimensions = 8;
	private static final int boardSize = 800;
	private static final int squareSize = boardSize / boardDimensions;
	private List<Piece> whiteCaptured = new ArrayList<Piece>();
	private List<Piece> blackCaptured = new ArrayList<Piece>();
	private int turn = 1;
	
	public CheckersGui() {

		for (int i = 0; i < boardDimensions; i++) {
			if (i % 2 == 0)
				pieces[i][1] = new Piece(0);
			else
				pieces[i][0] = new Piece(0);
		}
		for (int i = 0; i < boardDimensions; i++) {
			if (i % 2 == 0)
				pieces[i][7] = new Piece(1);
			else
				pieces[i][6] = new Piece(1);
		}

		try {
			board = ImageIO.read(new File("src/bobreedhere/Checkers/assets/board3.png"));
			crown = ImageIO.read(new File("src/bobreedhere/Checkers/assets/crown.png"));
		} catch (IOException e) {
		}

		// create application frame and set visible
		//
		JFrame f = new JFrame();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(this);
		f.setResizable(true);
		f.setIconImage(crown);
		System.out.println(System.getProperty("os.name")=="Windows 7");
		if(System.getProperty("os.name")=="Windows 7")
			f.setSize(boardSize+16, boardSize+38);
		else 
			f.setSize(boardSize + 18, boardSize + 47);
			
		f.setTitle("Checkers");
		// add mouse listeners to enable drag and drop
		//
		MyMouseListener listener = new MyMouseListener(this.pieces, this);
		this.addMouseListener(listener);

	}

	public Point point = new Point(-1, -1);

	public void setSelected(Point select) {
		point = select;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(board, 0, 0, boardSize, boardSize, this);

		if (point.getX() != -1) {
			Color c = new Color(255, 0, 0, 100);
			g.setColor(c);
			g.fillRect(point.x, point.y, squareSize, squareSize);
		}

		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				// System.out.print(pieces[i][j]);
				if (pieces[i][j] != null) {
					if (pieces[i][j].getColor() == 0)
						g.setColor(Color.BLACK);
					else
						g.setColor(Color.WHITE);

					g.fillOval(i * squareSize, j * squareSize, squareSize, squareSize);

					if (pieces[i][j].isKinged())
						g.drawImage(crown, i * squareSize, j * squareSize, squareSize, squareSize, this);
				}
			}
			// System.out.println();
		}
		g.setColor(Color.BLACK);
		g.drawString("Whites captured: "+whiteCaptured.size(), 0, boardSize+10);
		g.drawString("Blacks captured: "+blackCaptured.size(), 0, boardSize+20);
	}

	public static void main(String args[]) {
		new CheckersGui();

	}

	public int getSquareSize() {
		return squareSize;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void addCapturedWhite(Piece p) {
		whiteCaptured.add(p);
	}

	public void addCapturedBlack(Piece p) {
		blackCaptured.add(p);
	}
	public void switchTurn(){
		if(turn==0) turn=1;
		else turn=0;
	}
	public int getTurn(){
		return turn;
	}
}
