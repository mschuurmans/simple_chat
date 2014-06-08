package nl.avans.hball.server.network.handlers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import nl.avans.hball.entities.PlayerPosition;
import nl.avans.hball.server.models.HBallModel;

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
			try
			{
//				System.out.println("step 1");
//				
//				DataInputStream inStream = new DataInputStream(_socket.getInputStream());
//				ObjectInputStream inputFromClient = new ObjectInputStream(inStream);
//				

				System.out.println("step 2");
				
				DataOutputStream outStream = new DataOutputStream(_socket.getOutputStream());
				ObjectOutputStream outputTOClient = new ObjectOutputStream(outStream);
				

				System.out.println("step 3");

				HBallModel model = ObjectHandler.Instance().getModel();
				
				System.out.println("step 4");
				
				while(true)
				{
						
					if(model.getBallPosition() != null)
						outputTOClient.writeObject(model.getBallPosition());
					
					if(model.getPlayerPositionList() != null)
						outputTOClient.writeObject(model.getPlayerPositionList());
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		
		}
		
}

