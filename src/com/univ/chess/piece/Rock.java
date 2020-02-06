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

public class Rock extends Piece {

	public Rock(int col, int row, Color pieceColor) {
		super(col, row, pieceColor);
		this.value = 50;
		// TODO Auto-generated constructor stub
		setupImagePiece();
	}
	
	public void setupImagePiece() {
		int i = 0;
		int j = 2;
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
		if (this.getCol() == x) {
			if (y > this.getRow()) {
				for (int i = this.getRow() + 1; i <= y; i++) {
					// Check case: Exist the piece in path or not
					if (chessBoardSquare[x][i].getPiece() != null) {
						// Check case attack (last location and it's an enemy)
						if (chessBoardSquare[x][i].getPiece().getPieceColor() != this.pieceColor && i == y) {
							return true;
						} else {
							return false;
						}
					}
				}
				return true;
			} else {
				for (int i = this.getRow() - 1; i >= y; i--) {
					// Check case: Exist the piece in path or not
					if (chessBoardSquare[x][i].getPiece() != null) {
						// Check case attack (last location and it's an enemy)
						if (chessBoardSquare[x][i].getPiece().getPieceColor() != this.pieceColor && i == y) {
							return true;
						} else {
							return false;
						}
					}
				}
				return true;
			}
		}
		if (this.getRow() == y) {
			if (x > this.getCol()) {
				for (int i = this.getCol() + 1; i <= x; i++) {
					// Check case: Exist the piece in path or not
					if (chessBoardSquare[i][y].getPiece() != null) {
						// Check case attack (last location and it's an enemy)
						if (chessBoardSquare[i][y].getPiece().getPieceColor() != this.pieceColor && i == x) {
							return true;
						} else {
							return false;
						}
					}
				}
				return true;
			} else {
				for (int i = this.getCol() - 1; i >= x; i--) {
					// Check case: Exist the piece in path or not
					if (chessBoardSquare[i][y].getPiece() != null) {
						// Check case attack (last location and it's an enemy)
						if (chessBoardSquare[i][y].getPiece().getPieceColor() != this.pieceColor && i == x) {
							return true;
						} else {
							return false;
						}
					}
				}
				return true;
			}
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
		return false;
	}

}
