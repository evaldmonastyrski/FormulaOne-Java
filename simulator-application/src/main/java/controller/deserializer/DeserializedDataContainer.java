package controller.deserializer;

import model.DeserializedData;
import model.DreamTeamComponents;
import model.Driver;
import model.Engine;
import model.ImmutableDreamTeamComponents;
import model.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class DeserializedDataContainer {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(DeserializedDataContainer.class);

    @NotNull private final List<DataEntry> data;
    @NotNull private final String[] gpStages;

    @NotNull private final Map<String, List<Driver>> teamCache = new HashMap<>();
    @NotNull private final Map<String, List<Driver>> engineCache = new HashMap<>();

    @NotNull private final Map<Driver, DataEntry> driverCache = new HashMap<>();
    @NotNull private final Map<String, Team> teamMap = new HashMap<>();
    @NotNull private final Map<String, Engine> engineMap = new HashMap<>();

    @NotNull private final List<Driver> drivers;
    @NotNull private final List<Team> teams;
    @NotNull private final List<Engine> engines;

    public DeserializedDataContainer() {
        this.drivers = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.engines = new ArrayList<>();
        this.data = new ArrayList<>();

        @Nullable DeserializedData deserializedData = Deserializer.read();

        if (deserializedData != null) {
            this.data.addAll(deserializedData.getData());
            this.gpStages = deserializedData.getGPStages();
        } else {
            this.gpStages = new String[0];
        }
    }

    @NotNull
    public List<Driver> getDrivers() {
        return drivers;
    }

    @NotNull
    public List<Team> getTeams() {
        return teams;
    }

    @NotNull
    public List<Engine> getEngines() {
        return engines;
    }

    @NotNull
    public DreamTeamComponents createDreamTeamComponents(int gpStage) {
        clearCollections();
        createDrivers(gpStage);
        createTeams();
        createEngines();
        LOGGER.debug("Drivers: {}", drivers.size());
        LOGGER.debug("Teams: {}", teams.size());
        LOGGER.debug("Engines: {}", engines.size());
        return ImmutableDreamTeamComponents.builder()
                .addAllDrivers(drivers)
                .addAllTeams(teams)
                .addAllEngines(engines)
                .build();
    }

    public void updateDriversPriceOffset(int sampling) {
        for (Driver driver : drivers) {
            driver.setPriceOffset(driverCache.get(driver), sampling);
        }
        for (Team team : teams) {
            team.setPriceOffset();
        }
        for (Engine engine : engines) {
            engine.setPriceOffset();
        }
    }

    @NotNull
    public String[] getGPStages() {
        return gpStages;
    }

    public int lastGPIndex() {
        return gpStages.length - 1;
    }

    @NotNull
    public Map<String, Team> getTeamMap() {
        return teamMap;
    }

    @NotNull
    public Map<String, Engine> getEngineMap() {
        return engineMap;
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
            driverCache.put(driver, dataEntry);
            cacheTeam(dataEntry.getTeam(), driver);
            cacheEngine(dataEntry.getEngine(), driver);
            driverSet.add(driver);
        }
        drivers.addAll(driverSet);
    }

    private void createTeams() {
        @NotNull final Set<Team> teamSet = new TreeSet<>();
        for (String teamName : teamCache.keySet()) {
            Team team = new Team(teamName, teamCache.get(teamName));
            teamSet.add(team);
            teamMap.put(teamName, team);
        }
        teams.addAll(teamSet);
    }

    private void createEngines(){
        @NotNull final Set<Engine> engineSet = new TreeSet<>();
        for (String engine : engineCache.keySet()){
            Engine newEngine = new Engine(engine, engineCache.get(engine));
            engineSet.add(newEngine);
            engineMap.put(engine, newEngine);
        }
        engines.addAll(engineSet);
    }

    private void cacheTeam(@NotNull String teamName, @NotNull Driver driver) {
        if (teamCache.containsKey(teamName)) {
            teamCache.get(teamName).add(driver);
        } else {
            List<Driver> drivers = new ArrayList<>();
            drivers.add(driver);
            teamCache.put(teamName, drivers);
        }
    }

    private void cacheEngine(@NotNull String engineName, @NotNull Driver driver) {
        if (engineCache.containsKey(engineName)){
            engineCache.get(engineName).add(driver);
        } else {
            List<Driver> drivers = new ArrayList<>();
            drivers.add(driver);
            engineCache.put(engineName, drivers);
        }
    }
}
