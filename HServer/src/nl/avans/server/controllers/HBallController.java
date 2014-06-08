package nl.avans.server.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import net.phys2d.math.Vector2f;
import nl.avans.hball.server.models.HBallModel;

public class HBallController implements ActionListener
{
	private HBallModel _model;
	
	private static int CLIENTSAMPLE = 20;
	
	private Timer _timerModelUpdate, _controllerTimer;
	
	float keyForce = 40f;
	
	private boolean[][] wasdStatus = new boolean[CLIENTSAMPLE][4];
	
	public HBallController(HBallModel model)
	{
		this._model = model;
		
		_timerModelUpdate = new Timer(60, _model);
		_timerModelUpdate.start();
		
		_controllerTimer = new Timer(10, this);
		_controllerTimer.start();
		
	}
	
	public void enableWasdBool(int clientId, char c)
	{
		switch(c)
		{
		case 'w': wasdStatus[clientId][0] = true; break;
		case 'a': wasdStatus[clientId][1] = true; break;
		case 's': wasdStatus[clientId][2] = true; break;
		case 'd': wasdStatus[clientId][3] = true; break;
		}
	}
	
	private void driveModel(int clientId)
	{
		//update the vector that moves the player
		float vertical = 0;
		float horizontal = 0;
		
		if (wasdStatus[clientId][0])
		{
			vertical += -keyForce;
		}
		
		if (wasdStatus[clientId][1])
		{
			horizontal += -keyForce;
		}
		
		if (wasdStatus[clientId][2])
		{
			vertical += keyForce;
		}
		
		if (wasdStatus[clientId][3])
		{
			horizontal += keyForce;
		}
		
		if(movementKeyIsDown(clientId))
			_model.movePlayer(clientId, new Vector2f(horizontal, vertical));	
	}
	
	private boolean movementKeyIsDown(int clientId)
	{
		boolean result = false;
		
		for (int i = 0; i < 4; i++)
		{
			if(wasdStatus[clientId][i] == true)
				result = true;
		}
		
		return result;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		for(int i = 0; i < CLIENTSAMPLE; i++)
		{
			driveModel(i);
		}
		wasdStatus = new boolean[CLIENTSAMPLE][4];
	}
}
