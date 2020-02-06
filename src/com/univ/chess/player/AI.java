package com.univ.chess.player;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import com.univ.chess.Board;
import com.univ.chess.Move;
import com.univ.chess.Player;

public class AI extends Player {

	public AI(String name, Color color) {
		super(name, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void randomMove() {
		ArrayList<Move> arrayMoves = this.board.posibleMove();
		int index = (int)(Math.random() * arrayMoves.size()); // get position (0 -> lengh)
		Move tmp = arrayMoves.get(index);
	
		this.board.movePiece(tmp.getC1(), tmp.getR1(), tmp.getC2(), tmp.getR2());	
	}

	@Override
	public void calculateBestMove() {
		// TODO Auto-generated method stub
		ArrayList<Move> arrayMoves = this.board.posibleMove();
		Collections.shuffle(arrayMoves); // Make a random sort to evade the repetitive movement
		
		int bestValue = -Integer.MAX_VALUE;
		Move bestMove = null;
		
		for (int i = 0; i < arrayMoves.size(); i++) {
			Move tmp = arrayMoves.get(i);
			this.board.movePiece(tmp.getC1(), tmp.getR1(), tmp.getC2(), tmp.getR2());
			int currentValue = this.board.evaluateBoard(this.color);
			this.board.undo();
			
			if (currentValue > bestValue) {
				bestMove = tmp;
				bestValue = currentValue;
			}
		}
		
		if (bestMove != null) {
			this.board.movePiece(bestMove.getC1(), bestMove.getR1(), bestMove.getC2(), bestMove.getR2());
		}
	}
	
	@Override
	public int calculateWithMinimax(int depth, boolean isMaximizing) {
		// TODO Auto-generated method stub
		
		if (depth == 0) {
			return this.board.evaluateBoard(this.color);
		}
		
		ArrayList<Move> arrayMoves = this.board.posibleMove();
		Collections.shuffle(arrayMoves); // Make a random sort to evade the repetitive movement
		
		Move bestMove = null;
		int bestValue; 
		if (isMaximizing) {
			bestValue = -Integer.MAX_VALUE;
		} else {
			bestValue = Integer.MAX_VALUE;
		}

		for (int i = 0; i < arrayMoves.size(); i++) {
			Move tmp = arrayMoves.get(i);
			this.board.movePieceSimulate(tmp.getC1(), tmp.getR1(), tmp.getC2(), tmp.getR2());
			int currentValue = this.calculateWithMinimax(depth - 1, !isMaximizing);
			
			if (isMaximizing) {
				// Maximize way
				if (currentValue > bestValue) {
					bestMove = tmp;
					bestValue = currentValue;
				}
			} else {
				// Minimize way
				if (currentValue < bestValue) {
					bestMove = tmp;
					bestValue = currentValue;
				}
			}
			
			// Undo previous step
			this.board.undo();
		}
		this.bestMove = bestMove;
		return bestValue;
	}

	@Override
	public void excuteWithMinimax() {
		// TODO Auto-generated method stub
		this.calculateWithMinimax(4, true);
		if (bestMove != null) {
			this.board.movePiece(bestMove.getC1(), bestMove.getR1(), bestMove.getC2(), bestMove.getR2());
		}
	}
	

}
