package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import engine.Action;
import engine.Colors;

public abstract class AbstractOnlinePlayer {
	
	protected Socket socket;
	protected BufferedReader input;
	protected PrintWriter output;
	
	public void close() throws IOException {
		socket.close();
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
	}
	
	public Action queryAction() {
		Action act = null;
		
		
		try {
			if ( input.readLine().equals("action") ) {
				int x = Integer.parseInt(input.readLine());
				int y = Integer.parseInt(input.readLine());
				boolean skip = Boolean.parseBoolean(input.readLine());
				Colors color = Colors.valueOf(input.readLine());
				act = new Action(x,y,color);
				act.setSkip(skip);
			}

		} catch (IOException e) {
			e.printStackTrace();
			try {
				close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		

		return act;
	}
}
