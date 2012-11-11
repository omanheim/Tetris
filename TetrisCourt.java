/**
 * @author Oliver Manheim
 */

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;

import javax.swing.*;

@SuppressWarnings("serial")
public class TetrisCourt extends JPanel {
	private String thisTet;
	private Tetromino t;
	private Tetromino shadow;
	private boolean lock;
	private HashMap<Point, Block> blocks = new HashMap<Point, Block>();

	private int interval = 1000; // Milliseconds between updates.
	private Timer timer; // Each time timer fires we animate one step.

	final int COURTWIDTH = 200;
	final int COURTHEIGHT = 400;

	PreviewFrame previewFrame;
	private String next;

	private InfoLine lines;
	private InfoLine score;
	private InfoLine level;
	
	private boolean isShadowOn;

	private Color color;

	public TetrisCourt(InfoLine level, InfoLine lines, InfoLine score,
			PreviewFrame previewF, final Instructions instructions) {
		setPreferredSize(new Dimension(COURTWIDTH, COURTHEIGHT));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(Color.LIGHT_GRAY);
		setFocusable(true);
		
		this.add(instructions);
		instructions.setVisible(false);

		timer = new Timer(interval, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start();

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					if (!lock && !instructions.isShowing()) {
						t.moveX("left", blocks.keySet());
						shadow = setTet(thisTet);
						shadow.getToState(t);
						shadow.setX(t.getX());
						shadow.setY(t.getY());
						shadowAct();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (!lock && !instructions.isShowing()) {
						t.moveX("right", blocks.keySet());
						shadow = setTet(thisTet);
						shadow.getToState(t);
						shadow.setX(t.getX());
						shadow.setY(t.getY());
						shadowAct();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_R) {
					reset();
				} else if (e.getKeyCode() == KeyEvent.VK_I) {
					instructions.setVisible(!instructions.isShowing());
					if (timer.isRunning())
						timer.stop();
					else
						timer.start();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (!instructions.isShowing())
						timer.setDelay(60);
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (!instructions.isShowing()) {
						while (t.canMoveY(blocks.keySet()))
							t.moveY();
						nextBlock();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_UP && 
						!instructions.isShowing())
					if (!t.rotate(blocks.keySet())) {
						t.rotateBack(blocks.keySet());
						if (t.wallKick(t.getCoords(), blocks.keySet())) {
							shadow = setTet(thisTet);
							shadow.getToState(t);
							shadow.setX(t.getX());
							shadow.setY(t.getY());
							shadowAct();
						}
					} else {
						shadow = setTet(thisTet);
						shadow.getToState(t);
						shadow.setX(t.getX());
						shadowAct();
					}
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					timer.setDelay(interval);
				}
				repaint();
			}
		});

		this.lines = lines;
		this.score = score;
		this.level = level;
		level.newValue(1);
		lines.newValue(0);
		score.newValue(0);

		previewFrame = previewF;
		
