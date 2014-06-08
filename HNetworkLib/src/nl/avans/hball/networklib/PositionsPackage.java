package nl.avans.hball.networklib;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PositionsPackage extends HPackage implements Serializable 
{
	private static final long serialVersionUID = 56996245621303866L;
	
	private PlayerPosition _ballPosition;
	private List<PlayerPosition> _playerPositionList = new ArrayList<PlayerPosition>();
	
	public PositionsPackage(PlayerPosition ballPosition, List<PlayerPosition> playerPositionList)
	{
		this._ballPosition = ballPosition;
		this._playerPositionList = playerPositionList;
	}

	public PlayerPosition get_ballPosition() {
		return _ballPosition;
	}

	public void set_ballPosition(PlayerPosition _ballPosition) {
		this._ballPosition = _ballPosition;
	}

	public List<PlayerPosition> get_playerPositionList() {
		return _playerPositionList;
	}

	public void set_playerPositionList(List<PlayerPosition> _playerPositionList) {
		this._playerPositionList = _playerPositionList;
	}

}
