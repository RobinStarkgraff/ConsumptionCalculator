package starkgraff.robin.Application;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ConsumptionCalculator {

	private static final String CONSUMPTION_MESSAGE = "Consumption (l / 100km): %s";
	private static final String CONSUMPTION_INPUT_INVALID_MESSAGE = "Invalid input";

	private ArrayList<Result> priorResults;

	private JButton calculateButton;
	private JPanel jPanelMain;
	private JTextPane oldKmStand;
	private JTextPane newKmStand;
	private JTextPane tankStatus;
	private JButton aboutButton;
	private JButton tankStatusesButton;
	private JLabel consumptionTextField;

	public ConsumptionCalculator() {
		priorResults = new ArrayList<>();
		calculateButton.addActionListener(e -> consumptionTextField.setText(setAndSaveMessage()));
		tankStatusesButton.addActionListener(e -> displayResults());
		aboutButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Tank Stati"));
	}

	public static void main(String[] args) {
		JFrame jFrame = new JFrame("ConsumptionCalculator");
		jFrame.setContentPane(new ConsumptionCalculator().jPanelMain);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.pack();
		jFrame.setVisible(true);
	}

	private void displayResults() {
		JFrame consumptionHistory = new JFrame("ConsumptionHistory");
		consumptionHistory.setContentPane(new TankStatusesDialog(consumptionHistory, priorResults).getjPanelMain());
		consumptionHistory.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		consumptionHistory.pack();
		consumptionHistory.setVisible(true);
	}

	private String setAndSaveMessage() {
		String resultText;

		float consumption = calculateConsumption();
		if (consumption < 0 || Float.isInfinite(consumption)) {
			resultText = CONSUMPTION_INPUT_INVALID_MESSAGE;
		} else {
			resultText = String.format(CONSUMPTION_MESSAGE, consumption);
		}

		saveResult(resultText);

		return resultText;
	}

	private void saveResult(String resultText) {
		priorResults.add(new Result(getIntegerFromTextPane(this.tankStatus), getIntegerFromTextPane(this.newKmStand), getIntegerFromTextPane(this.oldKmStand), resultText));
	}

	private float calculateConsumption() {
		float tankStatus = getIntegerFromTextPane(this.tankStatus);
		float newKmStand = getIntegerFromTextPane(this.newKmStand);
		float oldKmStand = getIntegerFromTextPane(this.oldKmStand);

		if (tankStatus < 0 || newKmStand < 0 || oldKmStand < 0) {
			return -1f;
		}

		return tankStatus * 100f / (newKmStand - oldKmStand);
	}

	private float getIntegerFromTextPane(JTextPane textPane) {
		String textString = textPane.getText();
		try {
			textPane.setBackground(Color.WHITE);
			float myFloat = Float.parseFloat(textString);

			if (myFloat >= 0) {
				return myFloat;
			}

			throw new Exception();
		} catch(Exception e) {
			textPane.setBackground(Color.RED);
			return -1f;
		}
	}

	public class Result {

		private final float oldKmStand;
		private final float newKmStand;
		private final float tankStatus;
		private final String resultMessage;

		public Result(float oldKmStand, float newKmStand, float tankStatus, String resultMessage) {
			this.oldKmStand = oldKmStand;
			this.newKmStand = newKmStand;
			this.tankStatus = tankStatus;
			this.resultMessage = resultMessage;
		}

		public float getOldKmStand() {
			return oldKmStand;
		}

		public float getNewKmStand() {
			return newKmStand;
		}

		public float getTankStatus() {
			return tankStatus;
		}

		public String getResultMessage() {
			return resultMessage;
		}
	}
}
