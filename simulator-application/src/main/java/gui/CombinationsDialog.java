package gui;

import model.DreamTeam;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import java.util.List;

abstract class CombinationsDialog {

    CombinationsDialog(@NotNull List<DreamTeam> dreamTeams, @NotNull String name) {
        JFrame dialog = new JFrame(name);
        dialog.setSize(860, 820);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.append(getFirstLine());
        JScrollPane scrollPane = new JScrollPane(textArea);
        dialog.add(scrollPane);

        int i = 1;
        for (DreamTeam dreamTeam : dreamTeams) {
            String entry = getEntryLine(i, dreamTeam);

            textArea.append(entry);
            i++;
        }

        textArea.setCaretPosition(0);
        dialog.setVisible(true);
    }

    @NotNull
    String getEntryLine(int no, @NotNull DreamTeam dreamTeam) {
        return "";
    }

    @NotNull
    String getFirstLine() {
        return "";
    }
}
