package gui.resultpanel;

import model.DreamTeam;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import java.util.List;

abstract class CombinationsDialog {

    CombinationsDialog(@NotNull String[] header,
                       @NotNull List<DreamTeam> dreamTeams,
                       @NotNull String name,
                       @NotNull CombinationsType COMBINATIOnsTYPE) {
        JFrame dialog = new JFrame(name);
        dialog.setSize(935, 820);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        TableModel tableModel = COMBINATIOnsTYPE.equals(CombinationsType.REGULAR)
                ? new RegularCombinationsTableModel(header, dreamTeams)
                : new RiskCombinationsTableModel(header, dreamTeams);
        @NotNull JTable teamsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(teamsTable);
        teamsTable.setFillsViewportHeight(true);

        dialog.add(scrollPane);
        dialog.setVisible(true);
    }
}
