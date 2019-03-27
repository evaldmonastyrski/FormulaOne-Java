package gui;

import model.DreamTeam;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import java.util.List;

public class RegularCombinationsDialog extends CombinationsDialog {

    public RegularCombinationsDialog(@NotNull List<DreamTeam> dreamTeams) {
        super(dreamTeams, "Regular Results");
    }

    @Override
    @NotNull
    String getEntryLine(int no, DreamTeam dreamTeam) {
        return String.format("%4d %-1s %-12s %-12s %-12s %-8s %9.2f %9.2f %12.2f %12.2f %9.2f %12.2f \n",
                no,
                " ",
                dreamTeam.getDriver1().getSurname(),
                dreamTeam.getDriver2().getSurname(),
                dreamTeam.getTeam().getName(),
                dreamTeam.getEngine().getName(),
                dreamTeam.getPrice(),
                dreamTeam.getPoints(),
                dreamTeam.getPriceChange(),
                dreamTeam.getPriceOffset(),
                dreamTeam.getRisk(),
                dreamTeam.getOverall());
    }

    @Override
    @NotNull
    String getFirstLine() {
        String firstLine = String.format("%4s %-1s %-12s %-12s %-12s %-8s %9s %9s %12s %12s %9s %12s \n",
                "No",
                " ",
                "Driver 1",
                "Driver 2",
                "Team",
                "Engine",
                "Price",
                "Points",
                "Price Change",
                "Price Offset",
                "Risk",
                "Overall");
        String secondLine = "-------------------------------------------------------------------------------------" +
                "--------------------------------------\n";
        return firstLine + secondLine;
    }
}
