package nl.avans.hball.server.network;

import nl.avans.hball.server.network.handlers.AcceptComms;

public class NetworkController 
{
	private int _port = 12346;
	private AcceptComms _accepter;
	
	public NetworkController(int port)
	{
		this._port = port;
		_accepter = new AcceptComms(_port);
	}
	
	public void start()
	{
		new Thread(_accepter).start();
	}
	
	public void stop()
	{
		_accepter.stop();
	}
}

