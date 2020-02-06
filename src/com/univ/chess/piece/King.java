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

public class King extends Piece {

	public King(int col, int row, Color pieceColor) {
		super(col, row, pieceColor);
		this.value = 1000;
		// TODO Auto-generated constructor stub
		setupImagePiece();
	}
	
	public void setupImagePiece() {
		int i = 0;
		int j = 0;
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
	
	public boolean conditionMove(int x, int y) { // Move to x, y (col, row)
		if (x == this.col + 1 || x == this.col - 1) {
			if (y == this.row + 1 || y == this.row - 1) {
				return true;
			}
		}
		if (x == this.col) {
			if (y == this.row + 1 || y == this.row - 1) {
				return true;
			}
		}
		if (y == this.row) {
			if (x == this.col + 1 || x == this.col - 1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean conditionAttack(int x, int y) {
		return conditionMove(x, y); // -> in this case, move = attack
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
