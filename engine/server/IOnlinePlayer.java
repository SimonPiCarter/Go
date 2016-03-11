package server;

import java.io.IOException;

import engine.Action;

public interface IOnlinePlayer {
	public void close() throws IOException;
	
	public void sendAction(Action action);
	
	public Action queryAction();
}
