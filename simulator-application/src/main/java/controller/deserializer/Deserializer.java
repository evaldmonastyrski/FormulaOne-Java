package controller.deserializer;

import model.DeserializedData;
import model.ImmutableDeserializedData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

enum Deserializer {
    ;

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(Deserializer.class);
    @NotNull private static final String FILE = "Resources/MarketData.txt";
    private static final int PRICE_ENTRY_START = 5;

    @Nullable
    public static DeserializedData read() {
        LOGGER.info("Reading Market Data...");
        try {
            File file = new File(FILE);
            Scanner scanner = new Scanner(file);

            @NotNull String[] gpStages = extractGPStages(scanner.nextLine());
            @NotNull List<DataEntry> data = new ArrayList<>();

            while (scanner.hasNextLine()) {
                data.add(createDataEntries(scanner.nextLine()));
            }

            LOGGER.info("Parsing {} is completed", FILE);
            LOGGER.info("Number of GP Stages: {}", gpStages.length);
            LOGGER.info("Number of data entries: {}", data.size());

            scanner.close();
            return ImmutableDeserializedData.builder().gPStages(gpStages).addAllData(data).build();
        } catch (FileNotFoundException e) {
            LOGGER.error("No {} found", FILE);
            return null;
        }
    }

    @NotNull
    private static String[] extractGPStages(String firstLine) {
        String[] gpStageEntries = firstLine.trim().split("\\s+");

        for (int i = 0; i < gpStageEntries.length; i++) {
            if (gpStageEntries[i].contains("_")) {
                gpStageEntries[i] = gpStageEntries[i].replace("_", " ");
            }
        }

        return gpStageEntries;
    }

    private static DataEntry createDataEntries(@NotNull String entryLine) {
        String[] dataChunks = entryLine.split("\\s+");
        String[] priceDataChunks = Arrays.copyOfRange(dataChunks, PRICE_ENTRY_START, dataChunks.length);

        if (dataChunks[2].contains("_")) {
            dataChunks[2] = dataChunks[2].replace("_", " ");
        }

        double minPoints = Double.valueOf(dataChunks[4]);

        double[] prices = Arrays.stream(priceDataChunks)
                .mapToDouble(Double::parseDouble)
                .toArray();

        return ImmutableDataEntry.builder()
                .name(dataChunks[0])
                .surname(dataChunks[1])
                .team(dataChunks[2])
                .engine(dataChunks[3])
                .minPoints(minPoints)
                .prices(prices)
                .build();
    }
}
