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

public class Pawn extends Piece {
	
	public Pawn(int col, int row, Color pieceColor) {
		super(col, row, pieceColor);
		this.value = 10;
		setupImagePiece();
	}
	
	public void setupImagePiece() {
		int i = 0;
		int j = 5;
		if (pieceColor == Color.BLACK) {
			i = 0;
		} else if (pieceColor == Color.WHITE) {
			i = 1;
		}
		
		URL url = null;
		File file = new File("E:\\UAPV\\Application Conception\\Chess\\src\\com\\univ\\chess\\memI0.png");
		try {
//			url = new URL("http://i.stack.imgur.com/memI0.png");
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
	
	public boolean conditionMove(int x, int y) { // Move to x, y
		// First time move
		if (this.col == x) {
			if (this.pieceColor == Color.WHITE) {
				if (this.row == 6 && this.row - 2 == y) {
					return true;
				}
			}
			if (this.pieceColor == Color.BLACK) {
				if (this.row == 1 && this.row + 2 == y) {
					return true;
				}
			}
		}
		// Second time move
		if (this.col == x) {
			if (this.pieceColor == Color.WHITE && this.row == y + 1) {
				return true;
			} else if (this.pieceColor == Color.BLACK && this.row == y - 1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean conditionAttack(int x, int y) {
		// Attack
		if (this.pieceColor == Color.WHITE) {
			if (this.row - 1 == y && this.col + 1 == x) {
				return true;
			} else if (this.row - 1 == y && this.col - 1 == x) {
				return true;
			}
		} else if (this.pieceColor == Color.BLACK) {
			if (this.row + 1 == y && this.col + 1 == x) {
				return true;
			} else if (this.row + 1 == y && this.col - 1 == x) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canMove(Cell c2) {
		// TODO Auto-generated method stub
		
		// Check c2 have piece or not
		if (c2.getPiece() == null) { // Empty slot -> check conditionMove()
			return conditionMove(c2.getCol(), c2.getRow());
		} else if (c2.getPiece() != null) {
			Piece tmp = c2.getPiece();
			if (this.pieceColor == tmp.getPieceColor()) { // Same color can't move
				return false;
			} else {									  // Difference color -> check conditionAttack()
				return conditionAttack(c2.getCol(), c2.getRow());
			}
		}
		return false;
	}

}
