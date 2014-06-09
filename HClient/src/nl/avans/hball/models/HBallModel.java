package nl.avans.hball.models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import net.phys2d.math.ROVector2f;
import net.phys2d.math.Vector2f;
import nl.avans.hball.networklib.PlayerPosition;

public class HBallModel implements ActionListener
{
	private final boolean _debug = true;
	
	public static final float PLAYERDIAMETER = 30f;
	public static final float BALLDIAMETER = 15f;
	
	private boolean _localPlayerKicking;
	
	private int _clientId;
	
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
		this._localPlayerKicking = false;
	}
	
	public void setKicking(boolean bool)
	{
		this._localPlayerKicking = bool;
	}
	
	public boolean getKicking()
	{
		return this._localPlayerKicking;
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
	
	public void setBallPosition(float x, float y)
	{
		ROVector2f pos = new Vector2f(x, y);
		this._ballPosition = pos;
		//System.out.println(pos.getX() + pos.getY());
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		update();
	}

	public int getClientId() {
		return _clientId;
	}

	public void setClientId(int _clientId) {
		this._clientId = _clientId;
	}
}
