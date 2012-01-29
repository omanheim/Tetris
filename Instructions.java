import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Instructions extends JTextArea {
	
	public Instructions (int width, int height) {
		setPreferredSize(new Dimension(width,height - 10));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(Color.LIGHT_GRAY);
		setFocusable(true);
		
		this.setLayout(new BorderLayout());
		this.setEditable(false);
		
		String line1 = "        Tetris Instructions\n";
		String game = " Fill complete rows to stop the\n " +
				"blocks from reaching the top!\n\n";
		String line2 = "     ARROWKEY CONTROLS\n";
		String line3 = "LT/RT  -- Move block left or\n    right.\n";
		String line4 = "UP -- Rotate block.\n";
		String rotNote = "**Wall kicks allow for\n**increased mobility!\n";
		String line5 = "DOWN -- Speed up drop.\n";
		String line6 = "SPACEBAR -- Hard drop.\n";
		String line7 = "R -- Reset game.\n";
		String instr = "I -- Instructions.\n\n";
		String line10 = "\"Shadow On\" / \"Shadow Off\"\n";
		String line11 = "...toggle ghost piece\n...visiblity (drop location).\n";
		String line12 = "The \"Next Piece\" window\n...previews the next block.\n";
		String line13 = "Blocks speed up every 10 lines.\n\n";
		String line14 = "GOOD LUCK!\n";
		
		this.append(line1 + game + line2 + line3 + line4 + rotNote + 
				line5 + line6 + line7 + instr + 
				line10 + line11 + line12 + line13 + line14);
		
	}

}
