package model;

import org.jetbrains.annotations.NotNull;

enum PositionToPointsMap {
    ;

    @NotNull private static final int[] qPoints = {0, 10, 8, 6, 5, 4, 3, 2, 1};
    @NotNull private static final int[] rPoints = {0, 25, 18, 15, 12, 10, 8, 6, 5, 4, 3, 2, 1};

    static int mapQPosition(int qPosition) {
        return qPoints[qPosition];
    }

    static int mapRPosition(int rPosition) {
        return rPoints[rPosition];
    }
}
