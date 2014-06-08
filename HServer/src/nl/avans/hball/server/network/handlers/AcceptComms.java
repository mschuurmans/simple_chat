package nl.avans.hball.server.network.handlers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import nl.avans.hball.server.models.HBallModel;

public class AcceptComms implements Runnable
{
	private int _port = 12346;
	private boolean _running = true;
	
	public AcceptComms(int port)
	{
		_port = port;
	}
	public void stop()
	{
		_running = false;
	}
	@Override
	public void run() 
	{
		try 
		{
			HBallModel model = ObjectHandler.Instance().getModel();
			
			ServerSocket listener = new ServerSocket(_port);
			Socket server;
			System.out.println("Server started waiting for incomming connections.");
			while(_running)
			{
				int clientId = model.getPlayerList().size();
				System.out.println("Waiting for a new incomming connection");
				
				server = listener.accept();
				System.out.println("Incomming connection - starting new thread");
				
				CommHandler h = new CommHandler(clientId, server);
				Thread t = new Thread(h);
				t.start();
				
				System.out.println("Connection handled thread was started.");
			}
			
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}

}
