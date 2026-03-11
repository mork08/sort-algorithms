package sort_algorithms.utils.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CacheManager {

    private final Logger log = LoggerFactory.getLogger(CacheManager.class);
    private final Map<String, BufferedImage> cacheImage = new HashMap<>();
    private final Map<String, Font> cacheFont = new HashMap<>();

    /**
     * Lädt ein Bild aus den Ressourcen oder gibt es aus dem Cache zurück.
     * Das Bild wird nur einmal geladen und danach wiederverwendet.
     *
     * @param src Pfad zur Bildressource
     * @return geladenes oder gecachtes Bild
     */
    public BufferedImage loadImage(String src) {
        synchronized (this.cacheImage) {
            return this.cacheImage.computeIfAbsent(src, s -> {
                try {
                    return ImageIO.read(CacheManager.class.getResource(s));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        }
    }

    /**
     * Fügt ein Bild manuell in den Cache ein.
     *
     * @param id eindeutige ID des Bildes
     * @param image das zu cachende Bild
     * @return das übergebene Bild
     */
    public BufferedImage cacheImage(String id, BufferedImage image) {
        this.cacheImage.put(id, image);
        return image;
    }

    /**
     * Sucht ein Bild im Cache.
     *
     * @param id ID des Bildes
     * @return Optional mit Bild, falls vorhanden
     */
    public Optional<BufferedImage> findImage(String id) {
        return Optional.ofNullable(this.cacheImage.get(id));
    }

    /**
     * Lädt einen Font aus den Ressourcen oder gibt ihn aus dem Cache zurück.
     * Der Cache-Key besteht aus Fontname und Größe.
     *
     * @param size Schriftgröße
     * @return geladener oder gecachter Font
     */
    public Font loadFont(double size) {
        return this.loadFont(FontType.PIXEL_FONT, size);
    }

    /**
     * Lädt einen Font aus den Ressourcen oder gibt ihn aus dem Cache zurück.
     * Der Cache-Key besteht aus Fontname und Größe.
     *
     * @param fontType Font
     * @param size Schriftgröße
     * @return geladener oder gecachter Font
     */
    public Font loadFont(FontType fontType, double size) {
        synchronized (this.cacheImage) {
            return this.cacheFont.computeIfAbsent(
                    String.format("%s-%.0f", fontType.getName(), size),
                    s -> {
                        try {
                            InputStream stream = CacheManager.class.getResourceAsStream(fontType.getFile());
                            if (stream == null) {
                                this.log.error("Font {} does not exist", fontType.getName());
                                return null;
                            }
                            return Font.createFont(Font.TRUETYPE_FONT, stream);
                        } catch (IOException | FontFormatException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        }
    }

    /**
     * Fügt einen Font manuell in den Cache ein.
     *
     * @param font der zu cachende Font
     * @return der übergebene Font
     */
    public Font cacheFont(Font font) {
        this.cacheFont.put(String.format("%s-%.0f", font.getFontName(), font.getSize()), font);
        return font;
    }

    /**
     * Sucht einen Font im Cache anhand von Name und Größe.
     *
     * @param type Font
     * @param size Schriftgröße
     * @return Optional mit Font, falls vorhanden
     */
    public Optional<Font> findFont(FontType type, double size) {
        return Optional.ofNullable(this.cacheFont.get(String.format("%s-%.0f", type.getName(), size)));
    }
}