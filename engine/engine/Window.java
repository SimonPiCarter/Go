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
	private int moveX;
	private int moveY;
	private Colors colorPlaying;
	private int boardX;
	private int boardY;
	private boolean eventClicked;
	private boolean ctrlPressed=false;

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
		moveX=0;
		moveY=0;
		legalMove=false;
		playSkip=false;
		Colors.WHITE.img = new Image("white_token.png");
		Colors.BLACK.img = new Image("black_token.png");
		
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
			if( eventClicked )
			{
				///choix du coup et verif de la légalité
				moveX=boardX/64;
				moveY=boardY/64;
				Action playing=new Action(moveX,moveY,colorPlaying);
				legalMove=board.isLegal(playing);
				if ( legalMove ) {
					nextTurn(playing);
				}
			}
			
			if(playSkip)
			{
				nextTurn(new Action());
			}
			
	}
	
	private void nextTurn(Action play) {
		if(!playSkip)
		{
			board.play(play);
		}
		else
		{
			board.play(play);
		}
		
		colorPlaying=colorPlaying.oppositeColor();
		legalMove = false;
		playSkip = false;
		eventClicked=false;
		moveX=0;
		moveY=0;
	}
	
	public void setPanel(Panel panel) {
		this.panel  = panel;
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		if ( button == 0 ) {
			boardX=x;
			boardY=y;
			eventClicked=true;
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
