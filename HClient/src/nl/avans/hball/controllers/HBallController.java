package nl.avans.hball.controllers;

import javax.swing.Timer;

import net.phys2d.math.Vector2f;
import nl.avans.hball.events.InputTriggeredEventListener;
import nl.avans.hball.models.HBallModel;
import nl.avans.hball.utils.Enums.GameKeys;
import nl.avans.hball.views.HBallScreen;

public class HBallController 
{
	private HBallModel _model;
	private HBallScreen _view;
	
	private Timer _timerModelUpdate;
	
	public HBallController(HBallModel model, HBallScreen view)
	{
		this._model = model;
		this._view = view;
		
		_timerModelUpdate = new Timer(60, _model);
		_timerModelUpdate.start();
		
		InputController.Instance().setInputTriggeredEventListener(new InputTriggeredEventListener()
		{	
			@Override
			public void buttonPressed(GameKeys key) 
			{
				System.out.println("BUTTON PRESSED!!!!!!!!!!!!!!!!!!!!");
				//TODO handle key ofc.
				
				if(key == GameKeys.KeyA)
				{
					System.out.println("IT WORKS!!!!!!!!!!!!!!");
					_model.movePlayer(new Integer(10), new Vector2f(10, 0));
				}
			}
		});
	}
}
