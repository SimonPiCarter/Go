package engine;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import engine.core.ICore;


public class Window extends BasicGame {
	
	private ICore core;

	public Window(ICore core, String name) throws SlickException {
        super(name);	
        this.core = core;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		core.render(gc, g);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		core.init();
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		core = core.update(arg0, arg1);
			
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		core.mouseReleased(button, x, y);
	}

	@Override
	public void keyReleased(int key, char c) {
		core.keyReleased(key, c);
	}
	
	@Override
	public void keyPressed(int key, char c) {
		core.keyPressed(key, c);
	}
}
