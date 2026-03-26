package sort_algorithms.graphics.level.impl;

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
    protected SorterHistory sort(int[] array) {
        int[] arrayCopy = new int[array.length];
        SorterHistory sorterHistory = new SorterHistory("Linear Search", array);
        System.arraycopy(array, 0, arrayCopy, 0, array.length);

        sorterHistory.assignment(1);
        int indexTarget = (int) (arrayCopy.length / 2);

        sorterHistory.assignment(1);
        this.target = arrayCopy[indexTarget];

        sorterHistory.assignment(1);
        boolean f = false;

        // i = 0
        sorterHistory.assignment(1);
        for (int i = 0; i < arrayCopy.length; i++) {
            // i < arrayCopy.length
            sorterHistory.comparision(1);

            sorterHistory.comparePlaces(i, indexTarget);

            sorterHistory.comparision(1);
            if (arrayCopy[i] == this.target) {
                sorterHistory.finish("Gesuchte Zahl (" + this.target + ") wurde gefunden");

                // f = true
                sorterHistory.assignment(1);
                f = true;

                break;
            } else {
                sorterHistory.noSwitch(i, indexTarget, "%d ist nicht die gesuchte Zahl (%d)");
            }

            // i++
            sorterHistory.assignment(1);
        }

        // wenn nicht gefunden wurde und kein Break aufgerufen wurde, letzter Vergleich for-schleife
        if (!f) {
            sorterHistory.comparision(1);
        }

        sorterHistory.comparision(1);
        if (!f) {
            sorterHistory.finish("Gesuchte Zahl (" + this.target + ") wurde nicht gefunden");
        }

        return sorterHistory;
    }
}
