package sort_algorithms.graphics;

import sort_algorithms.utils.misc.ColorObject;

public record ThemeColor(ColorObject base, ColorObject accent) {

    public static ThemeColor createDefault() {
        return new ThemeColor(ColorObject.of("#f4b13b"), ColorObject.of("#feab32"));
    }

    public static sort_algorithms.utils.misc.ColorObject getTextColor() {
        return ColorObject.of(47, 29, 3);
    }
}
