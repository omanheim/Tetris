import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Set;

public class sTet extends Tetromino {

	int width;
	int height;

	final int blockWidth = 20;
	final int blockHeight = 20;
	
	final private Color color = Color.GREEN;
	
	public sTet(Point[] start, int CW, int CH) {
		super(start, CW, CH);
	}
	
	public sTet(int CW, int CH) {
		super(new Point[] { new Point(80, 20),new Point(100, 20), 
				new Point(100, 0), new Point(120, 0) }, CW, CH);
	}

	// Rotate
	public boolean rotate(Set<Point> taken) {
		Point[] newC = getCoords();
		switch (st) {
		case s1:
			newC[0].x += 40;
			newC[0].y += 20;
			newC[3].y += 20;
			st = state.s2;
			break;
		case s2:
			newC[0].x -= 20;
			newC[2].x -= 20;
			newC[2].y += 40;
			st = state.s3;
			break;
		case s3:
			newC[2].y -= 20;
			newC[3].x -= 40;
			newC[3].y -= 20;
			st = state.s4;
			break;
		case s4:
			newC[0].x -= 20;
			newC[0].y -= 20;
			newC[2].x += 20;
			newC[2].y -= 20;
			newC[3].x += 40;
			st = state.s1;
			break;
		}
		for (Point p : newC) {
			if (taken.contains(p) || p.x < 0 || p.y > bottomBound - 20
					|| p.x > rightBound - 20 || p.y < 0) {
				return false;
			}
		}
		return true;
	}

	// Rotate back if rotation was out of bounds
	public void rotateBack(Set<Point> taken) {
		Point[] newC = getCoords();
		switch (st) {
		case s1:
			st = state.s4;
			break;
		case s2:
			st = state.s1;
			break;
		case s3:
			st = state.s2;
			break;
		case s4:
			st = state.s3;
			break;
		}
		switch (st) {
		case s1:
			newC[0].x -= 40;
			newC[0].y -= 20;
			newC[3].y -= 20;
			break;
		case s2:
			newC[0].x += 20;
			newC[2].x += 20;
			newC[2].y -= 40;
			break;
		case s3:
			newC[2].y += 20;
			newC[3].x += 40;
			newC[3].y += 20;
			break;
		case s4:
			newC[0].x += 20;
			newC[0].y += 20;
			newC[2].x -= 20;
			newC[2].y += 20;
			newC[3].x -= 40;
			break;
		}
	}
	
	public boolean wallKick(Point[] coords, Set<Point> taken) {
		for (Point p : coords) {
			p.x += 20;
		}
		if (!this.rotate(taken)) {
			this.rotateBack(taken);
			for (Point p : coords) {
				p.x -= 20;
			}
			for (Point p : coords) {
				p.x -= 20;
			}
			if (!this.rotate(taken)) {
				this.rotateBack(taken);
				for (Point p : coords) {
					p.x += 20;
				}
				return false;
			}
		}
		return true;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void draw(Graphics g, boolean isT) {
		for (Point p : getCoords()) {
			Block b = new Block(p.x, p.y, isT);
			b.setColor(color);
			b.draw(g);
		}
	}

}
