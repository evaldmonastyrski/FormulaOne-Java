package controller.combinator;

import model.DreamTeam;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum Sorter {
    ;

    @NotNull
    public static List<DreamTeam> sortByPoints(@NotNull List<DreamTeam> dreamTeamList) {
        return dreamTeamList.stream()
                .sorted(Comparator.comparing(DreamTeam::getPoints).reversed())
                .collect(Collectors.toList());
    }

    @NotNull
    public static List<DreamTeam> sortByPriceChange(@NotNull List<DreamTeam> dreamTeamList) {
        return dreamTeamList.stream()
                .sorted(Comparator.comparing(DreamTeam::getPriceChange).reversed())
                .collect(Collectors.toList());
    }
}
