package engine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseListener implements MouseListener {

	int boardX=0, boardY =0;
	boolean eventClicked=false;

	public int getBoardX() {
		return boardX;
	}

	public int getBoardY() {
		return boardY;
	}

	public boolean isEventClicked() {
		return eventClicked;
	}

	public void resetEvent(){
		boardX=0;
		boardY=0;
		eventClicked=false;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		boardX=arg0.getX();
		boardY=arg0.getY();
		eventClicked=true;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
