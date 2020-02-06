package com.univ.chess;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public abstract class Piece implements Cloneable {
	
	protected int row;
	protected int col;
	protected Color pieceColor;
	protected Image pieceImage;
	protected int value;
	
	public Piece(int col, int row, Color pieceColor) {
		this.col = col;
		this.row = row;
		this.pieceColor = pieceColor;
	}
	
	@Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
	
	public Image getImage() {
		return this.pieceImage;
	}
	
	public Color getPieceColor() {
		return this.pieceColor;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public abstract boolean canMove(Cell c2);
	
}
