package sort_algorithms.graphics.level;


import KAGO_framework.view.DrawTool;
import sort_algorithms.graphics.ThemeColor;
import sort_algorithms.graphics.array.ArrayRepresentation;
import sort_algorithms.graphics.level.interaction.LevelInteractionElement;
import sort_algorithms.graphics.level.interaction.impl.LevelButton;
import sort_algorithms.model.sorting.SorterHistory;
import sort_algorithms.utils.misc.ColorObject;

import java.util.ArrayList;
import java.util.List;

public abstract class Level {

    protected List<LevelInteractionElement<?>> elements = new ArrayList<>();

    protected String name;
    protected ThemeColor theme;
    protected ArrayRepresentation visualArray;
    protected SorterHistory sorterHistory;
    protected int[] array;
    protected boolean autoPlay;
    private LevelButton pauseBtn;

    /***
     * "Erstellt" quasi das Level, wird in der ganzen Runtime nur einmal ausgeführt
     * @param name
     */
    protected Level(String name) {
        this.name = name;
        this.theme = ThemeColor.createDefault();
        this.array = generateArray(0,20);
        scramble(array);
        visualArray = new ArrayRepresentation(array, 0, 250, 600, 400, (ColorObject) this.theme.accent());
        sorterHistory = sort(array);
        autoPlay = false;
        this.pauseBtn = new LevelButton(this, "TEST", 100, 100, 200, 50, 20);
        this.pauseBtn.onClick((t) -> this.autoPlay = !this.autoPlay);
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
    protected abstract SorterHistory sort(int[] array);
    public void drawAfterObjects(DrawTool drawTool) {}

    public void drawHUD(DrawTool drawTool) {
        this.elements.forEach(element -> element.draw(drawTool));
    }

    protected int[] generateArray(int min, int max) {
        int[] array = new int[max - min + 1];
        for (int i = 0; i < array.length; i++) {
            array[i] = min + i;

        }
        return array;
    }

    protected void scramble(int[] array) {
        for (int i = 0; i < 1000; i++) {
            int index1 = (int) (Math.random() * (array.length));
            int index2 = (int) (Math.random() * (array.length));
            int temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
        }
    }

    public void registerElement(LevelInteractionElement<?> element) {
        this.elements.add(element);
    }

    public String getName() {
        return this.name;
    }

    public ThemeColor getTheme() {
        return this.theme;
    }
}
