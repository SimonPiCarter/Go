package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientPlayer extends AbstractOnlinePlayer {

	private String server;

	public ClientPlayer(String server) {
		this.server = server;
	}

	@Override
	public void connect() {
		try {
			socket = new Socket(server, 9090);

			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Could not connect to server " + server + ":9090");
		}
		setConnected(true);
	}

}
