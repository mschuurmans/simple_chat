package nl.avans.hball.models;

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
import nl.avans.hball.entities.PlayerPosition;
import nl.avans.hball.views.BaseFrame;

public class HBallModel implements ActionListener
{
	private final boolean _debug = true;
	
	public static final int MAXPLAYERS = 10;
	
	public static final float PLAYERDIAMETER = 15f;
	public static final float BALLDIAMETER = 5f;
	public static final float BALLMASS = 1000f;
	
	public static final int PLAYERTESTID = 0;
	
	private World _myWorld;
	private Body _floor, _wall;
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
		_floor.setRestitution(1f);
		
		_wall = new StaticBody("wall", new Box(1f, 200f));
		_wall.setPosition(200,400);
		_wall.setRestitution(1f);
		
		_ball = createBall();
		
		_ball.adjustVelocity(new Vector2f(-1000, -1000));
		
		Body player1 = new Body("LocalPlayer", new Circle(PLAYERDIAMETER), 1000f);
		player1.setUserData(new Integer(PLAYERTESTID));
		player1.setPosition(BaseFrame.SCREENWIDTH/8, BaseFrame.SCREENHEIGHT/2);
		player1.setDamping(200f);
		
		_playerList.add(player1);
		
		_myWorld.add(_floor);
		_myWorld.add(_wall);
		_myWorld.add(_ball);
		for(Body body : _playerList)
		{
			_myWorld.add(body);
		}
		
		movePlayer(1, new Vector2f(2000f, 20));
	}
	
	private Body createBall()
	{
		Body ball;
		
		ball = new Body("ball", new Circle(BALLDIAMETER), BALLMASS);
		ball.setPosition(BaseFrame.SCREENWIDTH/2, BaseFrame.SCREENHEIGHT/2);
		ball.setRestitution(1f);
		ball.setDamping(100f);
		
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
				_playerIsKicking = new boolean[MAXPLAYERS];
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
	
	public ROVector2f getBallPosition()
	{
		return _ball.getPosition();
	}
	
	public Body getBall()
	{
		return this._ball;
	}
	
	public Body getFloor()
	{
		return this._floor;
	}
	
	public Body getWall()
	{
		return this._wall;
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
				
				//save a copy of the ball.
				Body ballCopy = createBall();
				ballCopy.setPosition(_ball.getPosition().getX(), _ball.getPosition().getY());
				
				//get Location of the Body that kicks the ball
				float x = body.getPosition().getX();
				float y = body.getPosition().getY();
				//create a new heavy body that is slightly larger. this way the engine can determine where the ball should move
				Body newBody = new Body(new Circle(PLAYERDIAMETER *2), 1000000f);
				//remove the old body to replace it with the heavy newer one
				_myWorld.remove(body);
				newBody.setPosition(x, y);// set new heavy body to location of the old one
				_myWorld.add(newBody);
				_myWorld.step();		// in this step the ball gets relocated next to the bigger heavy body.
				
				ROVector2f ballPlacementPosition = _ball.getPosition();
				//fix the situation.  remove the big body. replace the old playerbody
				_myWorld.remove(newBody);
				_myWorld.add(body);
				_myWorld.remove(_ball);
				System.out.println("ballCopyPosition: " + ballCopy.getPosition());
				System.out.println("ballOrig.position: " + _ball.getPosition());
				_ball = ballCopy;
				_myWorld.add(_ball);
				
				float deltaX =  ballPlacementPosition.getX() - _ball.getPosition().getX();
				float deltaY = ballPlacementPosition.getY() - _ball.getPosition().getY();
				float forceMagnifier = 100f;
				
				Vector2f ballVector = new Vector2f(deltaX * forceMagnifier, deltaY * forceMagnifier);
				_ball.adjustVelocity(ballVector);
				
				// situation now is restored to where it was but we have the ballPlacementPosition. now we need a vector to adjust the ballVelocity towards that point.

				System.out.println("ballkicked");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		update();
	}
}
