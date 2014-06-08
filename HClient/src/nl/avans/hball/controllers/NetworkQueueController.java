package nl.avans.hball.controllers;

import java.util.ArrayList;
import java.util.List;

import nl.avans.hball.networklib.HPackage;

public class NetworkQueueController 
{
	private static NetworkQueueController _instance = null;
	
	private List<HPackage> _packages = new ArrayList<>();
	
	public static NetworkQueueController Instance()
	{
		if(_instance == null)
			_instance = new NetworkQueueController();
		
		return _instance;
	}
	
	public void add(HPackage pack)
	{
		_packages.add(pack);
	}
	
	public boolean available()
	{
		return (_packages.size() > 0) ? true : false;
	}
	
	public HPackage getNext()
	{
		HPackage pack = _packages.get(0);
		_packages.remove(0);
		return pack;
	}
}
