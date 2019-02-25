package controller.combinator;

import model.DreamTeam;
import model.Driver;
import model.Engine;
import model.SimulationParameters;
import model.Team;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Combinator {

    @NotNull private final List<DreamTeam> dreamTeams = new ArrayList<>();

    @NotNull public List<DreamTeam> getAvailableDreamTeams(@NotNull SimulationParameters simulationParameters) {
        List<DreamTeam> tempDreamTeams = new ArrayList<>();

        for (DreamTeam dreamTeam : dreamTeams) {
            if (dreamTeam.getPrice() <= simulationParameters.getBudget()) {
                tempDreamTeams.add(dreamTeam);
            }
        }

        double pointsThreshold =
                getPointsThreshold(simulationParameters.getPointsThreshold(), maxPoints(tempDreamTeams));
        List<DreamTeam> availableDreamTeams = new ArrayList<>();
        for (DreamTeam dreamTeam : tempDreamTeams) {
            if (dreamTeam.getPrice() <= simulationParameters.getBudget()) {
                if (simulationParameters.usePointsThreshold()) {
                    if (dreamTeam.getPoints() >= pointsThreshold) {
                        availableDreamTeams.add(dreamTeam);
                    }
                } else {
                    availableDreamTeams.add(dreamTeam);
                }
            }
        }

        return availableDreamTeams;
    }

    public void combine(@NotNull List<Driver> drivers, @NotNull List<Team> teams, @NotNull List<Engine> engines) {
        for (int i = 0; i < drivers.size(); i++) {
            for (int j = 0; j < drivers.size(); j ++) {
                if (j > i) {
                    for (Team team : teams) {
                        for (Engine engine : engines) {
                            dreamTeams.add(new DreamTeam(drivers.get(i), drivers.get(j), team, engine));
                        }
                    }
                }
            }
        }
    }

    private static double maxPoints(@NotNull List<DreamTeam> tempDreamTeams) {
        double maxPoints = 0;
        for (DreamTeam dreamTeam : tempDreamTeams) {
            if (dreamTeam.getPoints() > maxPoints) {
                maxPoints = dreamTeam.getPoints();
            }
        }
        return maxPoints;
    }

    private static double getPointsThreshold(double coefficient, double maxPoints) {
        return (coefficient / 100) * maxPoints;
    }
}
