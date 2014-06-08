package nl.avans.hball.controllers;

import nl.avans.hball.models.HBallModel;

public class ObjectHandler
{
	
	private static ObjectHandler _instance = null;
	
	private HBallModel _model;
	private HBallController _controller;

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

	public HBallController get_controller() {
		return _controller;
	}

	public void set_controller(HBallController _controller) {
		this._controller = _controller;
	}

}
