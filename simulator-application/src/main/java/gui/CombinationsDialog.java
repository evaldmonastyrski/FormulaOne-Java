package gui;

import model.DreamTeam;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import java.util.List;

public class CombinationsDialog {

    public CombinationsDialog(@NotNull List<DreamTeam> dreamTeams) {
        JFrame dialog = new JFrame("Results");
        dialog.setSize(730, 820);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.append(getFirstLine());
        JScrollPane scrollPane = new JScrollPane(textArea);
        dialog.add(scrollPane);

        int i = 1;
        for (DreamTeam dreamTeam : dreamTeams) {
            String entry = String.format("%4d %-1s %-12s %-12s %-12s %-8s %9.2f %9.2f %12.2f %12.2f \n",
                    i,
                    " ",
                    dreamTeam.getDriver1().getSurname(),
                    dreamTeam.getDriver2().getSurname(),
                    dreamTeam.getTeam().getName(),
                    dreamTeam.getEngine().getName(),
                    dreamTeam.getPrice(),
                    dreamTeam.getPoints(),
                    dreamTeam.getPriceChange(),
                    dreamTeam.getPriceOffset());

            textArea.append(entry);
            i++;
        }

        textArea.setCaretPosition(0);
        dialog.setVisible(true);
    }

    @NotNull
    private String getFirstLine() {
        String firstLine = String.format("%4s %-1s %-12s %-12s %-12s %-8s %9s %9s %12s %12s \n",
                "No",
                " ",
                "Driver 1",
                "Driver 2",
                "Team",
                "Engine",
                "Price",
                "Points",
                "Price Change",
                "Price Offset");
        String secondLine = "-------------------------------------------------------------------------------------" +
                "---------------\n";
        return firstLine + secondLine;
    }
}
