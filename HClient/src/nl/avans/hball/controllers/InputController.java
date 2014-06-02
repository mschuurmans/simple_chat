package nl.avans.hball.controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import nl.avans.hball.events.InputTriggeredEventListener;
import nl.avans.hball.utils.Utils;

public class InputController implements KeyListener 
{
	private InputTriggeredEventListener _listener = null;
	private static InputController _instance;
	
	public static InputController Instance()
	{
		if(_instance == null)
			_instance = new InputController();
		
		return _instance;
	}
	
	public void setInputTriggeredEventListener(InputTriggeredEventListener listener)
	{
		_listener = listener;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(_listener != null)
			_listener.buttonPressed(Utils.convertFromKeyCode(e.getKeyCode()));
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
