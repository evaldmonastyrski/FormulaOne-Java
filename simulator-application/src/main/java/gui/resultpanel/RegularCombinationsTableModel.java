package gui.resultpanel;

import model.DreamTeam;
import org.jetbrains.annotations.NotNull;

import javax.swing.table.AbstractTableModel;
import java.util.List;

import static gui.resultpanel.RegularCombinationsNameMap.*;

public class RegularCombinationsTableModel extends AbstractTableModel {

    @NotNull private static final String DOUBLE_FORMAT = "%.2f";
    @NotNull private final String[] columnNames;
    @NotNull private final String[][] data;

    RegularCombinationsTableModel(@NotNull String[] header, @NotNull List<DreamTeam> dreamTeams) {
        columnNames = header;
        data = new String[dreamTeams.size()][getColumnCount()];
        int fieldIndex = 0;
        for (DreamTeam dreamTeam : dreamTeams) {
            data[fieldIndex][NUMBER.ordinal()] = String.valueOf(getOrderNumber(fieldIndex));
            data[fieldIndex][DRIVER1.ordinal()] = dreamTeam.getDriver1().getSurname();
            data[fieldIndex][DRIVER2.ordinal()] = dreamTeam.getDriver2().getSurname();
            data[fieldIndex][TEAM.ordinal()] = dreamTeam.getTeam().getName();
            data[fieldIndex][ENGINE.ordinal()] = dreamTeam.getEngine().getName();
            data[fieldIndex][PRICE.ordinal()] = String.format(DOUBLE_FORMAT, dreamTeam.getPrice());
            data[fieldIndex][POINTS.ordinal()] = String.format(DOUBLE_FORMAT, dreamTeam.getPoints());
            data[fieldIndex][PRICE_CHANGE.ordinal()] = String.format(DOUBLE_FORMAT, dreamTeam.getPriceChange());
            data[fieldIndex][PRICE_OFFSET.ordinal()] = String.format(DOUBLE_FORMAT, dreamTeam.getPriceOffset());
            data[fieldIndex][RISK.ordinal()] = String.format(DOUBLE_FORMAT, dreamTeam.getRisk());
            data[fieldIndex][OVERALL.ordinal()] = String.format(DOUBLE_FORMAT, dreamTeam.getOverall());
            fieldIndex++;
        }
    }

    private int getOrderNumber(int fieldIndex) {
        return fieldIndex + 1;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @NotNull
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    @NotNull
    public String getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
}
