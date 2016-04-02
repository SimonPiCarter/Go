package engine;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import engine.core.BasicGo;
public class Main {

	public static void main(String[] args) throws SlickException, IOException {
		
		new AppGameContainer(new Window(new BasicGo(), "Go!"), 576, 576, false).start();
		
	}
}
