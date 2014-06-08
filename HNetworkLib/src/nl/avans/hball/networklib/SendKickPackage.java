package nl.avans.hball.networklib;

import java.io.Serializable;

public class SendKickPackage extends HPackage implements Serializable 
{
	private static final long serialVersionUID = -8876001371654414366L;
	private int _id;
	
	public SendKickPackage(int id)
	{
		this.set_id(id);
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

}
