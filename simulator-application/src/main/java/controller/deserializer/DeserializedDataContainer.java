package controller.deserializer;

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

public class DeserializedDataContainer {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(DeserializedDataContainer.class);

    @NotNull private final Deserializer deserializer = new Deserializer();
    @NotNull private final List<DataEntry> data;
    @NotNull private final Map<String, List<Driver>> teamCache = new HashMap<>();

    @NotNull private final Set<Driver> drivers;
    @NotNull private final Set<Team> teams;
    @NotNull private final Map<String, Team> teamMap;

    public DeserializedDataContainer(@NotNull Set<Driver> drivers,
                                     @NotNull Set<Team> teams,
                                     @NotNull Map<String, Team> teamMap) {
        this.data = deserializer.getData();
        this.drivers = drivers;
        this.teams = teams;
        this.teamMap = teamMap;
    }

    public void createDreamTeamComponents(int gpStage) {
        clearCollections();
        createDrivers(gpStage);
        createTeams();
        LOGGER.debug("Drivers: {}", drivers.size());
        LOGGER.debug("Teams: {}", teams.size());
    }

    @NotNull
    public String[] getGPStages() {
        return deserializer.getGPStages();
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
            Team newTeam = new Team(team, drivers);
            teams.add(newTeam);
            teamMap.put(team, newTeam);
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
