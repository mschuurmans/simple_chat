package nl.avans.server.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import net.phys2d.math.Vector2f;
import nl.avans.hball.server.models.HBallModel;

public class HBallController implements ActionListener
{
	private HBallModel _model;
	
	private Timer _timerModelUpdate, _controllerTimer;
	
	float keyForce = 40f;
	
	private boolean[] wasdStatus = new boolean[4];
	
	public HBallController(HBallModel model)
	{
		this._model = model;
		
		_timerModelUpdate = new Timer(60, _model);
		_timerModelUpdate.start();
		
		_controllerTimer = new Timer(10, this);
		_controllerTimer.start();
		
	}
	
	public void enableWasdBool(char c)
	{
		switch(c)
		{
		case 'w': wasdStatus[0] = true; break;
		case 'a': wasdStatus[1] = true; break;
		case 's': wasdStatus[2] = true; break;
		case 'd': wasdStatus[3] = true; break;
		}
	}
	
	private void driveModel()
	{
		//update the vector that moves the player
		float vertical = 0;
		float horizontal = 0;
		
		if (wasdStatus[0])
		{
			vertical += -keyForce;
		}
		
		if (wasdStatus[1])
		{
			horizontal += -keyForce;
		}
		
		if (wasdStatus[2])
		{
			vertical += keyForce;
		}
		
		if (wasdStatus[3])
		{
			horizontal += keyForce;
		}
		
		if(movementKeyIsDown())
			_model.movePlayer(new Integer(HBallModel.PLAYERTESTID), new Vector2f(horizontal, vertical));	
	}
	
	private boolean movementKeyIsDown()
	{
		boolean result = false;
		
		for (int i = 0; i < wasdStatus.length; i++)
		{
			if(wasdStatus[i] == true)
				result = true;
		}
		
		return result;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		driveModel();
		wasdStatus = new boolean[4];
	}
}
