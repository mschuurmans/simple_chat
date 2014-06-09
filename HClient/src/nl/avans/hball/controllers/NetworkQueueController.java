package nl.avans.hball.controllers;

import java.util.ArrayList;
import java.util.List;

import nl.avans.hball.events.PackageReceivedEventListener;
import nl.avans.hball.networklib.HPackage;
import nl.avans.hball.networklib.PositionsPackage;

public class NetworkQueueController 
{
	private static NetworkQueueController _instance = null;
	
	private List<HPackage> _packages = new ArrayList<>();
	private PackageReceivedEventListener _listener = null;
	
	public static NetworkQueueController Instance()
	{
		if(_instance == null)
			_instance = new NetworkQueueController();
		
		return _instance;
	}
	
	public void setPackageReceivedListener(PackageReceivedEventListener listener)
	{
		_listener = listener;
	}
	
	public void callPositionsPackageReceived(PositionsPackage p)
	{
		if(_listener != null)
			_listener.handlePositionsPackage(p);
	}
	
	public void add(HPackage pack)
	{
		_packages.add(pack);
	}
	
	public boolean available()
	{
		//System.out.println("Size: " + _packages.size());
		return (_packages.size() > 0) ? true : false;
	}
	
	public HPackage getNext()
	{
		HPackage pack = _packages.get(0);
		_packages.remove(0);
		return pack;
	}
}
