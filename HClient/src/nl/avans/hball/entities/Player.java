
package nl.avans.hball.entities;

import net.phys2d.math.ROVector2f;
import net.phys2d.raw.Body;

public class Player implements Comparable
{
	private Body _body;
	private float _x;
	private float _y;
	
	public Player(Body body)
	{
		this._body = body;
		
		ROVector2f location = body.getPosition();
		this._x = location.getX();
		this._y = location.getY();
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
		return (int)_body.getUserData();
	}
	
	public Body getBody()
	{
		return this._body;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return ((int)this.getId()) - ((Player)o).getId();
	}
	
}
