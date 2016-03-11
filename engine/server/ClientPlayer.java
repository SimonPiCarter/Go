package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import engine.Action;
import engine.Colors;

public class ClientPlayer implements IOnlinePlayer {
	
	private Action newAction;
	
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;
	
	public ClientPlayer(String Server) throws IOException {
		newAction = null;
		socket = new Socket(Server, 9090);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream());
	}
	
	public void close() throws IOException {
		socket.close();
	}
	
	
	public void sendAction(Action action) {
		newAction = action;
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
		
		if ( newAction != null ) {
			output.println("action");
			output.println(newAction.getCoordX());
			output.println(newAction.getCoordY());
			output.println(newAction.isSkip());
			output.println(newAction.getColorPlay());
			output.flush();
		}
		

		return act;
	}
}
