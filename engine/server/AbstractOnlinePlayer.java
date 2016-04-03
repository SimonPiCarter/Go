package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import engine.Action;
import engine.Colors;

public abstract class AbstractOnlinePlayer extends Thread {
	
	private Action act;

	private boolean connected;
	private boolean running;
	
	protected Socket socket;
	protected BufferedReader input;
	protected PrintWriter output;
	
	public AbstractOnlinePlayer() {
		setConnected(false);
		setRunning(true);
	}
	
	public abstract void connect();
	
	public void close() throws IOException {
		socket.close();
	}
	
	@Override
	public void run() {
		while (!connected && isRunning()) {
			connect();
		}
		
		try {			
			while ( isRunning() ) {
				queryAction();
			}
		} catch (Exception e) {
			System.out.println("Disconnected!");
		}
		if ( !isRunning() ) {
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

	public synchronized boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
