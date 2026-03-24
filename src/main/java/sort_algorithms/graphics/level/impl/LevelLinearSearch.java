package sort_algorithms.graphics.level.impl;

import KAGO_framework.view.DrawTool;
import sort_algorithms.graphics.ThemeColor;
import sort_algorithms.graphics.level.Level;
import sort_algorithms.model.sorting.SorterHistory;
import sort_algorithms.utils.misc.ColorObject;

import java.awt.*;

public class LevelLinearSearch extends Level {

    private int target;

    public LevelLinearSearch() {
        super("Linear Search", new ThemeColor(new Color(133, 5, 120), new Color(240, 62, 223), ColorObject.of("#4d2249")));
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

        int indexTarget = (int)(arrayCopy.length / 2);
        this.target = arrayCopy[indexTarget];

        boolean f = false;
        for (int i = 0; i < arrayCopy.length; i++) {
            sorterHistory.comparePlaces(i, indexTarget);
            if (arrayCopy[i] == this.target) {
                sorterHistory.finish("Gesuchte Zahl (" + this.target + ") wurde gefunden");
                f = true;
                break;

            } else {
                sorterHistory.noSwitch(i, indexTarget, "%d ist nicht die gesuchte Zahl (%d)");
            }
        }
        if (!f) {
            sorterHistory.finish("Gesuchte Zahl (" + this.target + ") wurde nicht gefunden");

        }
        return sorterHistory;
    }
}
