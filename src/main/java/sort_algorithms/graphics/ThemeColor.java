package sort_algorithms.graphics;

import sort_algorithms.utils.misc.ColorObject;

import java.awt.*;

public record ThemeColor(Color base, Color accent, Color background) {

    public static ThemeColor createDefault() {
        return new ThemeColor(ColorObject.of("#f4b13b"), ColorObject.of("#feab32"), ColorObject.of("#4d222c"));
    }

    public static ColorObject getTextColor() {
        return ColorObject.of("#785e34");
    }
}
