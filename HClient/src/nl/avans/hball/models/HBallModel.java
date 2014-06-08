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
	
	public static final float PLAYERDIAMETER = 30f;
	public static final float BALLDIAMETER = 15f;
	
	private List<PlayerPosition> _playerPositionList = new ArrayList<PlayerPosition>();
	
	private ROVector2f _ballPosition = new Vector2f();
	
	public HBallModel()
	{
		init();
	}
	
	private void init()
	{
		
	}
	
	public void update()
	{
		
	}
	
	public List<PlayerPosition> getPlayerPositionList()
	{
		return this._playerPositionList;
	}
	
	public void setPlayerPositionList(List<PlayerPosition> list)
	{
		this._playerPositionList = list;
	}
	
	public ROVector2f getBallPosition()
	{
		return _ballPosition;
	}
	
	public void setBallPosition(ROVector2f bp)
	{
		this._ballPosition = bp;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		update();
	}
}
