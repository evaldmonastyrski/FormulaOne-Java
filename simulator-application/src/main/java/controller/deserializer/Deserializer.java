package controller.deserializer;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Deserializer {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(Deserializer.class);
    @NotNull private static final String FILE = "MarketData.txt";
    @NotNull private final List<BaseDriver> drivers;
    @NotNull private String[] gpStages = new String[0];

    public Deserializer() {
        LOGGER.info("Reading Market Data...");
        drivers = new ArrayList<>();
        try {
            File file = new File(FILE);
            Scanner scanner = new Scanner(file);
            gpStages = extractGPStages(scanner.nextLine());

            while (scanner.hasNextLine()) {
                extractDriver(scanner.nextLine());
            }

            LOGGER.info("Parsing {} is completed", FILE);
            LOGGER.info("Number of GP Stages: {}", gpStages.length);
            LOGGER.info("Number of drivers: {}", drivers.size());

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
    private String[] extractGPStages(String firstLine) {
        String[] gpStageEntries = firstLine.trim().split("\\s+");

        for (int i = 0; i < gpStageEntries.length; i++) {
            if (gpStageEntries[i].contains("_")) {
                gpStageEntries[i] = gpStageEntries[i].replace("_", " ");
            }
        }

        return gpStageEntries;
    }

    @NotNull
    private void extractDriver(String entryLine) {
        String[] driver = entryLine.split("\\s+");
        String[] driverPriceStrings = Arrays.copyOfRange(driver, 4, driver.length);

        double[] driverPrices = Arrays.stream(driverPriceStrings)
                .mapToDouble(Double::parseDouble)
                .toArray();

        drivers.add(ImmutableBaseDriver.builder()
                .name(driver[0])
                .surname(driver[1])
                .prices(driverPrices)
                .build());
    }
}
