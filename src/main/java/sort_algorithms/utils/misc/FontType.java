package sort_algorithms.utils.misc;

public enum FontType {
    PIXEL_FONT("NinjaAdventure", "/graphic/font/NinjaAdventure.ttf"),
    DEBUG_FONT("NinjaAdventure", "/graphic/font/NinjaAdventure.ttf");

    private final String name;
    private final String file;

    FontType(String name, String file) {
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return this.name;
    }

    public String getFile() {
        return this.file;
    }
}
