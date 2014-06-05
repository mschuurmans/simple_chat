
package nl.avans.hball.entities;


public class PlayerPosition implements Comparable<Integer>
{
	private int _id;
	private float _x;
	private float _y;
	
	public PlayerPosition(int id, float x, float y)
	{
		this._id = id;
		this._x = x;
		this._y = y;
	}
	
	public float getX()
	{
		return this._x;
	}
	
	public float getY()
	{
		return this._y;
	}
	
	public int getId()
	{
		return this._id;
	}

	@Override
	public int compareTo(Integer arg0)
	{
		return this._id - arg0;
	}
	
}
