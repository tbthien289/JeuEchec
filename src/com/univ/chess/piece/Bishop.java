package com.univ.chess.piece;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.univ.chess.Cell;
import com.univ.chess.Piece;

public class Bishop extends Piece {

	public Bishop(int col, int row, Color pieceColor) {
		super(col, row, pieceColor);
		this.value = 30;
		// TODO Auto-generated constructor stub
		setupImagePiece();
	}

	public void setupImagePiece() {
		int i = 0;
		int j = 4;
		if (pieceColor == Color.BLACK) {
			i = 0;
		} else if (pieceColor == Color.WHITE) {
			i = 1;
		}
		
		URL url = null;
		File file = new File("E:\\UAPV\\Application Conception\\Chess\\src\\com\\univ\\chess\\memI0.png");
		try {
			url = file.toURI().toURL();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        try {
        	BufferedImage bi = ImageIO.read(url);
        	this.pieceImage = bi.getSubimage(j * 64, i * 64, 64, 64);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public boolean conditionMove(int x, int y, Cell[][] chessBoardSquare) { // Move to x, y
		int dCol = x - this.col;
		int dRow = y - this.row;
		if (dCol > 0 && dRow > 0 && dCol == dRow) { // Case Right-Bottom
			int i = this.col + 1;
			int j = this.row + 1;
			while (i <= x) {
				// Check case: Exist the piece in path or not
				if (chessBoardSquare[i][j].getPiece() != null) {
					// Check case attack (last location and it's an enemy)
					if (chessBoardSquare[i][j].getPiece().getPieceColor() != this.pieceColor && i == x) {
						return true;
					} else {
						return false;
					}
				}
				i++;
				j++;
			}
			return true;
		}
		
		if (dCol > 0 && dRow < 0 && Math.abs(dCol) == Math.abs(dRow)) { // Case Right-Top
			int i = this.col + 1;
			int j = this.row - 1;
			while (i <= x) {
				// Check case: Exist the piece in path or not
				if (chessBoardSquare[i][j].getPiece() != null) {
					// Check case attack (last location and it's an enemy)
					if (chessBoardSquare[i][j].getPiece().getPieceColor() != this.pieceColor && i == x) {
						return true;
					} else {
						return false;
					}
				}
				i++;
				j--;
			}
			return true;
		}
		
		if (dCol < 0 && dRow < 0 && Math.abs(dCol) == Math.abs(dRow)) { // Case Left-Top
			int i = this.col - 1;
			int j = this.row - 1;
			while (i >= x) {
				// Check case: Exist the piece in path or not
				if (chessBoardSquare[i][j].getPiece() != null) {
					// Check case attack (last location and it's an enemy)
					if (chessBoardSquare[i][j].getPiece().getPieceColor() != this.pieceColor && i == x) {
						return true;
					} else {
						return false;
					}
				}
				i--;
				j--;
			}
			return true;
		}
		
		if (dCol < 0 && dRow > 0 && Math.abs(dCol) == Math.abs(dRow)) { // Case Left-Bottom
			int i = this.col - 1;
			int j = this.row + 1;
			while (i >= x) {
				// Check case: Exist the piece in path or not 
				if (chessBoardSquare[i][j].getPiece() != null) {
					// Check case attack (last location and it's an enemy)
					if (chessBoardSquare[i][j].getPiece().getPieceColor() != this.pieceColor && i == x) {
						return true;
					} else {
						return false;
					}
				}
				i--;
				j++;
			}
			return true;
		}
		return false;
	}
	
	public boolean conditionAttack(int x, int y, Cell[][] chessBoardSquare) {
		return conditionMove(x, y, chessBoardSquare);
	}
	
	@Override
	public boolean canMove(Cell c2) {
		// TODO Auto-generated method stub
		// Check c2 have piece or not
		if (c2.getPiece() == null) { // Empty slot -> check conditionMove()
			return conditionMove(c2.getCol(), c2.getRow(), c2.getChessBoardSquare());
		} else if (c2.getPiece() != null) {
			Piece tmp = c2.getPiece();
			if (this.pieceColor == tmp.getPieceColor()) { // Same color can't move
				return false;
			} else {									  // Difference color -> check conditionAttack()
				return conditionAttack(c2.getCol(), c2.getRow(), c2.getChessBoardSquare());
			}
		}
		return true;
	}

}
