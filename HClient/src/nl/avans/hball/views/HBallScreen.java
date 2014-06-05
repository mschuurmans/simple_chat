package nl.avans.hball.views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import net.phys2d.math.ROVector2f;
import nl.avans.hball.entities.PlayerPosition;
import nl.avans.hball.models.HBallModel;

public class HBallScreen extends JPanel implements ActionListener 
{
	private static final long serialVersionUID = -7486919645597121411L;
	
	private HBallModel _model;
	
	private List<PlayerPosition> _playerPositions = new ArrayList<PlayerPosition>();
	private ROVector2f _ballPosition;
	
	private Timer _timer;
	
	public HBallScreen(HBallModel model)
	{
		this._model = model;
		
		this.setFocusable(true);
		_timer = new Timer(60, this);
		_timer.start(); 
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		_playerPositions = _model.getPlayerPositionList();
		_ballPosition = _model.getBallPosition();
		
		repaint();
	}
	
	public void paintComponent(Graphics g1)
	{
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D)g1;
		
		int x, y, diameter;
		
		for (PlayerPosition v : _playerPositions)
		{
			x = (int) v.getX();
			y = (int) v.getY();
			diameter = (int) HBallModel.PLAYERDIAMETER;
			g.drawOval(x, y, diameter, diameter);
		}
		
		x = (int) _ballPosition.getX();
		y = (int) _ballPosition.getY();
		diameter = (int) HBallModel.BALLDIAMETER;
		
		
		g.drawOval(x, y, diameter, diameter);
	}
}
