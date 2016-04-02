package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

import server.tools.IPAdressToolbox;

public class ServerPlayer extends AbstractOnlinePlayer {
	
	public ServerPlayer() throws IOException {
		System.out.println("Your local IP: "+IPAdressToolbox.getLocalIP());
		System.out.println("Your global IP: "+IPAdressToolbox.getGlobalIP());
		System.out.println("Waiting for client to connect on port 9090...");
		ServerSocket listener = new ServerSocket(9090);
		socket = listener.accept();
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream());
		listener.close();
	}
}
