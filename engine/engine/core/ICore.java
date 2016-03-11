package engine.core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public interface ICore {
	
	public void render(GameContainer gc, Graphics g) throws SlickException;
	
	public void init() throws SlickException;
	
	public void update(GameContainer arg0, int arg1) throws SlickException;
	
	public void mouseReleased(int button, int x, int y);

	public void keyReleased(int key, char c);
	
	public void keyPressed(int key, char c);
}
