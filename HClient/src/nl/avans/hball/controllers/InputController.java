package nl.avans.hball.controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.lang.model.element.ElementKind;

import nl.avans.hball.events.InputTriggeredEventListener;
import nl.avans.hball.utils.Utils;
import nl.avans.hball.utils.Enums.GameKeys;

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
		System.out.println("ADDED: " + _listener);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		char c = e.getKeyChar();
			if(_listener != null)
			_listener.buttonPressed(c);
	}

	@Override
	public void keyReleased(KeyEvent e)
	{	
		char c = e.getKeyChar();
		
		if(_listener != null)
			_listener.buttonReleased(c);
	}

}
