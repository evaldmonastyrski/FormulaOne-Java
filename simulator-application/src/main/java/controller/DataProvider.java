package controller;

import controller.combinator.Combinator;
import controller.deserializer.DeserializedDataContainer;
import model.DreamTeam;
import model.DreamTeamComponents;
import model.Driver;
import model.Engine;
import model.SimulationParameters;
import model.Team;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

class DataProvider {
    @NotNull private final DeserializedDataContainer componentsCreator;
    @NotNull private final Combinator combinator;

    DataProvider() {
        this.componentsCreator = new DeserializedDataContainer();
        this.combinator = new Combinator();
    }

    @NotNull
    DreamTeamComponents getDreamTeamComponents() {
        return componentsCreator.createDreamTeamComponents(getGPStages().length - 1);
    }

    @NotNull
    DreamTeamComponents getDreamTeamComponents(int gpIndex) {
        return componentsCreator.createDreamTeamComponents(gpIndex);
    }

    @NotNull
    String[] getGPStages() {
        return componentsCreator.getGPStages();
    }

    @NotNull List<Driver>  getDrivers() {
        return componentsCreator.getDrivers();
    }

    @NotNull
    List<Team> getTeams() {
        return componentsCreator.getTeams();
    }

    @NotNull
    List<Engine> getEngines() {
        return componentsCreator.getEngines();
    }

    @NotNull
    Map<String, Team> getTeamMap() {
        return componentsCreator.getTeamMap();
    }

    @NotNull
    Map<String, Engine> getEngineMap() {
        return componentsCreator.getEngineMap();
    }

    void combine() {
        combinator.combine(componentsCreator.getDrivers(), componentsCreator.getTeams(), componentsCreator.getEngines());
    }

    void updateDriversPriceOffset(int sampling) {
        componentsCreator.updateDriversPriceOffset(sampling);
    }

    void setAvailableDreamTeams(@NotNull SimulationParameters simulationParameters) {
        combinator.setAvailableDreamTeams(simulationParameters);
    }

    void setLowRiskDreamTeams(@NotNull SimulationParameters simulationParameters) {
        combinator.setLowRiskDreamTeams(simulationParameters);
    }

    void updateDreamTeams(double budget) {
        combinator.updateDreamTeams(budget);
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
