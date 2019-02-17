package controller.deserializer;

import model.Driver;
import model.Engine;
import model.Team;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static com.google.common.collect.Lists.newArrayList;

public class DeserializedDataContainer {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(DeserializedDataContainer.class);

    @NotNull private final Deserializer deserializer = new Deserializer();
    @NotNull private final List<DataEntry> data;
    @NotNull private final Map<String, List<Driver>> teamCache = new HashMap<>();
    @NotNull private final Map<String, List<Driver>> engineCache = new HashMap<>();

    @NotNull private final List<Driver> drivers;
    @NotNull private final List<Team> teams;
    @NotNull private final List<Engine> engines;
    @NotNull private final Map<String, Team> teamMap;
    @NotNull private final Map<String, Engine> engineMap;

    public DeserializedDataContainer(@NotNull List<Driver> drivers,
                                     @NotNull List<Team> teams,
                                     @NotNull Map<String, Team> teamMap,
                                     @NotNull List<Engine> engines,
                                     @NotNull Map<String, Engine> engineMap) {
        this.data = deserializer.getData();
        this.drivers = drivers;
        this.teams = teams;
        this.teamMap = teamMap;
        this.engines = engines;
        this.engineMap = engineMap;
    }

    public void createDreamTeamComponents(int gpStage) {
        clearCollections();
        createDrivers(gpStage);
        createTeams();
        createEngines();
        LOGGER.debug("Drivers: {}", drivers.size());
        LOGGER.debug("Teams: {}", teams.size());
        LOGGER.debug("Engines: {}", engines.size());
    }

    @NotNull
    public String[] getGPStages() {
        return deserializer.getGPStages();
    }

    private void clearCollections() {
        drivers.clear();
        teams.clear();
        teamCache.clear();
        engines.clear();
        engineCache.clear();
    }

    private void createDrivers(int gpStage) {
        @NotNull final Set<Driver> driverSet = new TreeSet<>();
        for (DataEntry dataEntry : data) {
            Driver driver = new Driver(dataEntry, gpStage);
            cacheTeam(dataEntry.getTeam(), driver);
            cacheEngine(dataEntry.getEngine(), driver);
            driverSet.add(driver);
        }
        drivers.addAll(driverSet);
    }

    private void createTeams() {
        @NotNull final Set<Team> teamSet = new TreeSet<>();
        for (String team : teamCache.keySet()) {
            List<Driver> drivers = teamCache.get(team);
            Team newTeam = new Team(team, drivers);
            teamSet.add(newTeam);
            teamMap.put(team, newTeam);
        }
        teams.addAll(teamSet);
    }

    private void createEngines(){
        @NotNull final Set<Engine> engineSet = new TreeSet<>();
        for (String engine : engineCache.keySet()){
            List<Driver> drivers = engineCache.get(engine);
            Engine newEngine = new Engine(engine, drivers);
            engineSet.add(newEngine);
            engineMap.put(engine, newEngine);
        }
        engines.addAll(engineSet);
    }

    private void cacheTeam(@NotNull String teamName, @NotNull Driver driver) {
        if (teamCache.containsKey(teamName)) {
            teamCache.get(teamName).add(driver);
        } else {
            teamCache.put(teamName, newArrayList(driver));
        }
    }

    private void cacheEngine(@NotNull String engineName, @NotNull Driver driver) {
        if (engineCache.containsKey(engineName)){
            engineCache.get(engineName).add(driver);
        } else {
            engineCache.put(engineName, newArrayList(driver));
        }
    }
}
