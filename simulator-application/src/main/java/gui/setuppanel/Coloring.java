package gui.setuppanel;

import org.jetbrains.annotations.NotNull;

import javax.swing.JLabel;
import java.awt.Color;

enum Coloring {
    ;

    @NotNull
    public static void colorLabel(@NotNull JLabel label, double priceChange) {
        if (priceChange > 1.5d) {
            label.setBackground(Colours.CHARTREUSE);
            label.setForeground(Color.BLACK);
        } else if (priceChange > 0.5F) {
            label.setBackground(Colours.SPRING_GREEN);
            label.setForeground(Color.BLACK);
        } else if (priceChange >= 0F) {
            label.setBackground(Colours.PALE_GREEN);
            label.setForeground(Color.BLACK);
        } else if (priceChange > -0.5F) {
            label.setBackground(Colours.YELLOW);
            label.setForeground(Color.BLACK);
        } else if (priceChange > -1F) {
            label.setBackground(Colours.RED);
            label.setForeground(Color.WHITE);
        } else {
            label.setBackground(Colours.FIRE_BRICK);
            label.setForeground(Color.WHITE);
        }
    }
}
