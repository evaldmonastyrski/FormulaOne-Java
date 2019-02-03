package controller.componentscreator;

import controller.deserializer.DataEntry;
import model.Driver;
import model.Team;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;

public class DreamTeamComponentsCreator {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(DreamTeamComponentsCreator.class);

    @NotNull private final List<DataEntry> data;
    @NotNull private final Map<String, List<Driver>> teamCache = new HashMap<>();

    @NotNull private final Set<Driver> drivers;
    @NotNull private final Set<Team> teams;

    public DreamTeamComponentsCreator(@NotNull List<DataEntry> data,
                                      @NotNull Set<Driver> drivers,
                                      @NotNull Set<Team> teams) {
        this.data = data;
        this.drivers = drivers;
        this.teams = teams;
    }

    public void createDreamTeamComponents(int gpStage) {
        clearCollections();
        createDrivers(gpStage);
        createTeams();
        LOGGER.debug("Drivers: {}", drivers.size());
        LOGGER.debug("Teams: {}", teams.size());
    }

    private void clearCollections() {
        drivers.clear();
        teams.clear();
        teamCache.clear();
    }

    private void createDrivers(int gpStage) {
        for (DataEntry dataEntry : data) {
            Driver driver = new Driver(dataEntry, gpStage);
            cacheTeam(dataEntry.getTeam(), driver);
            drivers.add(driver);
        }
    }

    private void createTeams() {
        for (String team : teamCache.keySet()) {
            List<Driver> drivers = teamCache.get(team);
            teams.add(new Team(team, drivers));
        }
    }

    private void cacheTeam(@NotNull String teamName, @NotNull Driver driver) {
        if (teamCache.containsKey(teamName)) {
            teamCache.get(teamName).add(driver);
        } else {
            teamCache.put(teamName, newArrayList(driver));
        }
    }
}
