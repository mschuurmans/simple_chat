package nl.avans.hball.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import nl.avans.hball.events.InputTriggeredEventListener;
import nl.avans.hball.events.PackageReceivedEventListener;
import nl.avans.hball.models.HBallModel;
import nl.avans.hball.networklib.EnumsNetwork.MoveDirections;
import nl.avans.hball.networklib.MovePackage;
import nl.avans.hball.networklib.PlayerPosition;
import nl.avans.hball.networklib.PositionsPackage;
import nl.avans.hball.networklib.SendKickPackage;
import nl.avans.hball.views.HBallScreen;

public class HBallController implements ActionListener
{
	private HBallModel _model;
	private HBallScreen _view;
	
	private Timer _timerModelUpdate, _controllerTimer;
	
	float keyForce = 40f;
	
	private boolean[] wasdStatus = new boolean[4];
	
	public HBallController(HBallModel model, HBallScreen view)
	{
		this._model = model;
		this._view = view;
		
		_timerModelUpdate = new Timer(60, _model);
		_timerModelUpdate.start();
		
		_controllerTimer = new Timer(10, this);
		_controllerTimer.start();
		
		System.out.println("method call");
		InputController.Instance().setInputTriggeredEventListener(new InputTriggeredEventListener()
		{	
			@Override
			public void buttonPressed(char key) 
			{
				int id = _model.getClientId();
				
				System.out.println("Key: " + key);
				if(key == ' ')
				{
					NetworkQueueController.Instance().add( new SendKickPackage(id) );
				}
				else if(key == 'w')
				{
					NetworkQueueController.Instance().add(new MovePackage(id, MoveDirections.Up));
				}
				else if(key == 'a')
				{
					NetworkQueueController.Instance().add(new MovePackage(id, MoveDirections.Left));
				}
				else if(key == 's')
				{
					NetworkQueueController.Instance().add(new MovePackage(id, MoveDirections.Down));
				}
				else if(key == 'd')
				{
					NetworkQueueController.Instance().add(new MovePackage(id, MoveDirections.Right));
				}
				
				//check and set the keys
				char[] chars = "wasd".toCharArray();
				for(int i = 0; i < 4; i++)
				{
					if(chars[i] == key)
					{
						wasdStatus[i] = true;
					}
				}
			}

			@Override
			public void buttonReleased(char key) 
			{
				char[] chars = "wasd".toCharArray();
				
				for(int i = 0; i < 4; i++)
				{
					if(chars[i] == key)
					{
						wasdStatus[i] = false;
					}
				}	
			}
		});
		
		NetworkQueueController.Instance().setPackageReceivedListener(new PackageReceivedEventListener()
		{	
			@Override
			public void handlePositionsPackage(PositionsPackage pack) 
			{
				PlayerPosition ballPos = pack.get_ballPosition();
				_model.setBallPosition(ballPos.getX(), ballPos.getY() );
				
				List<PlayerPosition> playerPosList = pack.get_playerPositionList();
				_model.setPlayerPositionList(playerPosList);
				
			}
		});
		
		_view.addKeyListener(InputController.Instance());
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
		
//		if(movementKeyIsDown())
//			_model.movePlayer(new Integer(HBallModel.PLAYERTESTID), new Vector2f(horizontal, vertical));	
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
	}
}
