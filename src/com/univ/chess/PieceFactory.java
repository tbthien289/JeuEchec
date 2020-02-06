package com.univ.chess;

import java.awt.Color;
import com.univ.chess.piece.*;

public class PieceFactory {

	public Piece getPiece(String pieceType, int col, int row, Color color) {
		if (pieceType == null) {
			return null;
		}
		if (pieceType.equalsIgnoreCase("PAWN")) {
			return new Pawn(col, row, color);
		} else if (pieceType.equalsIgnoreCase("BISHOP")) {
			return new Bishop(col, row, color);
		} else if (pieceType.equalsIgnoreCase("KING")) {
			return new King(col, row, color);
		} else if (pieceType.equalsIgnoreCase("KNIGHT")) {
			return new Knight(col, row, color);
		} else if (pieceType.equalsIgnoreCase("QUEEN")) {
			return new Queen(col, row, color);
		} else if (pieceType.equalsIgnoreCase("ROCK")) {
			return new Rock(col, row, color);
		}
		return null;
	}
}
