package nl.avans.hball.controllers;

import nl.avans.hball.models.HBallModel;

public class ObjectHandler
{
	
	private static ObjectHandler _instance = null;
	
	private static HBallModel _model;

	public static ObjectHandler Instance()
	{
		if (_instance == null)
			_instance = new ObjectHandler();

		return _instance;
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
