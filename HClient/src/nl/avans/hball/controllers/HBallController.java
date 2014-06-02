package nl.avans.hball.controllers;

import nl.avans.hball.events.InputTriggeredEventListener;
import nl.avans.hball.models.HBallModel;
import nl.avans.hball.utils.Enums.GameKeys;
import nl.avans.hball.views.HBallScreen;

public class HBallController 
{
	private HBallModel _model;
	private HBallScreen _view;
	
	public HBallController(HBallModel model, HBallScreen view)
	{
		this._model = model;
		this._view = view;
		
		InputController.Instance().setInputTriggeredEventListener(new InputTriggeredEventListener()
		{	
			@Override
			public void buttonPressed(GameKeys key) 
			{
				//TODO handle key ofc.
			}
		});
	}
}
