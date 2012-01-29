import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Set;

public class oTet extends Tetromino {

	int width;
	int height;

	final int blockWidth = 20;
	final int blockHeight = 20;

	final private Color color = Color.YELLOW;
	
	public oTet(Point[] start, int CW, int CH) {
		super(start, CW, CH);
	}
		
	public oTet(int CW, int CH) {
		super(new Point[] { new Point(80, 0), new Point(100, 0),
				new Point(80, 20), new Point(100, 20) }, CW, CH);
	}

	// Rotate does nothing for this shape
	public boolean rotate(Set<Point> taken) { return false; }
	public void rotateBack(Set<Point> taken) {}
	public boolean wallKick(Point[] coords, Set<Point> taken) { return false; }
	
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
