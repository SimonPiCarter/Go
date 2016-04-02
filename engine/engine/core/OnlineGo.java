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
import server.AbstractOnlinePlayer;

public class OnlineGo implements ICore {
    
	private AbstractOnlinePlayer player = null;
	
	private Panel panel = null;
	private boolean playSkip;
	private boolean legalMove;
	private Board board;
	private boolean endGame;
	private Colors localColor;
	private Colors colorPlaying;
	private boolean computedScore;
	private Action newAction = null;
	//private boolean ctrlPressed=false;
	private boolean justSkip;

	
	public OnlineGo(AbstractOnlinePlayer player, Colors local) {
		this.player = player;
		player.start();
		board =new Board(9);
		localColor = local; 	
		colorPlaying = Colors.WHITE;
		legalMove=false;
		playSkip=false;
	}


	@Override
	public void init() throws SlickException {
		panel = new Panel(board);
		Colors.WHITE.setImg(new Image("white_token.png"));
		Colors.BLACK.setImg(new Image("black_token.png"));
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		panel.paintComponent(gc,g);
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
			if ( !localColor.equals(colorPlaying) ) {
				// Ask for server if an action has been played
				newAction = player.getAction();
			}
			if( newAction != null )
			{
				///choix du coup et verif de la légalité
				legalMove=board.isLegal(newAction);
				if ( legalMove ) {
					// If legal move and local color playing send action to other player
					if ( localColor.equals(colorPlaying) ) {
						System.out.println(newAction);
						player.sendAction(newAction);
					}
					// Play next turn (and reset action)
					nextTurn(newAction);
				}
			}
			
			if(playSkip)
			{
				newAction = new Action();
				if ( localColor.equals(colorPlaying) ) {
					System.out.println(newAction);
					player.sendAction(newAction);
				}
				nextTurn(newAction);
			}
			
	}
	
	private void nextTurn(Action play) {
		if(playSkip)
		{
			if(endGame&&!computedScore)
			{
				board.groupForScore();
				computedScore=true;
			}
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
		
		colorPlaying=colorPlaying.oppositeColor();
		legalMove = false;
		playSkip = false;
		newAction=null;
	}
	
	
	public void setPanel(Panel panel) {
		this.panel  = panel;
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		if ( button == 0 && colorPlaying.equals(localColor) ) {
			if(endGame)
			{
				newAction = new Action(x/64,y/64,true);
			}
			else newAction = new Action(x/64,y/64,colorPlaying);
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		if ( key == Input.KEY_S && colorPlaying.equals(localColor) ) {
			playSkip = true;
		}
		
	}
	
	@Override
	public void keyPressed(int key, char c) {
	}
}
