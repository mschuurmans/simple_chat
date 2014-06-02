package nl.avans.hball.events;

import nl.avans.hball.utils.Enums.GameKeys;


public abstract class InputTriggeredEventListener 
{
	public abstract void buttonPressed(GameKeys key);

}
