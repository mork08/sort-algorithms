package sort_algorithms.model;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class KeyManagerModel {

    public static KeyManagerModel KEY_NEXT_ALG_STEP = new KeyManagerModel(KeyEvent.VK_RIGHT, "Nächster Schritt");
    public static KeyManagerModel KEY_PREVIOUS_ALG_STEP = new KeyManagerModel(KeyEvent.VK_LEFT, "Vorheriger Schritt");

    private final int key;
    private final String description;
    private BufferedImage icon;

    private KeyManagerModel(int key, String description) {
        this.key = key;
        this.description = description;
        try {
            String filename = KeyEvent.getKeyText(this.key).toUpperCase();
            switch (this.key) {
                case KeyEvent.VK_SPACE -> filename = "SPACE";
                case KeyEvent.VK_CONTROL -> filename = "CTRL";
                case KeyEvent.VK_ENTER -> filename = "ENTER";
                case KeyEvent.VK_LEFT -> filename = "ARROWLEFT";
                case KeyEvent.VK_RIGHT -> filename = "ARROWRIGHT";
            }
            this.icon = ImageIO.read(KeyManagerModel.class.getResourceAsStream(String.format("/graphic/keys/%s.png", filename)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getIcon() {
        return this.icon;
    }

    public int getKey() {
        return this.key;
    }

    public String getDescription() {
        return this.description;
    }
}
