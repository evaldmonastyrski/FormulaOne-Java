package controller;

import controller.combinator.Combinator;
import controller.deserializer.DeserializedDataContainer;
import model.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class DataProvider {
    @NotNull private final DeserializedDataContainer dataContainer;
    @NotNull private final Combinator combinator;

    DataProvider() {
        this.dataContainer = new DeserializedDataContainer();
        this.combinator = new Combinator();
    }

    void createCombinations() {
        dataContainer.updateDriversPriceOffset(Constants.OFFSET_STAGES);
        combinator.combine(dataContainer.getDrivers(), dataContainer.getTeams(), dataContainer.getEngines());
    }

    @NotNull
    String[] getGPStages() {
        return dataContainer.getGPStages();
    }

    @NotNull
    DreamTeamComponents getDefaultDreamTeamComponents() {
        return getDreamTeamComponents(dataContainer.lastGPIndex());
    }

    @NotNull
    DreamTeamComponents getDreamTeamComponents(int gpIndex) {
        return dataContainer.createDreamTeamComponents(gpIndex);
    }

    @NotNull
    ComponentsUpdate onComboBoxPositionChanged(@NotNull DriverUpdate driverUpdate) {
        Driver driver = dataContainer.getDrivers().get(driverUpdate.getIndex());
        driver.setPosition(driverUpdate);

        Team teamToUpdate = dataContainer.getTeamMap().get(driver.getTeam());
        teamToUpdate.updateTeam();
        Engine engineToUpdate =  dataContainer.getEngineMap().get(driver.getEngine());
        engineToUpdate.updateEngine();

        return ImmutableComponentsUpdate.builder()
                .driverIndex(driverUpdate.getIndex())
                .driverPoints(driver.getPoints())
                .driverPriceChange(driver.getPriceChange())
                .teamIndex(dataContainer.getTeams().indexOf(teamToUpdate))
                .teamPoints(teamToUpdate.getPoints())
                .teamPriceChange(teamToUpdate.getPriceChange())
                .engineIndex(dataContainer.getEngines().indexOf(engineToUpdate))
                .enginePoints(engineToUpdate.getPoints())
                .enginePriceChange(engineToUpdate.getPriceChange())
                .build();
    }

    @NotNull
    OffsetUpdate onSamplingChanged(int samples) {
        dataContainer.updateDriversPriceOffset(samples);
        return ImmutableOffsetUpdate.builder()
                .addAllDrivers(dataContainer.getDrivers())
                .addAllTeams(dataContainer.getTeams())
                .addAllEngines(dataContainer.getEngines())
                .build();
    }

    void onMinPointsChanged(int index, double points) {
        Driver driver = dataContainer.getDrivers().get(index);
        driver.setMinPoints(points);

        Team teamToUpdate = dataContainer.getTeamMap().get(driver.getTeam());
        teamToUpdate.setMinPoints();

        Engine engineToUpdate = dataContainer.getEngineMap().get(driver.getEngine());
        engineToUpdate.setMinPoints();
    }

    void applySimulationParameters(@NotNull SimulationParameters simulationParameters) {
        combinator.updateDreamTeamsSurplus(simulationParameters.getBudget());
        combinator.setAvailableDreamTeams(simulationParameters);
        combinator.setLowRiskDreamTeams(simulationParameters);
    }

    @NotNull
    List<DreamTeam> getAvailableDreamTeams() {
        return combinator.getAvailableDreamTeams();
    }

    @NotNull
    List<DreamTeam> getLowRiskDreamTeams() {
        return combinator.getLowRiskDreamTeams();
    }
}
