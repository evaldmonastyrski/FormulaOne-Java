package controller.combinator;

import model.DreamTeam;
import model.Driver;
import model.Engine;
import model.ImmutableDreamTeam;
import model.Team;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Combinator {
    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(Combinator.class);

    @NotNull private final List<DreamTeam> dreamTeams = new ArrayList<>();

    @NotNull public List<DreamTeam> getAvailableDreamTeams() {
        return dreamTeams;
    }

    public void combine(@NotNull List<Driver> drivers, @NotNull List<Team> teams, @NotNull List<Engine> engines) {
        for (int i = 0; i < drivers.size(); i++) {
            for (int j = 0; j < drivers.size(); j ++) {
                if (j > i) {
                    for (Team team : teams) {
                        for (Engine engine : engines) {
                            dreamTeams.add(ImmutableDreamTeam.builder()
                                    .driver1(drivers.get(i))
                                    .driver2(drivers.get(j))
                                    .team(team)
                                    .engine(engine)
                                    .build());
                        }
                    }
                }
            }
        }
    }

    public void tempPrintDreamTeams() {
        for (DreamTeam dreamTeam : dreamTeams) {
            LOGGER.info("Driver 1: {}, Driver 2: {}, Team: {}, Engine: {}",
                    dreamTeam.getDriver1(), dreamTeam.getDriver2(), dreamTeam.getTeam(), dreamTeam.getEngine());
        }
        LOGGER.info("Number of dream teams: {}", dreamTeams.size());
    }
}
