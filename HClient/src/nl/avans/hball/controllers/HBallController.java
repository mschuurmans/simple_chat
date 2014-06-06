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
		
		System.out.println("method call");
		InputController.Instance().setInputTriggeredEventListener(new InputTriggeredEventListener()
		{	
			@Override
			public void buttonPressed(char key) 
			{
				char[] chars = "wasd".toCharArray();
				char keyChar = key;
				
				if (keyChar == chars[0])
				{
					
				}
				else if (keyChar == chars[1])
				{
					System.out.println("a Released");
					_model.movePlayer(new Integer(1), new Vector2f(1000f, 0));	
				}
				else if (keyChar == chars[2])
				{
					
				}
				else if (keyChar == chars[3])
				{
					
				}
				else if (keyChar == chars[4])
				{
					
				}
			}
		});
	}
}
