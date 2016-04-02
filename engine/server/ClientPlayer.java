package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientPlayer extends AbstractOnlinePlayer {
	
	public ClientPlayer(String Server) throws IOException {
		socket = new Socket(Server, 9090);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream());
	}
	
}
