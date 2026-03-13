package sort_algorithms.graphics.level;

import KAGO_framework.view.DrawTool;
import sort_algorithms.graphics.ThemeColor;

public abstract class Level {

    protected String name;
    protected ThemeColor theme;

    /***
     * "Erstellt" quasi das Level, wird in der ganzen Runtime nur einmal ausgeführt
     * @param name
     */
    protected Level(String name) {
        this.name = name;
        this.theme = ThemeColor.createDefault();
    }

    /***
     * Wenn Level nicht angezeigt wurde und angezeigt wird
     * Im Gegensatz zum Konstruktor wird es öfters während der Runtime aufgerufen
     */
    public abstract void onActive();

    /***
     * Wenn Level beendet wird und nicht mehr angezeigt wird
     */
    public abstract void onHide();

    /***
     * Wenn Level zurückgesetzt werden soll
     */
    public abstract void onReset();

    public abstract void update(double dt);
    public abstract void draw(DrawTool drawTool);
    public void drawAfterObjects(DrawTool drawTool) {}

    public String getName() {
        return this.name;
    }

    public ThemeColor getTheme() {
        return this.theme;
    }
}
