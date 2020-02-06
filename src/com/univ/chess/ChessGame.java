package com.univ.chess;

import javax.swing.JOptionPane;
import com.univ.chess.player.AI;
import com.univ.chess.player.Human;

import java.awt.Color;

public class ChessGame { // apply singleton
	
	private Player player1 = new Human("P1", Color.WHITE);
	private Player player2 = new AI("P2", Color.BLACK);
	private Board board = new Board(player1, player2);
	
	private static ChessGame instance;
	
	private ChessGame() {}
	
	public static synchronized ChessGame getInstance() {
		if (instance == null) {
			instance = new ChessGame();
		}
		return instance;
	}
	
	public void initGame() {
		// Setup board for p2
		player2.setBoard(board);
		
		// draw GUI
		board.setupGUI();
	}
	
	public void startGame() {
		while (!this.board.isEnd()) {
			if (this.board.getTurn() == this.player2.getColor()) {
				// player2.randomMove();
				// player2.calculateBestMove();
				 player2.excuteWithMinimax();
			}
		}
		String msg = null;
		if (this.board.getTurn() == Color.WHITE) {
			msg = "Black win!";
		} else {
			msg = "White win!";
		}
		JOptionPane.showMessageDialog(null, msg, "Chess message", JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
	}

}
