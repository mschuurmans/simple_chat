package nl.avans.hball.server.network.handlers;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import nl.avans.hball.networklib.HPackage;
import nl.avans.hball.networklib.PingPackage;
import nl.avans.hball.networklib.PlayerPosition;
import nl.avans.hball.networklib.PositionsPackage;
import nl.avans.hball.server.models.HBallModel;

public class CommHandler implements Runnable
{
		private Socket _socket;
		private boolean _run = true;
		public CommHandler(Socket s)
		{
			_socket = s;
		}
		
		@Override
		public void run()
		{
			HBallModel model = ObjectHandler.Instance().getModel();
			
			while(_run)
			{
				try
				{
					ObjectInputStream in = new ObjectInputStream(_socket.getInputStream());
					
					HPackage packIn = (HPackage)in.readObject();
					//TODO do something usefull with incomming package.
					if(!(packIn instanceof PingPackage))
						System.out.println("INCOMMING PACKAGE: " + packIn);
					
					ObjectOutputStream out = new ObjectOutputStream(_socket.getOutputStream());
					
					PlayerPosition ballPos = model.getBallPosition();
					List<PlayerPosition> playerPosList = model.getPlayerPositionList();
					
					out.writeObject(new PositionsPackage(ballPos, playerPosList)); // TODO FILL THIS PACKAGE WITH DATA.
					out.flush();
				}
				catch(SocketException e)
				{
					_run = false;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
}

