package gui;

import org.jetbrains.annotations.NotNull;

enum GuiMainUtil {
    ;

    static boolean isApple() {
        return getOsName().startsWith("Mac OS X");
    }

    static boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    @NotNull
    private static String getOsName() {
        return System.getProperty("os.name");
    }
}
