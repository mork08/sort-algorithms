package sort_algorithms.graphics.level.impl;

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
    protected SorterHistory sort(int[] array) {
        int[] arrayCopy = new int[array.length];
        SorterHistory sorterHistory = new SorterHistory("Insertion Sort", array);
        System.arraycopy(array, 0, arrayCopy, 0, array.length);

        // i = 1
        sorterHistory.assignment(1);
        for (int i = 1; i < arrayCopy.length; i++) {
            // i < length
            sorterHistory.comparision(1);

            sorterHistory.comparePlaces(i, i - 1);

            sorterHistory.comparision(1);
            if (arrayCopy[i] < arrayCopy[i - 1]) {
                sorterHistory.assignment(2);
                int key = arrayCopy[i];
                int j = i - 1;

                sorterHistory.comparePlaces(j, i);

                while (true) {
                    sorterHistory.comparision(1);
                    if (!(j >= 0)) break;

                    sorterHistory.comparision(1);
                    if (!(arrayCopy[j] > key)) break;

                    sorterHistory.switchPlaces(j, j + 1);

                    sorterHistory.assignment(1);
                    arrayCopy[j + 1] = arrayCopy[j];

                    sorterHistory.comparision(1);
                    if (j > 0) {
                        sorterHistory.comparePlaces(j - 1, j);
                    }

                    sorterHistory.assignment(1);
                    j = j - 1;
                }

                sorterHistory.assignment(1);
                arrayCopy[j + 1] = key;
            }
            // i++
            sorterHistory.assignment(1);
        }
        // letzter Vergleich for-schleife
        sorterHistory.comparision(1);

        sorterHistory.finish(null);
        return sorterHistory;
    }
}
