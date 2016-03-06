package view.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;

import javax.swing.JPanel;

import dto.GameState;
import dto.PlayerData;
import view.impl.GamePanel.ShipShape;

public class GamePanel extends JPanel {
	GameState state;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Draw Ships
		if (state!=null && state.players!=null){
			g.setColor(Color.BLUE);
			Polygon poly = null;
			for (PlayerData p : state.players.values()) {
				poly = new ShipShape(p.xPos,p.yPos,p.angle);
				g.drawPolygon(poly);
			}
			
		}

	}

	public class ShipShape extends Polygon{

		public ShipShape(double xPos, double yPos, double angle) {
			this(xPos, yPos, angle, 1);
		}

		public ShipShape(double xPos, double yPos, double angle, double scale) {
			super();
			double topX = 0;
			double topY = 10*scale;
			double leftX = -5*scale;
			double leftY = -5*scale;
			double rightX = 5*scale;
			double rightY = -5*scale;
			double cosA = Math.cos(angle);
			double sinA = Math.sin(angle);
			double[] xPoly = {topX,leftX,rightX};
			double[] yPoly = {topY,leftY,rightY};
			for (int i = 0; i < xPoly.length; i++) {
				
				double newX = xPoly[i]*cosA - yPoly[i]*sinA;
				double newY = yPoly[i]*cosA + xPoly[i]*sinA;
				addPoint((int)(newX+xPos), (int)(newY+yPos));
			}			

		}




	}

}
