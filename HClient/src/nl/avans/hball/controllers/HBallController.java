package nl.avans.hball.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
				
				if(key == ' ')
				{
					NetworkQueueController.Instance().add( new SendKickPackage() );
					_model.setKicking(true);
				}
				
				//check and set the movement Keys
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
		int id = _model.getClientId();
		
		if (wasdStatus[0])
		{
			NetworkQueueController.Instance().add(new MovePackage(MoveDirections.Up));
		}
		
		if (wasdStatus[1])
		{
			NetworkQueueController.Instance().add(new MovePackage(MoveDirections.Left));
		}
		
		if (wasdStatus[2])
		{
			NetworkQueueController.Instance().add(new MovePackage(MoveDirections.Down));
		}
		
		if (wasdStatus[3])
		{
			NetworkQueueController.Instance().add(new MovePackage(MoveDirections.Right));
		}	
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
