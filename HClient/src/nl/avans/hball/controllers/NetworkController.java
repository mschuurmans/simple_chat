package nl.avans.hball.controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import nl.avans.hball.networklib.HPackage;
import nl.avans.hball.networklib.PingPackage;
import nl.avans.hball.networklib.PositionsPackage;

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
					ObjectOutputStream out = new ObjectOutputStream(_socket.getOutputStream());
					
					HPackage packOut = new PingPackage();
					
					boolean available = NetworkQueueController.Instance().available();
					
					if(available)
						packOut = NetworkQueueController.Instance().getNext();
					

					out.writeObject(packOut);
					out.flush();
					
					ObjectInputStream in = new ObjectInputStream(_socket.getInputStream());
					HPackage packIn = (HPackage)in.readObject();
					
					
					if(packIn instanceof PositionsPackage)
					{
						PositionsPackage pp = (PositionsPackage)packIn;
						NetworkQueueController.Instance().callPositionsPackageReceived(pp);
					}
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
