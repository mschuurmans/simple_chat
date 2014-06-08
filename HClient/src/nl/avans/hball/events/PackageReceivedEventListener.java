package nl.avans.hball.events;

import nl.avans.hball.networklib.PositionsPackage;

public abstract class PackageReceivedEventListener 
{
	public abstract void handlePositionsPackage(PositionsPackage pack);
}
