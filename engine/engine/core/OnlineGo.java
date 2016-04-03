package engine.core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import engine.Action;
import engine.Colors;
import server.AbstractOnlinePlayer;

public class OnlineGo extends BasicGo {
    
	private AbstractOnlinePlayer player = null;
	private Colors localColor;
	
	public OnlineGo(boolean small, AbstractOnlinePlayer player, Colors local) {
		super(small);
		this.player = player;
		localColor = local;
	}

	@Override
	public ICore update(GameContainer arg0, int arg1) throws SlickException {
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
			return this;
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		if ( button == 0 && colorPlaying.equals(localColor) ) {
			if(endGame)
			{
				newAction = new Action(x/board.getPieceSize(),y/board.getPieceSize(),true);
			}
			else newAction = new Action(x/board.getPieceSize(),y/board.getPieceSize(),colorPlaying);
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
