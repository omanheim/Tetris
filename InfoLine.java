import java.awt.Dimension;
import java.awt.TextField;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoLine {

	private int value;
	final TextField type;

	public InfoLine(String name, JPanel frame) {
		final JPanel line = new JPanel();
		line.add(new JLabel("  "));
		line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
		final JLabel label = new JLabel(name);
		label.setPreferredSize(new Dimension(40, 10));
		line.add(label);
		line.add(new JLabel("          "));
		type = new TextField(String.valueOf(value), 1);
		type.setEditable(false);
		type.setPreferredSize(new Dimension(10, 10));
		line.add(type);
		frame.add(line);
	}

	// Returns the current value
	public int currentValue() {
		return value;
	}

	// Updates the value by a given number
	public void updateBy(int num) {
		value += num;
		type.setText(String.valueOf(value));
	}

	// Updates the value by one
	public void updateValue() {
		value++;
		type.setText(String.valueOf(value));
	}

	// Changes the value to a given number
	public void newValue(int newVal) {
		value = newVal;
		type.setText(String.valueOf(value));
	}

}