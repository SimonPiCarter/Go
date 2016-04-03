package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

import server.tools.IPAdressToolbox;

public class ServerPlayer extends AbstractOnlinePlayer {
	
	public ServerPlayer() {
		System.out.println("Your local IP: "+IPAdressToolbox.getLocalIP());
		System.out.println("Your global IP: "+IPAdressToolbox.getGlobalIP());
		System.out.println("Waiting for client to connect on port 9090...");
		
	}

	@Override
	public void connect() {
		
		ServerSocket listener = null;
		try {
			listener = new ServerSocket(9090);
			socket = listener.accept();
			
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Could not connect to port 9090");
		}finally {
			if ( listener != null ) {
				try {
					listener.close();
				} catch (IOException e) {
					System.out.println("Could not open socket on port 9090");
				}
			}
		}
		setConnected(true);
	}
	
}
