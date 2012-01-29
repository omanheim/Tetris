import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Game extends JPanel {

	public Game() {
		
		// Top-level frame
		//final JFrame frame = new JFrame("Tetris");
		//frame.setLocation(200, 200);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		// Side panel
		final JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
		sidePanel.setSize(new Dimension(200,400));

		// Next block frame
		final PreviewFrame preview = new PreviewFrame();
		sidePanel.add(preview);

		// Add game info lines
		InfoLine level = new InfoLine("Level", sidePanel);
		InfoLine lines = new InfoLine("Lines", sidePanel);
		InfoLine score = new InfoLine("Score", sidePanel);
		
		// Add TETRIS label
		final JLabel TETRIS = new JLabel("TETRIS");
		Font largeWord = new Font("TETRIS", 1, 40);
		TETRIS.setFont(largeWord);
		final JPanel title = new JPanel();
		title.add(TETRIS);
		sidePanel.add(title);

		// Instructions
		final Instructions instructions = new Instructions(200, 400);
		
		// Main playing area
		final TetrisCourt court = new 
				TetrisCourt(level, lines, score, preview, instructions);
		court.setSize(new Dimension(200,400));
		
		// Add shadow option
		final ButtonGroup shadowGroup = new ButtonGroup();
		final JRadioButton shadowOn = new JRadioButton("Shadow On");
		shadowOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.shadowOn();
				court.grabFocus();
			}
		});
		final JRadioButton shadowOff = new JRadioButton ("Shadow Off");
		shadowOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.shadowOff();
				court.grabFocus();
			}
		});
		shadowGroup.add(shadowOn);
		shadowGroup.add(shadowOff);
		shadowOn.setSelected(true);
		final JPanel shadowOptions = new JPanel();
		shadowOptions.setLayout(new BoxLayout(shadowOptions, BoxLayout.X_AXIS));
		shadowOptions.add(shadowOn);
		shadowOptions.add(shadowOff);
		sidePanel.add(shadowOptions);

		// Add reset button
		final JButton reset = new JButton("Start Over");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.reset();
			}
		});
		final JPanel resetButton = new JPanel();
		resetButton.setPreferredSize(new Dimension (
				shadowOptions.getWidth(),reset.getHeight() + 10));
		resetButton.add(reset);
		sidePanel.add(resetButton);
		
		// Add instructions button
		final JButton instruct = new JButton("Instructions");
		instruct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instructions.setVisible(!instructions.isShowing());
				Timer time = court.getTimer();
				court.grabFocus();
				if (time.isRunning())
					time.stop();
				else
					time.start();
			}
		});
		final JPanel instructButton = new JPanel();
		instructButton.setPreferredSize(new Dimension (
				shadowOptions.getWidth(),instruct.getHeight() + 10));
		instructButton.add(instruct);
		sidePanel.add(instructButton);

		add(court);
		add(sidePanel);
		

		// Put the frame on the screen
		//this.setVisible(true);
		// Start the game running
		court.grabFocus();
	}


	/*
	 * Rather than directly building the top level frame object in the main
	 * method, we use the invokeLater utility method to ask the Swing framework
	 * to invoke the method 'run' of the Runnable object we pass it, at some
	 * later time that is convenient for it. (The key technical difference is
	 * that this will cause the new object to be created by a different
	 * "thread".)
	 */
	 /*public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Game();
			}
		});
	}*/
}


