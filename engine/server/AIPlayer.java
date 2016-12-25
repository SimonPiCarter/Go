package server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import engine.Action;
import engine.Board;
import engine.Cell;
import engine.Colors;

public class AIPlayer extends AbstractOnlinePlayer {

	private final Colors color;

	private Board board;
	private Board otherBoard;

	public AIPlayer(Colors color) {
		super();
		this.color = color;
	}

	@Override
	public void connect() {
		// NA
	}

	@Override
	public void run() {
		while (isRunning()) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public void sendAction(Action action) {
		synchronized (this) {
			board.play(action);
		}
		queryAction();
		System.out.println(board);
		System.out.println("other board");
		System.out.println(otherBoard);
	}

	public void queryAction() {
		System.out.println("query Action start");
		synchronized (this) {
			Action bestAct = findBestMove();
			act = new Action(bestAct.getCoordX(), bestAct.getCoordY(), color);
			act.setSkip(bestAct.isSkip());
			board.play(act);
			System.out.println("Action received : " + act);
		}
		System.out.println("query Action end");
	}

	private Action findBestMove() {
		Action bestAction = new Action(0, 0, color);
		bestAction.setSkip(true);
		int bestLiberties = computeLiberties();
		for (int x = 0; x < board.getSize(); ++x) {
			for (int y = 0; y < board.getSize(); ++y) {
				if (board.getCell(x, y).getCellColor() == Colors.EMPTY) {
					Action action = new Action(x, y, color);
					if (board.isLegal(action)) {
						board.play(action);
						int liberties = computeLiberties();
						if (liberties > bestLiberties) {
							bestLiberties = liberties;
							bestAction = action;
						}
						board.replay(-1);
					}
				}
			}
		}

		return bestAction;
	}

	private int computeLiberties() {
		int liberties = 0;
		Set<Cell> visitedCell = new HashSet<Cell>();
		for (int x = 0; x < board.getSize(); ++x) {
			for (int y = 0; y < board.getSize(); ++y) {
				Cell cell = board.getCell(x, y);
				if (cell.getCellColor() == color) {
					if (!visitedCell.contains(cell)) {
						ArrayList<Cell> groupList = new ArrayList<Cell>();
						groupList.add(cell);
						liberties += cell.computeNumberOfLiberties(board, groupList);
						visitedCell.addAll(groupList);
					}
				}
			}
		}
		return liberties;
	}

	public void setBoard(Board board) {
		this.board = new Board(board);
		this.otherBoard = board;
	}

}
