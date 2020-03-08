package gui.resultpanel;

import model.DreamTeam;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.util.List;

abstract class CombinationsDialog {

    CombinationsDialog(@NotNull String[] header,
                       @NotNull List<DreamTeam> dreamTeams,
                       @NotNull String name,
                       @NotNull CombinationsType combinationsType) {
        JFrame dialog = new JFrame(name);
        dialog.setSize(935, 820);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        TableModel tableModel = combinationsType.equals(CombinationsType.REGULAR)
                ? new RegularCombinationsTableModel(header, dreamTeams)
                : new RiskCombinationsTableModel(header, dreamTeams);
        @NotNull JTable teamsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(teamsTable);
        teamsTable.setFillsViewportHeight(true);
        setTableAlignment(teamsTable);

        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    private void setTableAlignment(@NotNull JTable teamsTable) {
        DefaultTableCellRenderer leftAlignment = new DefaultTableCellRenderer();
        leftAlignment.setHorizontalAlignment(JLabel.LEFT);

        DefaultTableCellRenderer rightAlignment = new DefaultTableCellRenderer();
        leftAlignment.setHorizontalAlignment(JLabel.RIGHT);

        for (int i = 0; i < 5; i++) {
            teamsTable.getColumnModel().getColumn(i).setCellRenderer(rightAlignment);
        }
        for (int i = 5; i < 11; i++) {
            teamsTable.getColumnModel().getColumn(i).setCellRenderer(leftAlignment);
        }
    }
}
