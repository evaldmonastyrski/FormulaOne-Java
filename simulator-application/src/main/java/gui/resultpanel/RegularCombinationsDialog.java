package gui.resultpanel;

import model.DreamTeam;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RegularCombinationsDialog extends CombinationsDialog {

    @NotNull
    private final static String[] header = {"No",
            "Driver 1",
            "Driver 2",
            "Team",
            "Engine",
            "Price",
            "Points",
            "Price Change",
            "Price Offset",
            "Risk",
            "Overall"};

    public RegularCombinationsDialog(@NotNull List<DreamTeam> dreamTeams, double maxPoints) {
        super(header, dreamTeams, "Regular Results", CombinationsType.REGULAR, maxPoints);
    }
}
