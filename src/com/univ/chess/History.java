package com.univ.chess;

public class History {
	
	private Cell cellOld1;
	private Cell cellOld2;
	private Piece pieceOld1;
	private Piece pieceOld2;
	
	public History(Cell cellOld1, Cell cellOld2, Piece pieceOld1, Piece pieceOld2) {
		this.setCellOld1(cellOld1);
		this.setCellOld2(cellOld2);
		this.setPieceOld1(pieceOld1);
		this.setPieceOld2(pieceOld2);
	}

	public Cell getCellOld1() {
		return cellOld1;
	}

	public void setCellOld1(Cell cellOld1) {
		this.cellOld1 = cellOld1;
	}

	public Cell getCellOld2() {
		return cellOld2;
	}

	public void setCellOld2(Cell cellOld2) {
		this.cellOld2 = cellOld2;
	}

	public Piece getPieceOld1() {
		return pieceOld1;
	}

	public void setPieceOld1(Piece pieceOld1) {
		this.pieceOld1 = pieceOld1;
	}

	public Piece getPieceOld2() {
		return pieceOld2;
	}

	public void setPieceOld2(Piece pieceOld2) {
		this.pieceOld2 = pieceOld2;
	}
	
}
