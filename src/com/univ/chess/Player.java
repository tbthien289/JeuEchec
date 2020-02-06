package com.univ.chess;

import java.awt.Color;

public abstract class Player {
	
	private String name;
	protected Color color;
	protected Board board;
	protected Move bestMove = null;
	
	public Player(String name, Color color) {
		this.setName(name);
		this.color = color;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public Color getColor() {
		return this.color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public abstract void randomMove();
	public abstract void calculateBestMove();
	public abstract void excuteWithMinimax();
	public abstract int calculateWithMinimax(int depth, boolean isMaximizing);
}
