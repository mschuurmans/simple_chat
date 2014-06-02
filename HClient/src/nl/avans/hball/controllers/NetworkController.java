package nl.avans.hball.controllers;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkController 
{
	private Socket _socket;
	
	public NetworkController(String ip, int port)
	{
		try 
		{
			_socket = new Socket(ip, port);
			Thread t = new Thread(new DoComms(_socket));
			t.start();
			
		}
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	class DoComms implements Runnable
	{
		//PackageHandler packageHandler;
		
		private Socket _socket;
		private boolean _running = true;
		public DoComms(Socket server)
		{						
			// TODO Add algoritmen for deciding player id;	
			_socket = server;
		}
	
		public void run()
		{			
			System.out.println("RUN IN CONNECTION CALLED");
			try
			{
				while(this._running)
				{
					//TODO HANDLE PACKAGE
				}					
			}			
			catch (Exception e)
			{
				e.printStackTrace();
				// handle package
			}			
		}
	}
	
}
