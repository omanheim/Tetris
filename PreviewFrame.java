/**
 * @author Oliver Manheim
 */

import java.awt.*;
import java.util.Random;

import javax.swing.*;

@SuppressWarnings("serial")
public class PreviewFrame extends JPanel {
	private Tetromino preview;

	final int COURTWIDTH = 100;
	final int COURTHEIGHT = 100;

	PreviewFrame previewFrame;

	public PreviewFrame() {
		setPreferredSize(new Dimension(COURTWIDTH, COURTHEIGHT));
		setBorder(BorderFactory.createTitledBorder("Next Piece"));
		setBackground(Color.GRAY);
		setFocusable(true);
	}

	// Generates a random tetromino
	public String randomTet() {
		String sh = "";
		Random generator = new Random();
		int rand = generator.nextInt(7);
		if (rand == 0) {
			sTet();
			sh = "s";
		} else if (rand == 1) {
			lTet();
			sh = "l";
		} else if (rand == 2) {
			iTet();
			sh = "i";
		} else if (rand == 3) {
			tTet();
			sh = "t";
		} else if (rand == 4) {
			oTet();
			sh = "o";
		} else if (rand == 5) {
			jTet();
			sh = "j";
		} else if (rand == 6) {
			zTet();
			sh = "z";
		} else {
			System.out.println("Error");
		}
		repaint();
		return sh;
	}

	// Creates an 'O' shaped tetr.
	public void oTet() {
		preview = new oTet(new Point[] { new Point(70, 40), new Point(90, 40),
				new Point(70, 60), new Point(90, 60) }, getWidth(), getHeight());
	}

	// Creates a 'J' shaped tetr.
	public void jTet() {
		preview = new jTet(new Point[] { new Point(50, 40), new Point(50, 60),
				new Point(70, 60), new Point(90, 60) }, getWidth(), getHeight());
	}

	// Creates an 'L' shaped tetr.
	public void lTet() {
		preview = new lTet(new Point[] { new Point(50, 60), new Point(70, 60),
				new Point(90, 60), new Point(90, 40) }, getWidth(), getHeight());
	}

	// Creates an 'I' shaped tetr.
	public void iTet() {
		preview = new iTet(new Point[] { new Point(50, 60), new Point(70, 60),
				new Point(90, 60), new Point(110, 60) }, getWidth(),
				getHeight());
	}

	// Creates a 'T' shaped tetr.
	public void tTet() {
		preview = new tTet(new Point[] { new Point(50, 60), new Point(70, 60),
				new Point(70, 40), new Point(90, 60) }, getWidth(),
				getHeight());
	}

	// Creates a 'Z' shaped tetr.
	public void zTet() {
		preview = new zTet(new Point[] { new Point(50, 40), new Point(70, 40),
				new Point(70, 60), new Point(90, 60) }, getWidth(), getHeight());
	}

	// Creates an 'S' shaped tetr.
	public void sTet() {
		preview = new sTet(new Point[] { new Point(50, 60), new Point(70, 60),
				new Point(70, 40), new Point(90, 40) }, getWidth(), getHeight());
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		preview.draw(g, true);
	}
}
