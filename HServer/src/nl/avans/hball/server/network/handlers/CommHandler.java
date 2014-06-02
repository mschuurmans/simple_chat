package nl.avans.hball.server.network.handlers;

import java.net.Socket;

public class CommHandler implements Runnable
{
		private Socket _socket;
		
		public CommHandler(Socket s)
		{
			_socket = s;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
}

