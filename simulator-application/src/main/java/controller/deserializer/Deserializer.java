package controller.deserializer;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Deserializer {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(Deserializer.class);
    @NotNull private static final String FILE = "MarketData.txt";
    @NotNull private final List<BaseDriver> drivers = new ArrayList<>();
    @NotNull private final List<BaseTeam> teams = new ArrayList<>();
    @NotNull private final Map<String, List<BaseDriver>> teamCache = new HashMap<>();
    @NotNull private String[] gpStages = new String[0];

    public Deserializer() {
        LOGGER.info("Reading Market Data...");
        try {
            File file = new File(FILE);
            Scanner scanner = new Scanner(file);
            gpStages = extractGPStages(scanner.nextLine());

            while (scanner.hasNextLine()) {
                createDriver(scanner.nextLine());
            }
            createTeams();

            LOGGER.info("Parsing {} is completed", FILE);
            LOGGER.info("Number of GP Stages: {}", gpStages.length);
            LOGGER.info("Number of drivers: {}", drivers.size());
            LOGGER.info("Number of teams: {}", teams.size());

            scanner.close();
        } catch (FileNotFoundException e) {
            LOGGER.error("No {} found", FILE);
        }
    }

    @NotNull
    public String[] getGPStages() {
        return gpStages;
    }

    @NotNull
    public List<BaseDriver> getDrivers() {
        return drivers;
    }

    @NotNull
    public List<BaseTeam> getTeams() {
        return teams;
    }

    @NotNull
    private String[] extractGPStages(String firstLine) {
        String[] gpStageEntries = firstLine.trim().split("\\s+");

        for (int i = 0; i < gpStageEntries.length; i++) {
            if (gpStageEntries[i].contains("_")) {
                gpStageEntries[i] = gpStageEntries[i].replace("_", " ");
            }
        }

        return gpStageEntries;
    }

    private void createDriver(String entryLine) {
        String[] driverData = entryLine.split("\\s+");
        String[] driverPriceStrings = Arrays.copyOfRange(driverData, 4, driverData.length);

        double[] driverPrices = Arrays.stream(driverPriceStrings)
                .mapToDouble(Double::parseDouble)
                .toArray();

        BaseDriver driver = ImmutableBaseDriver.builder()
                .name(driverData[0])
                .surname(driverData[1])
                .prices(driverPrices)
                .build();

        cacheTeam(driverData[2], driver);
        drivers.add(driver);
    }

    private void createTeams() {
        for (String team : teamCache.keySet()) {
            List<BaseDriver> drivers = teamCache.get(team);
            teams.add(ImmutableBaseTeam.builder()
                    .name(team)
                    .addAllDrivers(drivers)
                    .build());
        }
    }

    private void cacheTeam(@NotNull String driverDatum, @NotNull BaseDriver driver) {
        if (teamCache.containsKey(driverDatum)) {
            teamCache.get(driverDatum).add(driver);
        } else {
            List<BaseDriver> newList = new ArrayList<>();
            newList.add(driver);
            teamCache.put(driverDatum, newList);
        }
    }
}
