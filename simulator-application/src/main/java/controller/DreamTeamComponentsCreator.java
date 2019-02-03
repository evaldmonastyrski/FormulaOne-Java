package controller;

import controller.deserializer.DataEntry;
import model.Driver;
import model.Team;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class DreamTeamComponentsCreator {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(DreamTeamComponentsCreator.class);

    @NotNull private final List<DataEntry> data;
    @NotNull private final Map<String, List<Driver>> teamCache = new HashMap<>();

    @NotNull private final Set<Driver> drivers;
    @NotNull private final Set<Team> teams;

    DreamTeamComponentsCreator(@NotNull List<DataEntry> data, @NotNull Set<Driver> drivers, @NotNull Set<Team> teams) {
        this.data = data;
        this.drivers = drivers;
        this.teams = teams;
    }

    void createDreamTeamComponents(int gpStage) {
        clearCollections();
        for (DataEntry dataEntry : data) {
            Driver driver = new Driver(dataEntry, gpStage);
            cacheTeam(dataEntry.getTeam(), driver);
            drivers.add(driver);
        }

        for (String team : teamCache.keySet()) {
            List<Driver> drivers = teamCache.get(team);
            teams.add(new Team(team, drivers));
        }
        LOGGER.info("Drivers: {}", drivers.size());
        LOGGER.info("Teams: {}", teams.size());
    }

    private void clearCollections() {
        drivers.clear();
        teams.clear();
        teamCache.clear();
    }

    private void cacheTeam(@NotNull String teamName, @NotNull Driver driver) {
        if (teamCache.containsKey(teamName)) {
            teamCache.get(teamName).add(driver);
        } else {
            List<Driver> newList = new ArrayList<>();
            newList.add(driver);
            teamCache.put(teamName, newList);
        }
    }
}
