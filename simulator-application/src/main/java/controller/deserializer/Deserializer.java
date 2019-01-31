package controller.deserializer;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Deserializer {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(Deserializer.class);
    @NotNull private static final String FILE = "MarketData.txt";

    @NotNull private String[] gpStages = new String[0];

    public Deserializer() {
        LOGGER.info("Reading Market Data...");
        try {
            File file = new File(FILE);
            Scanner scanner = new Scanner(file);
            gpStages = extractGPStages(scanner.nextLine());

            while (scanner.hasNextLine()) {
                LOGGER.info(scanner.nextLine());
            }

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
