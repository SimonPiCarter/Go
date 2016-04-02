package engine.core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import engine.Action;
import engine.Board;
import engine.Colors;
import engine.Panel;

public class BasicGo implements ICore {
    

	private Panel panel = null;
	private boolean playSkip;
	private boolean legalMove;
	private Board board;
	private boolean endGame;
	private Colors colorPlaying;
	private boolean computedScore;
	private Action newAction = null;
	private boolean ctrlPressed;
	private boolean justSkip;

	public BasicGo() {
		board =new Board(9);
		colorPlaying = Colors.WHITE;
		legalMove=false;
		playSkip=false;
	}
	
	public void init() throws SlickException {
		panel = new Panel(board);
		Colors.WHITE.setImg(new Image("images/white_token.png"));
		Colors.BLACK.setImg(new Image("images/black_token.png"));
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		panel.paintComponent(gc,g);
	}

	@Override
	public ICore update(GameContainer arg0, int arg1) throws SlickException {
			if( newAction != null )
			{
				///choix du coup et verif de la légalité
				legalMove=board.isLegal(newAction);
				if ( legalMove ) {
					nextTurn(newAction);
				}
			}
			
			if(playSkip)
			{
				nextTurn(new Action());
			}
			return this;
	}
	
	private void nextTurn(Action play) {
		if(playSkip)
		{
			if(justSkip)
			{
				endGame=true;
			}
			justSkip=true;
		}
		else
		{
			board.play(play);
			justSkip=false;
		}
		if(endGame&&!computedScore)
		{
			board.groupForScore();
			computedScore=true;
		}
		colorPlaying=colorPlaying.oppositeColor();
		legalMove = false;
		playSkip = false;
		System.out.println("BLACK :"+Colors.BLACK.getScore());
		System.out.println("WHITE :"+Colors.WHITE.getScore()+"\n");
		newAction=null;
	}
	

	public void setPanel(Panel panel) {
		this.panel  = panel;
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		if ( button == 0 ) {
			if(endGame)
			{
				newAction = new Action(x/64,y/64,true);
			}
			else newAction = new Action(x/64,y/64,colorPlaying);
		}

	}

	@Override
	public void keyReleased(int key, char c) {
		if ( key == Input.KEY_S ) {
			playSkip = true;
		}
		if( key == Input.KEY_BACK&&ctrlPressed)
		{
			board.replay(-1);
		}
		
		if ( key == Input.KEY_LCONTROL|| key == Input.KEY_RCONTROL) {
			ctrlPressed = false;
		}
		
	}
	
	@Override
	public void keyPressed(int key, char c) {
		if ( key == Input.KEY_LCONTROL|| key == Input.KEY_RCONTROL) {
			ctrlPressed = true;
		}
	}
}
