package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

public class ServerPlayer extends AbstractOnlinePlayer {
	
	public ServerPlayer() throws IOException {
		ServerSocket listener = new ServerSocket(9090);
		socket = listener.accept();
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream());
		listener.close();
	}
}
