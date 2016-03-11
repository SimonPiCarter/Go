package engine;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class Window extends BasicGame {
    
	private Panel panel = null;
	private boolean playSkip;
	private boolean legalMove;
	private Board board;
	
	private Colors colorPlaying;
	
	private Action newAction = null;
	private boolean ctrlPressed=false;
	private boolean justSkip=false;

	public Window(String name) throws SlickException {
        super(name);		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		panel.paintComponent(gc,g);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		board =new Board(9);
		panel = new Panel(board);
		colorPlaying = Colors.WHITE;
		legalMove=false;
		playSkip=false;
		Colors.WHITE.img = new Image("white_token.png");
		Colors.BLACK.img = new Image("black_token.png");
		
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
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
			
	}
	
	private void nextTurn(Action play) {
		if(playSkip)
		{
			if(justSkip)
			{
				endOfGame();
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
	
	private void endOfGame(){
		
	}
	
	public void setPanel(Panel panel) {
		this.panel  = panel;
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		if ( button == 0 ) {
			newAction = new Action(x/64,y/64,colorPlaying);
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
