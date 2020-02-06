package com.univ.chess;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.univ.chess.piece.*;

public class Board {
	
	private JPanel chessBoard;
	private final JPanel gui = new JPanel(new BorderLayout(3, 3));
	private Cell[][] chessBoardSquares = new Cell[8][8];
	private static final String COLS = "ABCDEFGH";
	
	private Player player1;
	private Player player2;
	private Color turn;
	
	private Cell cellSelected = null;
	
	private Stack<History> histories = new Stack<History>(); 
	
	public Board(Player p1, Player p2) {
		this.player1 = p1;
		this.player2 = p2;
		this.turn = this.player1.getColor();
		
		create();
		setupCell();
		try {
			setupPiece();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JPanel getGui() {
		return this.gui;
	}
	
	public void setupGUI() {
		Runnable r = new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame("Chess");
                f.add(Board.this.gui);
                // Ensures JVM closes after frame(s) closed and
                // all non-daemon threads are finished
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                // See https://stackoverflow.com/a/7143398/418556 for demo.
                f.setLocationByPlatform(true);
        
                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        // Swing GUIs should be created and updated on the EDT
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency
        SwingUtilities.invokeLater(r);
	}

	public void create() {
		chessBoard = new JPanel(new GridLayout(0, 9)) {

            /**
             * Override the preferred size to return the largest it can, in
             * a square shape.  Must (must, must) be added to a GridBagLayout
             * as the only component (it uses the parent as a guide to size)
             * with no GridBagConstaint (so it is centered).
             */
            @Override
            public final Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                Dimension prefSize = null;
                Component c = getParent();
                if (c == null) {
                    prefSize = new Dimension(
                            (int)d.getWidth(),(int)d.getHeight());
                } else if (c!=null &&
                        c.getWidth()>d.getWidth() &&
                        c.getHeight()>d.getHeight()) {
                    prefSize = c.getSize();
                } else {
                    prefSize = d;
                }
                int w = (int) prefSize.getWidth();
                int h = (int) prefSize.getHeight();
                // the smaller of the two sizes
                int s = (w>h ? h : w);
                return new Dimension(s,s);
            }
        };
        
        chessBoard.setBorder(new CompoundBorder(new EmptyBorder(8,8,8,8), new LineBorder(Color.BLACK)));
        
        // Set the background
        chessBoard.setBackground(Color.WHITE);
        JPanel boardConstrain = new JPanel(new GridBagLayout());
        boardConstrain.add(chessBoard);
        gui.add(boardConstrain);
	}
	
	public void setupCell() {
		// Create the chess board squares
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int i = 0; i < chessBoardSquares.length; i++) {
            for (int j = 0; j < chessBoardSquares.length; j++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.BLACK);
                }
                
                Cell cell = new Cell(b, i, j, this);
                chessBoardSquares[i][j] = cell;
            }
        }
        
        // ...
        /*
         * fill the chess board
         */
        chessBoard.add(new JLabel(""));
        // fill the top row
        for (int i = 0; i < 8; i++) {
            chessBoard.add(new JLabel(COLS.substring(i, i + 1), SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (j) {
                    case 0:
                    	// set number of column
                        chessBoard.add(new JLabel("" + (9-(i + 1)), SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[j][i].getCell());
                }
            }
        }
	}
	
	public void setupPiece() throws MalformedURLException {
		PieceFactory factory = new PieceFactory();
		// set up the black pawn
        for (int i = 0; i < 8; i++) {
        	Piece piece = factory.getPiece("PAWN", i, 1, Color.BLACK);
            chessBoardSquares[i][1].setPiece(piece);
        }
        
        // setup the another black piece
        for (int i = 0; i < 8; i++) {
        	if (i == 0 || i == 7) {
        		Piece piece = factory.getPiece("ROCK", i, 0, Color.BLACK);
                chessBoardSquares[i][0].setPiece(piece);
        	}
        	if (i == 1 || i == 6) {
        		Piece piece = factory.getPiece("KNIGHT", i, 0, Color.BLACK);
                chessBoardSquares[i][0].setPiece(piece);
        	}
        	if (i == 2 || i == 5) {
        		Piece piece = factory.getPiece("BISHOP", i, 0, Color.BLACK);
                chessBoardSquares[i][0].setPiece(piece);
        	}
        	if (i == 3) {
        		Piece piece = factory.getPiece("QUEEN", i, 0, Color.BLACK);
                chessBoardSquares[i][0].setPiece(piece);
        	}
        	if (i == 4) {
        		Piece piece = factory.getPiece("KING", i, 0, Color.BLACK);
                chessBoardSquares[i][0].setPiece(piece);
        	}
        }
        
        // set up the white pawn
        for (int i = 0; i < 8; i++) {
        	Piece piece = factory.getPiece("PAWN", i, 6, Color.WHITE);
            chessBoardSquares[i][6].setPiece(piece);
        }
        
        // setup the another white piece
        for (int i = 0; i < 8; i++) {
        	if (i == 0 || i == 7) {
        		Piece piece = factory.getPiece("ROCK", i, 7, Color.WHITE);
                chessBoardSquares[i][7].setPiece(piece);
        	}
        	if (i == 1 || i == 6) {
        		Piece piece = factory.getPiece("KNIGHT", i, 7, Color.WHITE);
                chessBoardSquares[i][7].setPiece(piece);
        	}
        	if (i == 2 || i == 5) {
        		Piece piece = factory.getPiece("BISHOP", i, 7, Color.WHITE);
        		chessBoardSquares[i][7].setPiece(piece);
        	}
        	if (i == 3) {
        		Piece piece = factory.getPiece("QUEEN", i, 7, Color.WHITE);
                chessBoardSquares[i][7].setPiece(piece);
        	}
        	if (i == 4) {
        		Piece piece = factory.getPiece("KING", i, 7, Color.WHITE);
                chessBoardSquares[i][7].setPiece(piece);
        	}
        }
	}
	
	public Cell getCellSelected() {
		return this.cellSelected;
	}
	
	public void setCellSelected(Cell c) {
		this.cellSelected = c;
	}

	public void movePiece(int x, int y, int m, int n) {
		// From (x, y) to (m, n)
		Cell c1 = chessBoardSquares[x][y];
		Cell c2 = chessBoardSquares[m][n];
		
		if (c1.getPiece() != null) { 
			// Call function move of Cell
			if (c1.getPiece().getPieceColor() == this.turn) {  // --> Check turn
				
				// Move piece
				if(c1.move(c2)) {
					this.updateTurn();
					
					// Check end after moved
					this.isEnd();
				}
			}
		}
	}
	
	public int evaluateBoard(Color color) { // -> Calculate the value of piece follow color
		int bestValue = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Cell tmp = this.chessBoardSquares[i][j];
				if (tmp.getPiece() != null) {
					if (tmp.getPiece().getPieceColor() == color) {
						bestValue = bestValue + tmp.getPiece().getValue();
					} else {
						bestValue = bestValue - tmp.getPiece().getValue();
					}
				}
			}
		}
		return bestValue;
	}
	
	public void movePieceSimulate(int x, int y, int m, int n) {
		// From (x, y) to (m, n)
		Cell c1 = chessBoardSquares[x][y];
		Cell c2 = chessBoardSquares[m][n];
		
		if (c1.getPiece() != null) { 
			// Call function move of Cell
			if (c1.getPiece().getPieceColor() == this.turn) {  // --> Check turn
				
				// Save previous step
				Cell cellOld1 = c1; // --> Save old position
				Cell cellOld2 = c2;
				
				// --> Save old piece
				Piece pieceOld1 = null;
				Piece pieceOld2 = null;
				if (c1.getPiece() != null) {
					try {
						pieceOld1 = (Piece)c1.getPiece().clone();
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					pieceOld1 = null;
				}
				
				if (c2.getPiece() != null) {
					try {
						pieceOld2 = (Piece)c2.getPiece().clone();
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					pieceOld2 = null;
				}
				
				History prevStep = new History(cellOld1, cellOld2, pieceOld1, pieceOld2);
				this.histories.push(prevStep); // Push to stack
				
				// Move piece
				if(c1.moveSimulate(c2)) {
					this.updateTurn();
					
					// Check end after moved
					this.isEnd();
				}
			}
		}
	}
	
	public void undo() {
		// Change turn		
		updateTurn();
		
		// Undo position of piece
		History tmp = this.histories.pop();
		this.chessBoardSquares[tmp.getCellOld1().getCol()][tmp.getCellOld1().getRow()].setPieceSimulate(tmp.getPieceOld1());
		this.chessBoardSquares[tmp.getCellOld2().getCol()][tmp.getCellOld2().getRow()].setPieceSimulate(tmp.getPieceOld2());
	}
	
	public void updateTurn() {
		if (this.turn == this.player1.getColor()) {
			this.turn = this.player2.getColor();
		} else {
			this.turn = this.player1.getColor();
		}
	}
	
	public Color getTurn() {
		return this.turn;
	}
	
	public void removePiece() {
		
	}
	
	public Cell[][] getChessBoardSquares() {
		return this.chessBoardSquares;
	}
	
	public ArrayList<Move> posibleMove() {
		ArrayList<Move> moves = new ArrayList<Move>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (this.chessBoardSquares[i][j].getPiece() != null) {
					if (this.chessBoardSquares[i][j].getPiece().getPieceColor() == this.turn) {
						ArrayList<Move> tmp = this.chessBoardSquares[i][j].posibleMove();
						if (tmp != null) { // Check for null exception
							moves.addAll(tmp);
						}
					}
				}
			}
		}
		return moves;
	}
	
	public boolean hasKing() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Cell tmp = this.chessBoardSquares[i][j];
				if (tmp.getPiece() != null) {
					if (tmp.getPiece().getPieceColor() == this.turn) {
						if (tmp.getPiece().getValue() == 1000) { // -> King
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean isEnd() {
		ArrayList<Move> tmp = this.posibleMove();
		if (tmp == null) {
			System.out.println("Array: " + tmp);
		}
		if (tmp.size() == 0 || !this.hasKing()) {
			return true;
		}
		return false;
	}

}
