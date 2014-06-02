package nl.avans.hball;

import nl.avans.hball.controllers.HBallController;
import nl.avans.hball.models.HBallModel;
import nl.avans.hball.views.BaseFrame;
import nl.avans.hball.views.HBallScreen;

public class Main 
{
	public static void main(String[] args)
	{
		HBallModel model = new HBallModel();
		HBallScreen view = new HBallScreen(model);
		HBallController controller = new HBallController(model, view);
		new BaseFrame(view);
	}
	
}
