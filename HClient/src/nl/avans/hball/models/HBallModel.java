package nl.avans.hball.models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import net.phys2d.math.ROVector2f;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.World;
import net.phys2d.raw.shapes.Circle;
import net.phys2d.raw.strategies.BruteCollisionStrategy;
import nl.avans.hball.entities.PlayerPosition;
import nl.avans.hball.views.BaseFrame;

public class HBallModel implements ActionListener
{
	private final boolean _debug = true;
	
	public static final float PLAYERDIAMETER = 30f;
	public static final float BALLDIAMETER = 10f;
	
	private World _myWorld;
	private List<Body> _playerList = new ArrayList<Body>();
	
	private Body _ball;
	
	public HBallModel()
	{
		init();
	}
	
	private void init()
	{
		_myWorld = new World(new Vector2f(0.0f, 0.0f), 10, new BruteCollisionStrategy());
		_myWorld.clear();
		_myWorld.setGravity(0f, 0f);
		
		_ball = new Body("ball", new Circle(10), 1000f);
		_ball.setPosition(BaseFrame.SCREENWIDTH/2, BaseFrame.SCREENHEIGHT/2);
		_ball.setDamping(100f);
		
		_ball.adjustVelocity(new Vector2f(-1000, -1000));
		
		Body player1 = new Body("LocalPlayer", new Circle(30), 1000f);
		player1.setUserData(new Integer(1));
		player1.setPosition(BaseFrame.SCREENWIDTH/8, BaseFrame.SCREENHEIGHT/2);
		player1.setDamping(200f);
		
		_playerList.add(player1);
		
		_myWorld.add(_ball);
		for(Body body : _playerList)
		{
			_myWorld.add(body);
		}
		
		movePlayer(1, new Vector2f(2000f, 20));
	}
	
	public void update()
	{
		for (int i = 0; i < 1; i++)
		{
			_myWorld.step();
		}
	}
	
	public List<Body> getPlayerList()
	{
		return this._playerList;
	}
	
	public List<PlayerPosition> getPlayerPositionList()
	{
		List<PlayerPosition> result = new ArrayList<PlayerPosition>();
		
		for(Body body : _playerList)
		{
			result.add(new PlayerPosition((int)body.getUserData(), body.getPosition().getX(), body.getPosition().getY()));
		}
		
		return result;
	}
	
	public ROVector2f getBallPosition()
	{
		return _ball.getPosition();
	}
	
	public Body getBall()
	{
		return this._ball;
	}
	
	public void movePlayer(int playerId, Vector2f delta)
	{
		System.out.println("MovePlayerCalled");
		for(Body body : _playerList)
		{
			if( playerId == (int)body.getUserData() )
			{
				body.adjustVelocity(delta);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		update();
	}
}
