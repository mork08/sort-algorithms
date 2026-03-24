package sort_algorithms.graphics.level.impl;

import KAGO_framework.view.DrawTool;
import sort_algorithms.graphics.ThemeColor;
import sort_algorithms.graphics.level.Level;
import sort_algorithms.model.sorting.SorterHistory;
import sort_algorithms.utils.misc.ColorObject;

import java.awt.*;
import java.util.Arrays;

public class LevelBinarySearch extends Level {

    private int target;

    public LevelBinarySearch() {
        super("Binary Search", new ThemeColor(new Color(56, 89, 8), new Color(117, 206, 94), ColorObject.of("#244d22")));
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
        array = generateArray(0, 100, 10);
        Arrays.sort(array);
        int[] arrayCopy = new int[array.length];
        SorterHistory sorterHistory = new SorterHistory("Binary Search", array);
        System.arraycopy(array, 0, arrayCopy, 0, array.length);

        int indexTarget = arrayCopy.length - 1;
        this.target = arrayCopy[indexTarget];

        int left = 0;
        int right = arrayCopy.length - 1;
        boolean found = false;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            sorterHistory.comparePlaces(mid, indexTarget, "vergleiche %d mit der gesuchten Zahl %d");

            if (arrayCopy[mid] == this.target) {
                sorterHistory.finish("Gesuchte Zahl (" + this.target + ") wurde gefunden");
                found = true;
                break;

            } else if (arrayCopy[mid] < this.target) {
                sorterHistory.noSwitch(mid, indexTarget, "%d ist kleiner als die gesuchte Zahl (%d), suche rechts weiter");
                left = mid + 1;

            } else {
                sorterHistory.noSwitch(mid, indexTarget, "%d ist groesser als die gesuchte Zahl (%d), suche links weiter");
                right = mid - 1;
            }
        }
        if (!found) {
            sorterHistory.finish("Gesuchte Zahl (" + this.target + ") wurde nicht gefunden");
        }
        return sorterHistory;
    }
}
