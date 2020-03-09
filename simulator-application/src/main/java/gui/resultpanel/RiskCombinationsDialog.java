package gui.resultpanel;

import model.DreamTeam;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RiskCombinationsDialog extends CombinationsDialog {

    @NotNull
    private final static String[] header = {"No",
            "Driver 1",
            "Driver 2",
            "Team",
            "Engine",
            "Price",
            "Min Points",
            "Max WChange",
            "Price Offset",
            "Risk",
            "Overall"};

    public RiskCombinationsDialog(@NotNull List<DreamTeam> dreamTeams, double maxPoints) {
        super(header, dreamTeams, "Risk Results", CombinationsType.RISK, maxPoints);
    }
}
