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
	public static final float BALLMASS = 500f;
	public static final float BALLRESTITUTION = 0.4f;
	
	public static final int PLAYERTESTID = 1;
	
	public static final float FIELDWIDTH = 1200;
	public static final float FIELDHEIGHT = 600;
	
	public static final int AMOUNTOFWALLS = 50;
	
	private World _myWorld;
	private Body _floor;
	private Body[] _wall = new Body[AMOUNTOFWALLS];
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
		
		for (int i = 0; i < AMOUNTOFWALLS; i++)
		{
			_wall[i] = new StaticBody("wall", new Box(FIELDWIDTH, FIELDHEIGHT));
			_wall[i].setPosition(50 + i + FIELDWIDTH/2f, 50 + i + FIELDHEIGHT/2f);
			_wall[i].setRestitution(0.8f);
		}
		
		_ball = createBall();
		
		for (int i = 0; i < AMOUNTOFWALLS; i++)
		{
			_myWorld.add(_wall[i]);
		}
		_myWorld.add(_ball);
	}
	
	private Body createBall()
	{
		Body ball;
		
		ball = new Body("ball", new Circle(BALLDIAMETER), BALLMASS);
		ball.setPosition(674, 374);
		ball.setRestitution(BALLRESTITUTION);
		ball.setDamping(50f);
		
		return ball;
	}
	
	private boolean ballIsInField()
	{
		float ballX = _ball.getPosition().getX();
		float ballY = _ball.getPosition().getY();
		
		if(ballX < 100 || ballX > 1250 || ballY < 100 || ballY > 650)
			return false;
		
		else
			return true;
	}
	
	private boolean ballTouchesLeftGoal()
	{
		float ballX = _ball.getPosition().getX();
		float ballY = _ball.getPosition().getY();
		
		if(ballX < 110 && ballY < 450 && ballY > 300)
			return true;
		
		else
			return false;					
	}
	
	private boolean ballTouchesRightGoal()
	{
		float ballX = _ball.getPosition().getX();
		float ballY = _ball.getPosition().getY();
		
		if(ballX > 1250 - 10 && ballY < 450 && ballY > 300)
			return true;
		
		else
			return false;					
	}
	
	public void addNewPlayer()
	{
		Body player = new Body("new Player", new Circle(PLAYERDIAMETER), 1000f);
		int id = _playerList.size();
		player.setUserData(id);
		float newX = (float) (Math.random() * FIELDWIDTH + 100);
		float newY = (float) (Math.random() * FIELDHEIGHT + 100);
		player.setPosition(newX, newY);
		player.setDamping(200f);
		
		_playerList.add(player);
		_myWorld.add(player);
	}
	
	public void update()
	{
		for (int i = 0; i < 1; i++)
		{
			_myWorld.step();
		}
		
		if(!ballIsInField())
		{
			_ball = createBall();
			_myWorld.add(_ball);
		}
		
		if(ballTouchesLeftGoal() || ballTouchesRightGoal())
		{
			_myWorld.remove(_ball);
			_ball = createBall();
			_myWorld.add(_ball);
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
	
	public PlayerPosition getBallPosition()
	{
		ROVector2f pos = _ball.getPosition();
		return new PlayerPosition(-1, pos.getX(), pos.getY());
	}
	public void clearPlayerFromScreen(int id)
	{
		_playerList.get(id).setPosition(-200, 0);
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
