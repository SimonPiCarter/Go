package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import engine.Action;
import engine.Colors;

public abstract class AbstractOnlinePlayer extends Thread {
	
	private Action act;
	
	protected Socket socket;
	protected BufferedReader input;
	protected PrintWriter output;
	
	public void close() throws IOException {
		socket.close();
	}
	
	@Override
	public void run() {
		try {
			while ( true ) {
				queryAction();
			}
		} catch (Exception e) {
			System.out.println("Disconnected!");
		}
	}
	
	public void sendAction(Action action) {
		if ( action != null ) {
			output.println("action");
			output.println(action.getCoordX());
			output.println(action.getCoordY());
			output.println(action.isSkip());
			output.println(action.getColorPlay());
			output.flush();
		}
		synchronized (this) {
			act = null;
		}
	}
	
	public void queryAction() throws NumberFormatException, IOException {
		if ( input.readLine().equals("action") ) {
			synchronized (this) {
				int x = Integer.parseInt(input.readLine());
				int y = Integer.parseInt(input.readLine());
				boolean skip = Boolean.parseBoolean(input.readLine());
				Colors color = Colors.valueOf(input.readLine());
				act = new Action(x,y,color);
				act.setSkip(skip);
				System.out.println("Action received : "+act);
			}
		}
	}
	
	public Action getAction() {
		synchronized (this) {
			return act;
		}
	}
}
