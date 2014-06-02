package nl.avans.hball.views;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import nl.avans.hball.models.HBallModel;

public class HBallScreen extends JPanel implements ActionListener 
{
	private static final long serialVersionUID = -7486919645597121411L;
	
	private HBallModel _model;
	
	public HBallScreen(HBallModel model)
	{
		this._model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
}
