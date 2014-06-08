package nl.avans.hball;

import nl.avans.hball.controllers.HBallController;
import nl.avans.hball.controllers.NetworkController;
import nl.avans.hball.controllers.ObjectHandler;
import nl.avans.hball.models.HBallModel;
import nl.avans.hball.views.BaseFrame;
import nl.avans.hball.views.HBallScreen;

public class Main 
{
	public static void main(String[] args)
	{
		ObjectHandler objectHandler = new ObjectHandler();
		
		HBallModel model = new HBallModel();
		HBallScreen view = new HBallScreen(model);
		objectHandler.setModel(model);
		HBallController controller = new HBallController(model, view);

		NetworkController network = new NetworkController("127.0.0.1", 12346);
		new BaseFrame(view);
	}
	
}