		isShadowOn = true;
		lock = false;
	}

	// Resets the game
	public void reset() {
		blocks.clear();
		thisTet = randomTet();
		t = setTet(thisTet);
		shadow = setTet(thisTet);
		shadow.setX(t.getX());
		shadowAct();
		next = previewFrame.randomTet();
		level.newValue(1);
		lines.newValue(0);
		score.newValue(0);
		grabFocus();
		interval = 1000;
		timer.setDelay(interval);
		timer.start();
	}

	public void shadowOn() {
		isShadowOn = true;
		repaint();
	}
	
	public void shadowOff() {
		isShadowOn = false;
		repaint();
	}
	
	// Locks the active tetr. on the screen and starts a new one moving
	public void nextBlock() {
		if (!lock()) {
			gameOver();
			return;
		}
		thisTet = next;
		t = setTet(next);
		shadow = setTet(next);
		shadow.setX(t.getX());
		shadowAct();
		next = previewFrame.randomTet();
		for (Point p : t.getCoords())
			if (blocks.keySet().contains(p))
				gameOver();
		grabFocus();
	}

	private void shadowAct() {
		while (shadow.canMoveY(blocks.keySet()))
			shadow.moveY();
	}

	// Records the position of the tetr. so it remains on the screen, checks for
	// completed lines, and updates the score. If the tetr. is at the top of the
	// field, the game ends.
	public boolean lock() {
		try {
			for (Point p : t.getCoords()) {
				if (p.y < 0) {
					return false;
				}
				Block b = new Block(p.x, p.y, true);
				b.setColor(t.getColor());
				blocks.put(new Point(p.x, p.y), b);
			}
			lock = false;
			updateScore();
			checkLines();
			timer.setDelay(interval);
			return true;
		} catch (NoSuchElementException e) {
			return true;
		} catch (NullPointerException e) {
			return true;
		}
	}

	// End the game
	public void gameOver() {
		timer.stop();
		JOptionPane.showMessageDialog(this, "Game Over");
		reset();
	}

	// Updates the score by adding the number of rows by which the tetromino
	// has just dropped
	public void updateScore() {
		int min = t.getCoords()[0].y;
		for (int i = 1; i < 4; i++)
			if (t.getCoords()[0].y < min)
				min = t.getCoords()[0].y;
		score.updateBy(min / 20);
	}

	// If the active tetr. can move, it moves; otherwise, the next one starts
	void tick() {
		try {
			if (t.canMoveY(blocks.keySet())) {
				t.moveY();
			} else {
				lock = true;
				nextBlock();
			}
			repaint();
		}
		catch (NullPointerException e) {
			t.moveY();
		}
	}

	// Checks for completed lines
	public void checkLines() {
		for (int y = 0; y < 400; y += 20) {
			LinkedList<Point> line = createLine(y);
			if (blocks.keySet().containsAll(line)) {
				removeLine(line, y);
				lines.updateValue();
				if (lines.currentValue() % 10 == 0) {
					if (lines.currentValue() > 500)
						interval = timer.getDelay() - 200;
					else
						interval = timer.getDelay() / 2;
					timer.setDelay(interval);
					level.updateValue();
				}
			}
		}
	}

	// Returns a full line of points at a given row
	private LinkedList<Point> createLine(int y) {
		LinkedList<Point> line = new LinkedList<Point>();
		for (int x = 0; x < 200; x += 20) {
			line.add(new Point(x, y));
		}
		return line;
	}

	// Removes a given line from the screen and moves everything above
	// it down a row. Also updates the level and speed every two lines.
	// Also adds 50 to the score.
	private void removeLine(LinkedList<Point> line, int lineY) {
		for (Point p : line) {
			blocks.remove(p);
		}
		for (int y = lineY; y > 0; y--) {
			for (int x = 0; x < 200; x += 20) {
				if (blocks.keySet().contains(new Point(x, y))) {
					Block b = new Block(x, y + 20, true);
					b.setColor(blocks.get(new Point(x, y)).getColor());
					blocks.remove(new Point(x, y));
					blocks.put(new Point(x, y + 20), b);
				}
			}
		}
		score.updateBy(50);
	}

	// Returns the timer
	public Timer getTimer() {
		return timer;
	}
	
	// Generates a random tetromino
	public Tetromino setTet(String shape) {
		if (shape.equals("s"))
			return sTet();
		else if (shape.equals("l"))
			return lTet();
		else if (shape.equals("i"))
			return iTet();
		else if (shape.equals("t"))
			return tTet();
		else if (shape.equals("o"))
			return oTet();
		else if (shape.equals("j"))
			return jTet();
		else if (shape.equals("z"))
			return zTet();
		else
			return iTet();
	}

	// Generates a random tetromino
	public String randomTet() {
		Random generator = new Random();
		int rand = generator.nextInt(7);
		if (rand == 0) {
			return "s";
		} else if (rand == 1) {
			return "l";
		} else if (rand == 2) {
			return "i";
		} else if (rand == 3) {
			return "t";
		} else if (rand == 4) {
			return "o";
		} else if (rand == 5) {
			return "j";
		} else if (rand == 6) {
			return "z";
		} else {
			return "i";
		}
	}
	
	// Creates an 'O' shaped tetr.
	public Tetromino oTet() {
		return new oTet(getWidth(), getHeight());
	}

	// Creates a 'J' shaped tetr.
	public Tetromino jTet() {
		return new jTet(getWidth(), getHeight());
	}

	// Creates an 'L' shaped tetr.
	public Tetromino lTet() {
		return new lTet(getWidth(), getHeight());
	}

	// Creates an 'I' shaped tetr.
	public Tetromino iTet() {
		return new iTet(getWidth(), getHeight());
	}

	// Creates a 'T' shaped tetr.
	public Tetromino tTet() {
		return new tTet(getWidth(), getHeight());
	}

	// Creates a 'Z' shaped tetr.
	public Tetromino zTet() {
		return new zTet(getWidth(), getHeight());
	}

	// Creates an 'S' shaped tetr.
	public Tetromino sTet() {
		return new sTet(getWidth(), getHeight());
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Block b : blocks.values()) {
			color = b.getColor();
			b.setColor(color);
			b.draw(g);
		}
		t.draw(g, true);
		if (isShadowOn)
			shadow.draw(g, false);
		previewFrame.repaint();
	}
}
