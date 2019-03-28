package gui.setuppanel;

import controller.Constants;
import gui.SetupPanel;
import model.Driver;
import org.jetbrains.annotations.NotNull;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.List;

public class SetupRiskManager {

    @NotNull private final SpinnerNumberModel[] riskSpinnerModels = new SpinnerNumberModel[Constants.NUMBER_OF_DRIVERS];
    @NotNull private final JSpinner[] driverRiskSpinners = new JSpinner[Constants.NUMBER_OF_DRIVERS];
    @NotNull private final Dimension riskLabelsDimension = new Dimension(80, 24);

    @NotNull private final SetupPanel setupPanel;
    @NotNull private final GridBagConstraints constraints;

    public SetupRiskManager(@NotNull SetupPanel setupPanel, @NotNull GridBagConstraints constraints) {
        this.setupPanel = setupPanel;
        this.constraints = constraints;
    }

    public void init(int startRow) {
        initializeRiskLabels(startRow, 5);
    }

    private void updateMinPoints(int index) {
        String value = driverRiskSpinners[index].getValue() + "";
        double points = Double.parseDouble(value);
        setupPanel.updateDriverMinPoints(index, points);
    }

    public void setMinimumPoints(@NotNull List<Driver> drivers) {
        int i = 0;
        for (Driver driver : drivers) {
            driverRiskSpinners[i].setMinimumSize(riskLabelsDimension);
            driverRiskSpinners[i].setPreferredSize(riskLabelsDimension);
            riskSpinnerModels[i].setValue(driver.getMinPoints());
            i++;
        }
    }

    private void initializeRiskLabels(int rowNo, int columnNo) {
        int row = rowNo;
        for (int i = 0; i < driverRiskSpinners.length; i++) {
            int index = i;
            constraints.fill = GridBagConstraints.CENTER;
            constraints.gridx = columnNo;
            constraints.gridy = row;
            riskSpinnerModels[i] = new SpinnerNumberModel(0, 0, 35, 1);
            driverRiskSpinners[i] = new JSpinner(riskSpinnerModels[i]);
            driverRiskSpinners[i].addChangeListener(e -> updateMinPoints(index));
            setupPanel.add(driverRiskSpinners[i], constraints);
            row++;
        }
    }
}
