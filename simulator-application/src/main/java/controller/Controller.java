package controller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class Controller {
    @NotNull private final DataProvider dataProvider;

    @Nullable private GuiViewController guiViewController;

    Controller() {
        this.dataProvider = new DataProvider();
        this.guiViewController = new GuiViewController(dataProvider, reloadHandler());
        dataProvider.createCombinations();
    }

    @NotNull
    private ReloadHandler reloadHandler() {
        return new ReloadHandler() {
            @Override
            public void reload() {
                guiViewController = new GuiViewController(dataProvider, this);
            }
        };
    }
}
