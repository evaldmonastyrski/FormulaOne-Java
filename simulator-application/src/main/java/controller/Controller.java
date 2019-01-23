package controller;

import controller.deserializer.Deserializer;

class Controller {

    private final Deserializer deserializer;

    Controller() {
        deserializer = new Deserializer();
        initialize();
    }

    private void initialize() {
        deserializer.readTextFile();
    }
}
