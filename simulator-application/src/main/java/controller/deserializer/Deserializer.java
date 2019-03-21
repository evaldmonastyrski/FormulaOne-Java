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

class Deserializer {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(Deserializer.class);
    @NotNull private static final String FILE = "Resources/MarketData.txt";
    private static final int PRICE_ENTRY_START = 5;
    @NotNull private final List<DataEntry> data = new ArrayList<>();
    @NotNull private String[] gpStages = new String[0];

    Deserializer() {
        LOGGER.info("Reading Market Data...");
        try {
            File file = new File(FILE);
            Scanner scanner = new Scanner(file);
            gpStages = extractGPStages(scanner.nextLine());

            while (scanner.hasNextLine()) {
                createDataEntries(scanner.nextLine());
            }

            LOGGER.info("Parsing {} is completed", FILE);
            LOGGER.info("Number of GP Stages: {}", gpStages.length);
            LOGGER.info("Number of data entries: {}", data.size());

            scanner.close();
        } catch (FileNotFoundException e) {
            LOGGER.error("No {} found", FILE);
        }
    }

    @NotNull
    String[] getGPStages() {
        return gpStages;
    }

    @NotNull
    List<DataEntry> getData() {
        return data;
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

    private void createDataEntries(@NotNull String entryLine) {
        String[] dataChunks = entryLine.split("\\s+");
        String[] priceDataChunks = Arrays.copyOfRange(dataChunks, PRICE_ENTRY_START, dataChunks.length);

        if (dataChunks[2].contains("_")) {
            dataChunks[2] = dataChunks[2].replace("_", " ");
        }

        double minPoints = Double.valueOf(dataChunks[4]);

        double[] prices = Arrays.stream(priceDataChunks)
                .mapToDouble(Double::parseDouble)
                .toArray();

        DataEntry dataEntry = ImmutableDataEntry.builder()
                .name(dataChunks[0])
                .surname(dataChunks[1])
                .team(dataChunks[2])
                .engine(dataChunks[3])
                .minPoints(minPoints)
                .prices(prices)
                .build();

        this.data.add(dataEntry);
    }
}
