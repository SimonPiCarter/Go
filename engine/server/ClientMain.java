package server;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import engine.Colors;
import engine.Window;
import engine.core.OnlineGo;

public class ClientMain {

	public static void main(String[] args) throws SlickException, IOException {
		
		new AppGameContainer(new Window(new OnlineGo(new ClientPlayer("localhost"),Colors.BLACK), "Go! Client"), 576, 576, false).start();
		
	}
}
