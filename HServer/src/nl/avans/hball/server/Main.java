package nl.avans.hball.server;

import nl.avans.hball.server.network.NetworkController;

public class Main 
{
	public static final int PORT = 12346;
	
	public static void main(String[] args)
	{
		NetworkController network = new NetworkController(PORT);
		network.start();
	}
}
