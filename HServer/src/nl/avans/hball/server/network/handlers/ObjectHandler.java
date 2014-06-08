package nl.avans.hball.server.network.handlers;

import nl.avans.hball.server.models.HBallModel;

public class ObjectHandler
{
	
	private static ObjectHandler _instance = null;
	
	private HBallModel _model;

	public static ObjectHandler Instance()
	{
		if (_instance == null)
			_instance = new ObjectHandler();

		return _instance;
	}
	
	public ObjectHandler()
	{
		
	}
	
	public void setModel(HBallModel model)
	{
		this._model = model;
	}
	
	public HBallModel getModel()
	{
		return this._model;
	}

}
