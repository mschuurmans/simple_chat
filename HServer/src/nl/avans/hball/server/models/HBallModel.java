package nl.avans.hball.server.models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import net.phys2d.math.ROVector2f;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.World;
import net.phys2d.raw.shapes.Box;
import net.phys2d.raw.shapes.Circle;
import net.phys2d.raw.strategies.BruteCollisionStrategy;
import nl.avans.hball.networklib.PlayerPosition;

public class HBallModel implements ActionListener, Cloneable
{
	private final boolean _debug = true;
	
	public static final int MAXPLAYERS = 10;
	
	public static final float PLAYERDIAMETER = 30f;
	public static final float BALLDIAMETER = 15f;
	public static final float BALLMASS = 1000f;
	
	public static final int PLAYERTESTID = 0;
	
	public static final float FIELDWIDTH = 1200;
	public static final float FIELDHEIGHT = 600;
	
	public static final int AMOUNTOFWALLS = 50;
	
	private World _myWorld;
	private Body _floor;
	private Body[] _wall = new Body[AMOUNTOFWALLS];
	private List<Body> _playerList = new ArrayList<Body>();
	private boolean[] _playerIsKicking = new boolean[MAXPLAYERS];
	
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
		
		_floor = new StaticBody("Ground", new Box(1400f, 1f));
		_floor.setPosition(0, 600);
		_floor.setRestitution(0.8f);
		
		for (int i = 0; i < AMOUNTOFWALLS; i++)
		{
			_wall[i] = new StaticBody("wall", new Box(FIELDWIDTH, FIELDHEIGHT));
			_wall[i].setPosition(50 + i + FIELDWIDTH/2f, 50 + i + FIELDHEIGHT/2f);
			_wall[i].setRestitution(0.8f);
		}
		
		_ball = createBall();
		
		Body player1 = new Body("LocalPlayer", new Circle(PLAYERDIAMETER), 1000f);
		player1.setUserData(new Integer(PLAYERTESTID));
		player1.setPosition(1200, 380);
		player1.setDamping(200f);
		
		_playerList.add(player1);
		
		for (int i = 0; i < AMOUNTOFWALLS; i++)
		{
			_myWorld.add(_wall[i]);
		}
		_myWorld.add(_floor);
		_myWorld.add(_ball);
		for(Body body : _playerList)
		{
			_myWorld.add(body);
		}
	}
	
	private Body createBall()
	{
		Body ball;
		
		ball = new Body("ball", new Circle(BALLDIAMETER), BALLMASS);
		ball.setPosition(674, 374);
		ball.setRestitution(0.9f);
		ball.setDamping(50f);
		
		return ball;
	}
	
	public void update()
	{
		for (int i = 0; i < 1; i++)
		{
			_myWorld.step();
		}
		
		for (int i = 0; i < 10; i++)
		{
			//reset the array
			if(i == 9)
			{
				_playerIsKicking = new boolean[MAXPLAYERS];
			}
		}
		
	}
	
	public List<Body> getPlayerList()
	{
		return this._playerList;
	}
	
	public boolean[] getKickingPlayers()
	{
		return this._playerIsKicking;
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
	
	public PlayerPosition getBallPosition()
	{
		ROVector2f pos = _ball.getPosition();
		return new PlayerPosition(-1, pos.getX(), pos.getY());
	}
	
	public Body getBall()
	{
		return this._ball;
	}
	
	public Body getFloor()
	{
		return this._floor;
	}
	
	public Body[] getWall()
	{
		return this._wall;
	}
	
	public void playerHasJoined()
	{
		_ball.adjustVelocity(new Vector2f(2000f, 2000f));
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
	
	public void ballKick(int playerId)
	{
		for(Body body : _playerList)
		{
			if( playerId == (int)body.getUserData() )
			{
				_playerIsKicking[playerId] = true;
				
				if(body.getPosition().distance(_ball.getPosition()) > 100) // if the ball is not near.. return operation
						return;
				
				float deltaX = _ball.getPosition().getX() - body.getPosition().getX();
				float deltaY = _ball.getPosition().getY() - body.getPosition().getY();
				float forceMagnifier = 40f;
				
				// situation now is restored to where it was but we have the ballPlacementPosition. now we need a vector to adjust the ballVelocity towards that point.
				Vector2f ballVector = new Vector2f(deltaX * forceMagnifier, deltaY * forceMagnifier);
				_ball.adjustVelocity(ballVector);
				
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		update();
	}
}
