/**
 * @author Oliver Manheim
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public abstract class Tetromino {
	private Point[] coords;

	int rightBound;
	int bottomBound;

	int width;
	int height;

	private int blockWidth;
	private int blockHeight;

	public enum state {
		s1, s2, s3, s4
	}

	protected state st;
	private boolean isT;

	public Tetromino(Point[] coords, int CW, int CH) {
		this.coords = coords;
		this.rightBound = CW;
		this.bottomBound = CH;
		st = state.s1;
		blockWidth = 20;
		blockHeight = 20;
	}

	public void makeT() {
		isT = true;
	}

	public boolean ifT() {
		return isT;
	}

	public void getToState(Tetromino other) {
		while (!this.getState().equals(other.getState())) {
			this.rotate(new HashSet<Point>());
		}
	}

	// Sets the x-coordinates of the tetromino
	public void setX(int[] xs) {
		for (int i = 0; i < coords.length; i++)
			coords[i].x = xs[i];
	}

	// Sets the x-coordinates of the tetromino
	public void setY(int[] ys) {
		for (int i = 0; i < coords.length; i++)
			coords[i].y = ys[i];
	}

	// Returns the current x coordinates of the tetromino
	public int[] getY() {
		int[] ys = new int[coords.length];
		for (int i = 0; i < coords.length; i++)
			ys[i] = coords[i].y;
		return ys;
	}
	
	public state getState() {
		return st;
	}

	public state getLastState() {
		switch (st) {
		case s1:
			return state.s4;
		case s2:
			return state.s1;
		case s3:
			return state.s2;
		case s4:
			return state.s3;
		}
		return state.s1;
	}

	public void setState(state s) {
		st = s;
	}

	// Returns the current coordinates of the tetromino
	public Point[] getCoords() {
		return coords;
	}

	// Returns the current x coordinates of the tetromino
	public int[] getX() {
		int[] xs = new int[coords.length];
		for (int i = 0; i < coords.length; i++)
			xs[i] = coords[i].x;
		return xs;
	}

	// Move the tetromino the height of a block.
	public void moveY() {
		for (Point p : coords)
			p.y = p.y += blockHeight;
	}

	public boolean canMoveY(Set<Point> taken) {
		for (Point p : coords) {
			if (p.y + blockHeight == bottomBound)
				return false;
			if (taken.contains(new Point(p.x, p.y + blockHeight)))
				return false;
		}
		return true;
	}

	// Move the tetromino left or right the width of a block if able
	public void moveX(String dir, Set<Point> taken) {
		boolean canMove = true;
		if (dir.equals("right")) {
			for (Point p : coords) {
				if (p.x + blockWidth == rightBound)
					canMove = false;
				if (taken.contains(new Point(p.x + blockWidth, p.y)))
					canMove = false;
			}
			if (canMove) {
				for (Point p : coords)
					p.x = p.x += blockWidth;
			}
		}
		if (dir.equals("left")) {
			for (Point p : coords) {
				if (p.x == 0)
					canMove = false;
				if (taken.contains(new Point(p.x - blockWidth, p.y)))
					canMove = false;
			}
			if (canMove) {
				for (Point p : coords) {
					p.x = p.x -= blockWidth;
				}
			}
		}
	}

	// Move the tetromino left or right the width of a block
	public void moveX(String dir) {
		if (dir.equals("right")) {
			for (Point p : coords)
				p.x = p.x += blockWidth;

		}
		if (dir.equals("left")) {
			for (Point p : coords) {
				p.x = p.x -= blockWidth;
			}

		}
	}

	public abstract boolean rotate(Set<Point> taken);

	public abstract void rotateBack(Set<Point> taken);

	public abstract boolean wallKick(Point[] coords, Set<Point> taken);

	public abstract void draw(Graphics g, boolean isT);

	public abstract Color getColor();
}
