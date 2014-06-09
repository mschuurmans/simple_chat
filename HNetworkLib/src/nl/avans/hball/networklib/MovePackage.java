package nl.avans.hball.networklib;

import java.io.Serializable;

import nl.avans.hball.networklib.EnumsNetwork.MoveDirections;

public class MovePackage extends HPackage implements Serializable
{
	private static final long serialVersionUID = 3238065144411468469L;

	private MoveDirections _dir;
	
	public MovePackage(MoveDirections dir)
	{
		_dir = dir;
	}
	
	public MoveDirections getDirection()
	{
		return _dir;
	}
	
}
