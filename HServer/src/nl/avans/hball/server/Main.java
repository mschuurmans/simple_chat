package nl.avans.hball.server;

import nl.avans.hball.server.models.HBallModel;
import nl.avans.hball.server.network.NetworkController;
import nl.avans.hball.server.network.handlers.ObjectHandler;
import nl.avans.server.controllers.HBallController;

public class Main 
{
	public static final int PORT = 12346;
	
	public static void main(String[] args)
	{
		HBallModel model = new HBallModel();
		ObjectHandler objectHandler = new ObjectHandler();
		HBallController controller = new HBallController(model);
		objectHandler.setModel(model);
		

//		HBallController controller = new HBallController(model);
		
		NetworkController network = new NetworkController(PORT);
		network.start();
	}
}
