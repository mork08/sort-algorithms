package sort_algorithms.utils.misc;

import java.awt.*;

public class ColorObject extends Color {

    private ColorObject(float r, float g, float b, float a) {
        super(r, g, b, a);
    }

    private ColorObject(float r, float g, float b) {
        super(r, g, b);
    }

    private ColorObject(int rgba, boolean hasalpha) {
        super(rgba, hasalpha);
    }

    private ColorObject(int rgb) {
        super(rgb);
    }

    private ColorObject(int r, int g, int b, int a) {
        super(r, g, b, a);
    }

    private ColorObject(int r, int g, int b) {
        super(r, g, b);
    }

    public static ColorObject of(int r, int g, int b) {
        return new ColorObject(r, g, b);
    }

    public static ColorObject of(int r, int g, int b, int a) {
        return new ColorObject(r, g, b, a);
    }

    public static ColorObject of(float r, float g, float b) {
        return new ColorObject(r, g, b);
    }

    public static ColorObject of(float r, float g, float b, float a) {
        return new ColorObject(r, g, b, a);
    }

    public static ColorObject of(String hex) {
        if (hex == null) throw new NullPointerException("Hex is null");

        String h = hex.trim();
        if (h.startsWith("#")) h = h.substring(1);

        if (h.length() == 6) {
            int rgb = Integer.parseUnsignedInt(h, 16);
            return new ColorObject((0xFF << 24) | rgb, true);
        }
        if (h.length() == 8) {
            // RRGGBBAA -> in ARGB umsortieren
            int rrggbbaa = (int) Long.parseLong(h, 16);
            int r = (rrggbbaa >> 24) & 0xFF;
            int g = (rrggbbaa >> 16) & 0xFF;
            int b = (rrggbbaa >> 8) & 0xFF;
            int a = rrggbbaa & 0xFF;
            return new ColorObject(r, g, b, a);
        }
        throw new IllegalArgumentException("Ungültiges Hex-Format: " + hex);
    }

    public ColorObject blendOver(Color background, Color foreground) {
        double fa = foreground.getAlpha() / 255.0;
        double ba = background.getAlpha() / 255.0;

        double outA = fa + ba * (1.0 - fa);
        if (outA <= 0.0) return new ColorObject(0, 0, 0, 0);

        int r = (int) Math.round(((foreground.getRed() * fa) + (background.getRed() * ba * (1.0 - fa))) / outA);
        int g = (int) Math.round(((foreground.getGreen() * fa) + (background.getGreen() * ba * (1.0 - fa))) / outA);
        int b = (int) Math.round(((foreground.getBlue() * fa) + (background.getBlue() * ba * (1.0 - fa))) / outA);
        int a = (int) Math.round(outA * 255.0);

        return new ColorObject(r, g, b, a);
    }

    public ColorObject multiply(Color other) {
        int r = (getRed()   * other.getRed())   / 255;
        int g = (getGreen() * other.getGreen()) / 255;
        int b = (getBlue()  * other.getBlue())  / 255;
        int a = (getAlpha() * other.getAlpha()) / 255;
        return new ColorObject(r, g, b, a);
    }

    public ColorObject invert() {
        return new ColorObject(255 - getRed(), 255 - getGreen(), 255 - getBlue(), getAlpha());
    }

    /**
     * Interpoliert zwischen zwei Farben.
     *
     * @param from Startfarbe
     * @param to   Zielfarbe
     * @param t    0.0 -> from, 1.0 -> to
     */
    public Color lerp(Color from, Color to, double t) {
        t = Math.clamp(0.0, 1.0, t);

        int r = (int) (from.getRed()   + (to.getRed()   - from.getRed())   * t);
        int g = (int) (from.getGreen() + (to.getGreen() - from.getGreen()) * t);
        int b = (int) (from.getBlue()  + (to.getBlue()  - from.getBlue())  * t);
        int a = (int) (from.getAlpha() + (to.getAlpha() - from.getAlpha()) * t);

        return new Color(r, g, b, a);
    }
}
