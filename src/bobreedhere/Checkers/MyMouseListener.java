package bobreedhere.Checkers;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseListener implements MouseListener {

	Piece[][] pieces;
	private CheckersGui checkersGui;
	private boolean pieceSelected;
	private Piece selectedPiece;
	private int sX, sY;
	private int squareSize;
	private int boardSize;
	private Point jumpPoint;

	public MyMouseListener(Piece[][] pieces, CheckersGui chessGui) {
		super();
		this.pieces = pieces;
		this.checkersGui = chessGui;
		squareSize = checkersGui.getSquareSize();
		boardSize = checkersGui.getBoardSize();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("CLICK");
		// System.out.println(pieces[e.getPoint().x/squareSize][e.getPoint().y/squareSize]);
		// System.out.println(e.getPoint().x/squareSize+" : "+e.getPoint().y/squareSize);
		System.out.println(selectedPiece);
		// If a piece isnt selected
		if (!pieceSelected) {

			System.out.println("NADA");
			// If the selected square has a piece there
			if (pieces[e.getPoint().x / squareSize][e.getPoint().y / squareSize] != null) {
				// If the piece attempted to be selected is the right colour
				if (pieces[e.getPoint().x / squareSize][e.getPoint().y / squareSize].getColor() == checkersGui.getTurn()) {

					// Selects the chosen piece
					pieceSelected = true;
					selectedPiece = pieces[e.getPoint().x / squareSize][e.getPoint().y / squareSize];
					sX = e.getPoint().x / squareSize;
					sY = e.getPoint().y / squareSize;
					checkersGui.setSelected(new Point(sX * squareSize, sY * squareSize));
				}
			}
			// If a piece is already selected
		} else {
			System.out.println("EYO");
			System.out.println(pieces[e.getPoint().x / squareSize][e.getPoint().y / squareSize]);
			if (selectedPiece.getColor() == checkersGui.getTurn()) {

				// variable contains wether moveValid() says the move works
				int temp = moveValid(selectedPiece, new Point(sX, sY), pieces[e.getPoint().x / squareSize][e.getPoint().y / squareSize], new Point(e.getPoint().x / squareSize, e.getPoint().y / squareSize));
				// If the move is valid and a regular jump
				if (temp == 0) {
					System.out.println("NOT JUMPPPP");
					// sets the new piece to the selected piece
					pieces[e.getPoint().x / squareSize][e.getPoint().y / squareSize] = selectedPiece;
					// destroys the original piece
					pieces[sX][sY] = null;
					// If the selected square contains a black piece
					if (pieces[e.getPoint().x / squareSize][e.getPoint().y / squareSize].getColor() == 0) {
						// If the piece should be kinged
						if (e.getPoint().y / squareSize == boardSize / squareSize - 1) {
							// Kings the piece
							pieces[e.getPoint().x / squareSize][e.getPoint().y / squareSize].setKinged();
						}
						// If the selected square contains a white piece
					} else {
						// If the piece should be kinged
						if (e.getPoint().y / squareSize == 0) {
							// Kings the piece
							pieces[e.getPoint().x / squareSize][e.getPoint().y / squareSize].setKinged();
						}
					}
					// If the move is valid and a jump
				} else if (temp == 1) { // JUMP
					System.out.println("JUMPPPPP");
					// sets the selected square to the stored piece
					pieces[e.getPoint().x / squareSize][e.getPoint().y / squareSize] = selectedPiece;
					// clears the square the piece is from
					pieces[sX][sY] = null;
					// Counts captured pieces of each colour
					if (pieces[jumpPoint.x][jumpPoint.y].getColor() == 0)
						checkersGui.addCapturedBlack(pieces[jumpPoint.x][jumpPoint.y]);
					else
						checkersGui.addCapturedWhite(pieces[jumpPoint.x][jumpPoint.y]);
					// removes jumped piece
					pieces[jumpPoint.x][jumpPoint.y] = null;
					// kinging stuff and things
					if (pieces[e.getPoint().x / squareSize][e.getPoint().y / squareSize].getColor() == 0) {
						if (e.getPoint().y / squareSize == boardSize / 100 - 1) {
							pieces[e.getPoint().x / squareSize][e.getPoint().y / squareSize].setKinged();
						}
					} else {
						if (e.getPoint().y / squareSize == 0) {
							pieces[e.getPoint().x / squareSize][e.getPoint().y / squareSize].setKinged();
						}
					}
				}
			}
			pieceSelected = false;
			System.out.println("SWITCH");
			checkersGui.switchTurn();

			selectedPiece = null;
			checkersGui.setSelected(new Point(-1, -1));
		}
		// System.out.println(checkersGui.pieces[e.getPoint().x/squareSize][e.getPoint().y/squareSize]);

		checkersGui.repaint();
	}

	private int moveValid(Piece sPiece, Point sPoint, Piece nPiece, Point nPoint) {
			if (nPiece == null) {
				if (sPiece.isKinged()) {
					if (nPoint.x == sPoint.x - 1 && nPoint.y == sPoint.y + 1 || nPoint.x == sPoint.x + 1 && nPoint.y == sPoint.y + 1 || nPoint.x == sPoint.x - 1 && nPoint.y == sPoint.y - 1 || nPoint.x == sPoint.x + 1 && nPoint.y == sPoint.y - 1) {
						System.out.println(true);
						return 0;
					} else if (nPoint.x == sPoint.x - 2 && nPoint.y == sPoint.y + 2 || nPoint.x == sPoint.x + 2 && nPoint.y == sPoint.y + 2 || nPoint.x == sPoint.x - 2 && nPoint.y == sPoint.y - 2 || nPoint.x == sPoint.x + 2 && nPoint.y == sPoint.y - 2) {
						if (pieces[sPoint.x - 1][sPoint.y + 1] != null) {
							if (pieces[sPoint.x - 1][sPoint.y + 1].getColor() != pieces[sPoint.x][sPoint.y].getColor()) {
								if (nPoint.x == sPoint.x - 2 && nPoint.y == sPoint.y + 2)
									jumpPoint = new Point(sPoint.x - 1, sPoint.y + 1);
								else if (nPoint.x == sPoint.x + 2 && nPoint.y == sPoint.y + 2)
									jumpPoint = new Point(sPoint.x + 1, sPoint.y + 1);
								else if (nPoint.x == sPoint.x - 2 && nPoint.y == sPoint.y - 2)
									jumpPoint = new Point(sPoint.x - 1, sPoint.y - 1);
								else if (nPoint.x == sPoint.x + 2 && nPoint.y == sPoint.y - 2)
									jumpPoint = new Point(sPoint.x + 1, sPoint.y - 1);

								System.out.println("jump " + jumpPoint);
								return 1;
							}
						} else if (pieces[sPoint.x + 1][sPoint.y + 1] != null) {
							if (pieces[sPoint.x + 1][sPoint.y + 1].getColor() != pieces[sPoint.x][sPoint.y].getColor()) {
								if (nPoint.x == sPoint.x - 2 && nPoint.y == sPoint.y + 2)
									jumpPoint = new Point(sPoint.x - 1, sPoint.y + 1);
								else if (nPoint.x == sPoint.x + 2 && nPoint.y == sPoint.y + 2)
									jumpPoint = new Point(sPoint.x + 1, sPoint.y + 1);
								else if (nPoint.x == sPoint.x - 2 && nPoint.y == sPoint.y - 2)
									jumpPoint = new Point(sPoint.x - 1, sPoint.y - 1);
								else if (nPoint.x == sPoint.x + 2 && nPoint.y == sPoint.y - 2)
									jumpPoint = new Point(sPoint.x + 1, sPoint.y - 1);

								System.out.println("jump " + jumpPoint);
								return 1;
							}
						} else if (pieces[sPoint.x - 1][sPoint.y + 1] != null) {
							if (pieces[sPoint.x - 1][sPoint.y + 1].getColor() != pieces[sPoint.x][sPoint.y].getColor()) {
								if (nPoint.x == sPoint.x - 2 && nPoint.y == sPoint.y + 2)
									jumpPoint = new Point(sPoint.x - 1, sPoint.y + 1);
								else if (nPoint.x == sPoint.x + 2 && nPoint.y == sPoint.y + 2)
									jumpPoint = new Point(sPoint.x + 1, sPoint.y + 1);
								else if (nPoint.x == sPoint.x - 2 && nPoint.y == sPoint.y - 2)
									jumpPoint = new Point(sPoint.x - 1, sPoint.y - 1);
								else if (nPoint.x == sPoint.x + 2 && nPoint.y == sPoint.y - 2)
									jumpPoint = new Point(sPoint.x + 1, sPoint.y - 1);

								System.out.println("jump " + jumpPoint);
								return 1;
							}
						} else if (pieces[sPoint.x + 1][sPoint.y + 1] != null) {
							if (pieces[sPoint.x + 1][sPoint.y + 1].getColor() != pieces[sPoint.x][sPoint.y].getColor()) {
								if (nPoint.x == sPoint.x - 2 && nPoint.y == sPoint.y + 2)
									jumpPoint = new Point(sPoint.x - 1, sPoint.y + 1);
								else if (nPoint.x == sPoint.x + 2 && nPoint.y == sPoint.y + 2)
									jumpPoint = new Point(sPoint.x + 1, sPoint.y + 1);
								else if (nPoint.x == sPoint.x - 2 && nPoint.y == sPoint.y - 2)
									jumpPoint = new Point(sPoint.x - 1, sPoint.y - 1);
								else if (nPoint.x == sPoint.x + 2 && nPoint.y == sPoint.y - 2)
									jumpPoint = new Point(sPoint.x + 1, sPoint.y - 1);

								System.out.println("jump " + jumpPoint);
								return 1;
							}
						}

					}
				} else {
					if (sPiece.getColor() == 0) {
						if (nPoint.x == sPoint.x - 1 && nPoint.y == sPoint.y + 1 || nPoint.x == sPoint.x + 1 && nPoint.y == sPoint.y + 1) {
							System.out.println(true);
							return 0;
						} else if (nPoint.x == sPoint.x - 2 && nPoint.y == sPoint.y + 2 || nPoint.x == sPoint.x + 2 && nPoint.y == sPoint.y + 2) {
							if (pieces[sPoint.x - 1][sPoint.y + 1] != null) {
								if (pieces[sPoint.x - 1][sPoint.y + 1].getColor() != pieces[sPoint.x][sPoint.y].getColor()) {// ||
									if (nPoint.x == sPoint.x - 2 && nPoint.y == sPoint.y + 2)
										jumpPoint = new Point(sPoint.x - 1, sPoint.y + 1);
									else if (nPoint.x == sPoint.x + 2 && nPoint.y == sPoint.y + 2)
										jumpPoint = new Point(sPoint.x + 1, sPoint.y + 1);

									System.out.println("jump" + jumpPoint);
									return 1;
								}
							} else if (pieces[sPoint.x + 1][sPoint.y + 1] != null) {
								if (pieces[sPoint.x + 1][sPoint.y + 1].getColor() != pieces[sPoint.x][sPoint.y].getColor()) {
									if (nPoint.x == sPoint.x - 2 && nPoint.y == sPoint.y + 2)
										jumpPoint = new Point(sPoint.x - 1, sPoint.y + 1);
									else if (nPoint.x == sPoint.x + 2 && nPoint.y == sPoint.y + 2)
										jumpPoint = new Point(sPoint.x + 1, sPoint.y + 1);

									System.out.println("jump" + jumpPoint);
									return 1;
								}
							}
						}
					} else {
						if (nPoint.x == sPoint.x - 1 && nPoint.y == sPoint.y - 1 || nPoint.x == sPoint.x + 1 && nPoint.y == sPoint.y - 1) {
							System.out.println(true);
							return 0;
						} else if (nPoint.x == sPoint.x - 2 && nPoint.y == sPoint.y - 2 || nPoint.x == sPoint.x + 2 && nPoint.y == sPoint.y - 2) {
							if (pieces[sPoint.x - 1][sPoint.y - 1] != null) {
								if (pieces[sPoint.x - 1][sPoint.y - 1].getColor() != pieces[sPoint.x][sPoint.y].getColor()) {
									if (nPoint.x == sPoint.x - 2 && nPoint.y == sPoint.y - 2)
										jumpPoint = new Point(sPoint.x - 1, sPoint.y - 1);
									else if (nPoint.x == sPoint.x + 2 && nPoint.y == sPoint.y - 2)
										jumpPoint = new Point(sPoint.x + 1, sPoint.y - 1);

									System.out.println("jump" + jumpPoint);
									return 1;
								}
							}
							if (pieces[sPoint.x + 1][sPoint.y - 1] != null) {
								if (pieces[sPoint.x + 1][sPoint.y - 1].getColor() != pieces[sPoint.x][sPoint.y].getColor()) {
									if (nPoint.x == sPoint.x - 2 && nPoint.y == sPoint.y - 2)
										jumpPoint = new Point(sPoint.x - 1, sPoint.y - 1);
									else if (nPoint.x == sPoint.x + 2 && nPoint.y == sPoint.y - 2)
										jumpPoint = new Point(sPoint.x + 1, sPoint.y - 1);

									System.out.println("jump" + jumpPoint);
									return 1;
								}
							}
						}
					}
				}

			}
		
		System.out.println(false);
		return -1;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
