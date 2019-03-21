package gui;

import model.DreamTeam;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RiskCombinationsDialog extends CombinationsDialog {

    public RiskCombinationsDialog(@NotNull List<DreamTeam> dreamTeams) {
        super(dreamTeams, "Risk Results");
    }

    @Override
    @NotNull
    String getEntryLine(int no, DreamTeam dreamTeam) {
        return String.format("%4d %-1s %-12s %-12s %-12s %-8s %9.2f %12.2f %12.2f %12.2f %9.2f \n",
                no,
                " ",
                dreamTeam.getDriver1().getSurname(),
                dreamTeam.getDriver2().getSurname(),
                dreamTeam.getTeam().getName(),
                dreamTeam.getEngine().getName(),
                dreamTeam.getPrice(),
                dreamTeam.getMinPoints(),
                dreamTeam.getMaxPriceChange(),
                dreamTeam.getPriceOffset(),
                dreamTeam.getRisk());
    }

    @Override
    @NotNull
    String getFirstLine() {
        String firstLine = String.format("%4s %-1s %-12s %-12s %-12s %-8s %9s %12s %12s %12s %9s \n",
                "No",
                " ",
                "Driver 1",
                "Driver 2",
                "Team",
                "Engine",
                "Price",
                "Min Points",
                "Max WChange",
                "Price Offset",
                "Risk");
        String secondLine = "-------------------------------------------------------------------------------------" +
                "----------------------------\n";
        return firstLine + secondLine;
    }
}
