package nl.avans.hball.server.network.handlers;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import nl.avans.hball.networklib.EnumsNetwork.MoveDirections;
import nl.avans.hball.networklib.HPackage;
import nl.avans.hball.networklib.MovePackage;
import nl.avans.hball.networklib.PingPackage;
import nl.avans.hball.networklib.PlayerPosition;
import nl.avans.hball.networklib.PositionsPackage;
import nl.avans.hball.networklib.SendKickPackage;
import nl.avans.hball.server.models.HBallModel;
import nl.avans.server.controllers.HBallController;

public class CommHandler implements Runnable
{
		private int _clientId;
		private Socket _socket;
		private boolean _run = true;
		private HBallModel model;
		
		public CommHandler(int id, Socket s)
		{
			model= ObjectHandler.Instance().getModel();
			
			_clientId = id;
			_socket = s;
			
			model.addNewPlayer();

		}
		
		@Override
		public void run()
		{
			HBallController controller = ObjectHandler.Instance().get_controller();
			
			while(_run)
			{
				try
				{
					ObjectInputStream in = new ObjectInputStream(_socket.getInputStream());
					
					HPackage packIn = (HPackage)in.readObject();
					//TODO do something usefull with incomming package.
					if(!(packIn instanceof PingPackage))
					{
						if(packIn instanceof SendKickPackage)
						{
							model.ballKick( ((SendKickPackage) packIn).get_id() );
						}
						if(packIn instanceof MovePackage)
						{
							int id = ((MovePackage) packIn).getId();
							
							if(((MovePackage) packIn).getDirection() == MoveDirections.Up)
							{
								controller.enableWasdBool(id, 'w');
							}
							else if(((MovePackage) packIn).getDirection() == MoveDirections.Right)
							{
								controller.enableWasdBool(id, 'd');
							}
							else if(((MovePackage) packIn).getDirection() == MoveDirections.Down)
							{
								controller.enableWasdBool(id, 's');
							}
							else if(((MovePackage) packIn).getDirection() == MoveDirections.Left)
							{
								controller.enableWasdBool(id, 'a');
							}
							
						}

						System.out.println("INCOMMING PACKAGE: " + packIn);
					}
					
					ObjectOutputStream out = new ObjectOutputStream(_socket.getOutputStream());
					
					PlayerPosition ballPos = model.getBallPosition();
					List<PlayerPosition> playerPosList = model.getPlayerPositionList();
					
					out.writeObject(new PositionsPackage(_clientId, ballPos, playerPosList)); // TODO FILL THIS PACKAGE WITH DATA.
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

