package sort_algorithms.graphics.level.impl;

import KAGO_framework.view.DrawTool;
import sort_algorithms.graphics.ThemeColor;
import sort_algorithms.graphics.level.Level;
import sort_algorithms.model.sorting.SorterHistory;
import sort_algorithms.utils.misc.ColorObject;

import java.awt.*;

public class LevelInsertionSort extends Level {


    public LevelInsertionSort() {
        super("Insertion Sort", new ThemeColor(new Color(51, 15, 134), new Color(150, 33, 166), ColorObject.of("#3a224d")));
    }

    @Override
    public void onActive() {}

    @Override
    public void onHide() {}

    @Override
    public void update(double dt) {autoplay();}

    @Override
    public void draw(DrawTool drawTool) {
        this.visualizer.draw(drawTool);
    }

    @Override
    protected SorterHistory sort(int[] array) {
        int[] arrayCopy = new int[array.length];
        SorterHistory sorterHistory = new SorterHistory("Insertion Sort", array);
        System.arraycopy(array, 0, arrayCopy, 0, array.length);
        for (int i = 1; i < arrayCopy.length; i++) {
            sorterHistory.comparePlaces(i, i - 1);

            if (arrayCopy[i] < arrayCopy[i-1]) {
                int key = arrayCopy[i];
                int j = i - 1;
                sorterHistory.comparePlaces(j, i);
                //go back to the according position
                while (j >= 0 && arrayCopy[j] > key) {

                    sorterHistory.switchPlaces(j, j+1);

                    arrayCopy[j + 1] = arrayCopy[j];
                    if(j > 0) sorterHistory.comparePlaces(j - 1, j);
                    j = j - 1;

                }
                arrayCopy[j + 1] = key;
            }
        }
        sorterHistory.finish(null);
        return sorterHistory;
    }
}
