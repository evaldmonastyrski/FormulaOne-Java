package gui;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class HelpTab extends JPanel {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(HelpTab.class);
    @NotNull private static final String OWNER = "© Evald Monastyrski & Evelina Monastyrska";
    @NotNull private static final String VERSION = "Version: 2.0";

    @NotNull private final JLabel ownerLabel = new JLabel();
    @NotNull private final JLabel versionLabel = new JLabel();

    HelpTab() {
        super(new BorderLayout());
    }

    void init() {
        versionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        versionLabel.setText(VERSION);
        ownerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ownerLabel.setText(OWNER);
        this.add(versionLabel, BorderLayout.NORTH);
        this.add(ownerLabel, BorderLayout.SOUTH);
        loadImage();
    }

    private void loadImage() {
        try {
            BufferedImage myPicture = ImageIO.read(new File("Resources/Graphics/logo.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            picLabel.setSize(500, 500);
            Image dimg = myPicture.getScaledInstance(picLabel.getWidth(), picLabel.getHeight(),
                    Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dimg);
            picLabel.setIcon(imageIcon);
            this.add(picLabel, BorderLayout.CENTER);

        } catch (IOException ex) {
            LOGGER.error("Image could not be loaded");
        }
    }
}
