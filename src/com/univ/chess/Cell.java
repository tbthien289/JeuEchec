package com.univ.chess;

import java.awt.Image;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.univ.chess.piece.Queen;

public class Cell {

	private JButton cell = new JButton();
	private int row;
	private int col;
	private Piece piece;
	
	private Board board;
	
	public Cell(JButton cell, int col, int row, Board board) {
		this.cell = cell;
		this.col = col;
		this.row = row;
		this.piece = null;
		this.board = board;
		setCellMouseEvent();
	}
	
	public void setCellMouseEvent() {
		this.cell.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				Cell cell = Cell.this.board.getCellSelected();
				if (cell == null) {
					// Check current cell have piece or not
					if (Cell.this.piece != null) {
						Cell.this.board.setCellSelected(Cell.this);
					}
				} else {
					if (cell.getCol() != Cell.this.col || cell.getRow() != Cell.this.row) { // Check not himself
						Cell.this.board.setCellSelected(null);
						Cell.this.board.movePiece(cell.getCol(), cell.getRow(), Cell.this.col, Cell.this.row);
//						try {
//							Thread.sleep(100);
//						} catch (InterruptedException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
					} else {
						Cell.this.board.setCellSelected(null);
					}
				}
				
				if (Cell.this.board.getCellSelected() != null) {
					System.out.println("Clicked");
			    	System.out.print(Cell.this.board.getCellSelected().getCol());
			    	System.out.print(" - ");
			    	System.out.print(Cell.this.board.getCellSelected().getRow());
			    	System.out.println("");
				}
			}
		});
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
		
		// Apply icon for cell
		if (piece != null) {
			this.setIcon();
		} else {
			this.removeIcon();
		}
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public boolean canMove(Cell c2) {
		// 1. Exist piece or not
		if (this.piece == null) {
			return false;
		}
		// 2. Movement of piece valid or no?
		if (!this.piece.canMove(c2)) {
			return false;
		}
		
		return true;
	}
	
	public boolean move(Cell c2) {
		// Check some condition here
		if (this.canMove(c2)) {
			if (this.piece.getValue() == 10) { // Promotion Pawn
				if (this.piece.getPieceColor() == Color.WHITE && c2.row == 0) {
					this.piece = new Queen(this.col, this.row, Color.WHITE);
				} else if (this.piece.getPieceColor() == Color.BLACK && c2.row == 7) {
					this.piece = new Queen(this.col, this.row, Color.BLACK);
				}
			}
			// Change position piece
			this.piece.setCol(c2.col);
			this.piece.setRow(c2.row);
			
			// Change image piece
			c2.setPiece(this.piece);
			this.setPiece(null);
			
			return true;
		} else {
			System.out.println("Cannot move!");
			return false;
		}
	}
	
	public Piece getPiece() {
		return this.piece;
	}
	
	public JButton getCell() {
		return this.cell;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public void setIcon() {
		this.cell.setIcon(new ImageIcon(this.piece.getImage()));
	}
	
	public void removeIcon() {
		this.cell.setIcon(null);
	}
	
	public Cell[][] getChessBoardSquare() {
		return this.board.getChessBoardSquares();
	}
	
	public ArrayList<Move> posibleMove() {
		ArrayList<Move> moves = new ArrayList<Move>();
		Cell[][] chessBoard = this.board.getChessBoardSquares();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				boolean tmp = false;
				if (this.piece != null) { // Some condition to evade null exception
					tmp = this.piece.canMove(chessBoard[i][j]);
				}
				if (tmp) {
					Move newMove = null;
					if (this.piece != null) { // Some condition to evade null exception
						newMove = new Move(this.piece.getCol(), this.piece.getRow(), i, j);
					}
					if (newMove != null) {
						moves.add(newMove);
					}
				}
			}
		}
		return moves;
	}
	
	// use for case AI
	public void setPieceSimulate(Piece piece) {
		this.piece = piece; // -> Change change value of piece dont set img
	}
	
	public boolean moveSimulate(Cell c2) {
		// Check some condition here
		if (this.canMove(c2)) {
			// Change position piece
			this.piece.setCol(c2.col);
			this.piece.setRow(c2.row);
			
			// Change image piece
			c2.setPieceSimulate(this.piece);
			this.setPieceSimulate(null);
			
			return true;
		} else {
			System.out.println("Cannot move!");
			return false;
		}
	}
	
}
