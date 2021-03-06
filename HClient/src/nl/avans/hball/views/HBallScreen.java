package nl.avans.hball.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import net.phys2d.math.ROVector2f;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.Box;
import nl.avans.hball.models.HBallModel;
import nl.avans.hball.networklib.PlayerPosition;

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
		this.setBackground(Color.green.darker().darker());
		
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
	
	public void drawBox(Graphics2D g2, Body b)
	{
		Box box = (Box) b.getShape();
		Vector2f[] pts = box.getPoints(b.getPosition(), b.getRotation());

		Vector2f p1 = pts[0];
		Vector2f p2 = pts[1];
		Vector2f p3 = pts[2];
		Vector2f p4 = pts[3];
		
		g2.drawLine((int) p1.x,(int) p1.y,(int) p2.x,(int) p2.y);
		g2.drawLine((int) p2.x,(int) p2.y,(int) p3.x,(int) p3.y);
		g2.drawLine((int) p3.x,(int) p3.y,(int) p4.x,(int) p4.y);
		g2.drawLine((int) p4.x,(int) p4.y,(int) p1.x,(int) p1.y);

	}
	
	public void paintComponent(Graphics g1)
	{
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D)g1;
		
		int x, y, diameter;
	
		g.setColor(Color.white);

		g.drawString("Player ID: " + _model.getClientId(), 30, 30);
		
		Font font = new Font("Arial", Font.PLAIN, 20) ;
		g.setFont(font);
		g.drawString("Use the WASD keys to move and the spacebar to kick!", BaseFrame.SCREENWIDTH/2 - 480/2, 50);
		
		//draw the field
		//outline
		g.setStroke(new BasicStroke(4f));
		g.drawRect(100, 100, 1150, 550);
		//center dot
		g.fillOval(660, 360, 30, 30);
		//center circle
		g.drawOval(575, 275, 200, 200);
		//centerLine
		g.drawLine(675, 100, 675, 650);
		//goal areas
		g.drawRect(100, 200, 150, 350);
		g.drawRect(1100, 200, 150, 350);
		//goals
		g.drawRect(100 - 50, 300, 50, 150);
		g.fillOval(100 - 5, 300 -5, 10, 10);
		g.fillOval(100 - 5, 450 -5, 10, 10);
		g.drawRect(1250, 300, 50, 150);
		g.fillOval(1250 - 5, 300 -5, 10, 10);
		g.fillOval(1250 - 5, 450 -5, 10, 10);
		
		
		for (PlayerPosition v : _playerPositions)
		{
			int i = _playerPositions.indexOf(v);
						
			diameter = (int) HBallModel.PLAYERDIAMETER;
			diameter *= 2;
			x = (int) v.getX() -diameter/2;
			y = (int) v.getY() -diameter/2;
			
			if(_model.getClientId() == v.getId())
				g.setColor(Color.red);
			else
				g.setColor(Color.blue);
			
			g.fillOval(x, y, diameter, diameter);
			
			font = new Font("Arial", Font.PLAIN, 35) ;
			g.setFont(font);
			
			g.setColor(Color.white);
			g.drawString("" + v.getId(), x + 20, y + 40);
			
			if(i == _model.getClientId() && _model.getKicking() )
			{
				g.setStroke(new BasicStroke(3));
				g.setColor(Color.white);
			}
			
			g.setStroke(new BasicStroke(1));
			g.setColor(Color.black);
		}
		
		diameter = (int) HBallModel.BALLDIAMETER;
		diameter *= 2;
		x = (int) _ballPosition.getX() -diameter/2;
		y = (int) _ballPosition.getY() -diameter/2;
		
		g.setColor(Color.gray.brighter());
		g.fillOval(x, y, diameter, diameter);
	}
}
