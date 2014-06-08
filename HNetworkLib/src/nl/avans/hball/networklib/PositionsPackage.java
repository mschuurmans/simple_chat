package nl.avans.hball.networklib;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PositionsPackage extends HPackage implements Serializable 
{
	private static final long serialVersionUID = 56996245621303866L;
	
	private int _myId;
	private PlayerPosition _ballPosition;
	private List<PlayerPosition> _playerPositionList = new ArrayList<PlayerPosition>();
	
	public PositionsPackage(int clientId, PlayerPosition ballPosition, List<PlayerPosition> playerPositionList)
	{
		this._myId = clientId;
		this._ballPosition = ballPosition;
		this._playerPositionList = playerPositionList;
	}

	public int get_myId() {
		return _myId;
	}

	public void set_myId(int _myId) {
		this._myId = _myId;
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
