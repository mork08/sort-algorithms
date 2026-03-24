package sort_algorithms.graphics.level.impl;

import KAGO_framework.view.DrawTool;
import sort_algorithms.graphics.ThemeColor;
import sort_algorithms.graphics.level.Level;
import sort_algorithms.model.sorting.SorterHistory;
import sort_algorithms.utils.misc.ColorObject;

public class LevelBubbleSort extends Level {
    private double timer = 0.0;
    private double cooldown = 0.3;

    public LevelBubbleSort() {
        super("Bubble Sort");
        this.theme = new ThemeColor(ColorObject.ORANGE, ColorObject.RED);
    }

    @Override
    public void onActive() {}

    @Override
    public void onHide() {}

    @Override
    public void onReset() {}

    @Override
    public void update(double dt) {}

    @Override
    public void draw(DrawTool drawTool) {
        this.visualizer.draw(drawTool);
    }

    @Override
    protected SorterHistory sort(int[] array) {
        int[] arrayCopy = new int[array.length];
        SorterHistory sorterHistory = new SorterHistory("Bubble Sort", array);
        System.arraycopy(array, 0, arrayCopy, 0, array.length);
        boolean sorted = true;
        while (sorted) {
            sorted = false;
            for (int i = 0; i < arrayCopy.length - 1; i++) {
                sorterHistory.comparePlaces(i, i + 1, null);
                if (arrayCopy[i] > arrayCopy[i + 1]) {
                    sorted = true;
                    int temp = arrayCopy[i];
                    arrayCopy[i] = arrayCopy[i + 1];
                    arrayCopy[i + 1] = temp;
                    sorterHistory.switchPlaces(i, i + 1, null);

                } else {
                    sorterHistory.noSwitch(i, i + 1, null);
                }
            }
        }
        sorterHistory.finish(null);
        return sorterHistory;
    }
}
