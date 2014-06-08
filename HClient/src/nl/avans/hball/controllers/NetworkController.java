package nl.avans.hball.controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import net.phys2d.math.ROVector2f;
import nl.avans.hball.entities.PlayerPosition;
import nl.avans.hball.models.HBallModel;

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
				ObjectInputStream objectIn = new ObjectInputStream(	_socket.getInputStream());
				
				Object obj = objectIn.readObject();
				
				HBallModel model = ObjectHandler.Instance().getModel();
				
				while(this._running)
				{

					
					if(obj != null)
					{
						if(obj instanceof ROVector2f)
						{
							model.setBallPosition((ROVector2f)obj);
						}
						else if(obj instanceof List<?>)
						{
							model.setPlayerPositionList((List<PlayerPosition>)obj );
						}
						else
						{
							System.err.println("Error. unexpected package received");
						}
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
