package view.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;

import javax.swing.JPanel;

import dto.GameState;
import dto.GameState.playerData;
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
			for (playerData p : state.players) {
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
			double cosA = Math.cos(angle);System.out.println(cosA);
			double sinA = Math.sin(angle);System.out.println(sinA);
			double[] xPoly = {topX,leftX,rightX};
			double[] yPoly = {topY,leftY,rightY};
			for (int i = 0; i < xPoly.length; i++) {
				xPoly[i] = xPoly[i]*cosA - yPoly[i]*sinA;
				yPoly[i] = yPoly[i]*cosA + xPoly[i]*sinA;
				System.out.println(xPoly[i]  + ", " + yPoly[i]);
				addPoint((int)(xPoly[i]+xPos), (int)(yPoly[i]+yPos));
			}			

		}




	}

}
