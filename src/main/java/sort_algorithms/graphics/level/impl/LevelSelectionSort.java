package sort_algorithms.graphics.level.impl;

import KAGO_framework.view.DrawTool;
import sort_algorithms.graphics.ThemeColor;
import sort_algorithms.graphics.level.Level;
import sort_algorithms.model.sorting.SorterHistory;
import sort_algorithms.utils.misc.ColorObject;

public class LevelSelectionSort extends Level {

    public LevelSelectionSort() {
        super("Selection Sort", new ThemeColor(ColorObject.ORANGE, ColorObject.RED, ColorObject.of("#4d222c")));
    }

    @Override
    public void onActive() {}

    @Override
    public void onHide() {}

    @Override
    public void update(double dt) {
        autoplay();
    }

    @Override
    public void draw(DrawTool drawTool) {
        this.visualizer.draw(drawTool);
    }

    @Override
    protected SorterHistory sort(int[] array) {
        int[] arrayCopy = new int[array.length];
        SorterHistory sorterHistory = new SorterHistory("Selection Sort", array);
        System.arraycopy(array, 0, arrayCopy, 0, array.length);

        for (int i = 0; i < arrayCopy.length - 1; i++) {
            int smallest = i;
            boolean swap = false;
            for (int j = i + 1; j < arrayCopy.length; j++) {
                sorterHistory.comparePlaces(i, j);
                if (arrayCopy[j] < arrayCopy[smallest]) {
                    smallest = j;
                    swap = true;
                }
            }
            if (swap) {
                int temp = arrayCopy[i];
                arrayCopy[i] = arrayCopy[smallest];
                arrayCopy[smallest] = temp;
                sorterHistory.switchPlaces(i, smallest);
            }

        }
        sorterHistory.finish(null);
        return sorterHistory;
    }
}
