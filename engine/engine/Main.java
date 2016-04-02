package engine;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import engine.core.Menu;
public class Main {

	public static void main(String[] args) throws SlickException, IOException {
		
		new AppGameContainer(new Window(new Menu(), "Go!"), 576, 576, false).start();
		
	}
}
