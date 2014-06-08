package nl.avans.hball.server.network.handlers;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import nl.avans.hball.networklib.HPackage;
import nl.avans.hball.networklib.PositionsPackage;

public class CommHandler implements Runnable
{
		private Socket _socket;
		
		public CommHandler(Socket s)
		{
			_socket = s;
		}
		
		@Override
		public void run()
		{
			while(true)
			{
				try
				{
					ObjectInputStream in = new ObjectInputStream(_socket.getInputStream());
					
					HPackage packIn = (HPackage)in.readObject();
					//TODO do something usefull with incomming package.
					System.out.println("INCOMMING PACKAGE: " + packIn);
					ObjectOutputStream out = new ObjectOutputStream(_socket.getOutputStream());
					out.writeObject(new PositionsPackage()); // TODO FILL THIS PACKAGE WITH DATA.
					out.flush();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
}

