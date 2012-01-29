import java.awt.*;

public class Block {

	int x;
	int y;
	private int width;
	private int height;
	Color color;
	private boolean isT;

	public Block(int x, int y, boolean isT) {
		this.x = x;
		this.y = y;
		width = 20;
		height = 20;
		this.isT = isT;
	}
	
	public Block(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void draw(Graphics g) {
		g.setColor(color.darker());
		if (isT) {
			g.fill3DRect(x, y, width, height, true);
		}
		else
			g.draw3DRect(x, y, width, height, true);
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

	public Point getCoords() {
		return new Point(x, y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}