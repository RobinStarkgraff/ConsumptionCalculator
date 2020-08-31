package starkgraff.robin.Application;

import javax.swing.*;
import java.util.List;

public class TankStatusesDialog {

	private final JFrame myJFrame;
	private final List<ConsumptionCalculator.Result> resultList;
	private int currentEntry;

	private JButton backButton;
	private JButton nextButton;
	private JButton okayButton;
	private JLabel oldKm;
	private JLabel newKm;
	private JLabel tankStatus;
	private JLabel message;
	private JPanel jPanelMain;

	public TankStatusesDialog(JFrame myJFrame, List<ConsumptionCalculator.Result> resultList) {
		this.myJFrame = myJFrame;
		this.resultList = resultList;
		this.currentEntry = resultList.size();
		viewPrior();

		nextButton.addActionListener(e -> viewNext());
		backButton.addActionListener(e -> viewPrior());
		okayButton.setDefaultCapable(true);
		okayButton.addActionListener(e -> myJFrame.dispose());
	}

	private void viewPrior() {
		if (this.currentEntry - 1 < 0) {
			return;
		}

		int currentEntry = --this.currentEntry;
		changeText(currentEntry);
	}

	private void viewNext() {
		if (this.currentEntry > resultList.size()) {
			return;
		}

		int currentEntry = ++this.currentEntry;
		changeText(currentEntry);
	}

	private void changeText(int currentEntry) {
		oldKm.setText(String.format("Old Kilometers: %s", Float.toString(resultList.get(currentEntry).getOldKmStand())));
		newKm.setText(String.format("New Kilometers: %s", Float.toString(resultList.get(currentEntry).getNewKmStand())));
		tankStatus.setText(String.format("Tank Status: %s", Float.toString(resultList.get(currentEntry).getTankStatus())));
		message.setText(resultList.get(currentEntry).getResultMessage());
	}

	public JPanel getjPanelMain() {
		return jPanelMain;
	}
}
