package engine;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTest {

	@Test
	public void testBoard() {
		Board board = new Board(9, 32);
		String a = "coordonées(7;4)\n";
		assertEquals("nop", a, board.getCell(7, 4).toString());
	}

	@Test
	public void testGetCell() {
		// used with testBoard
	}

	@Test
	public void testIsLegal() {
	}

}
