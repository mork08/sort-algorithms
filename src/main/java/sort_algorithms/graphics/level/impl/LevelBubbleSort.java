package sort_algorithms.graphics.level.impl;

import sort_algorithms.graphics.ThemeColor;
import sort_algorithms.graphics.level.Level;
import sort_algorithms.model.sorting.SorterHistory;
import sort_algorithms.utils.misc.ColorObject;

public class LevelBubbleSort extends Level {

    public LevelBubbleSort() {
        super("Bubble Sort", new ThemeColor(ColorObject.ORANGE, ColorObject.RED, ColorObject.of("#4d222c")));
    }

    @Override
    public void onActive() {}

    @Override
    public void onHide() {}

    @Override
    protected SorterHistory sort(int[] array) {
        int[] arrayCopy = new int[array.length];
        SorterHistory sorterHistory = new SorterHistory("Bubble Sort", array);
        System.arraycopy(array, 0, arrayCopy, 0, array.length);

        sorterHistory.assignment(1);
        boolean sorted = true;

        while (sorted) {
            // while Vergleich
            sorterHistory.comparision(1);

            sorterHistory.assignment(1);
            sorted = false;

            // i = 0
            sorterHistory.assignment(1);
            for (int i = 0; i < arrayCopy.length - 1; i++) {
                // i < arrayCopy.length - 1
                sorterHistory.comparision(1);

                sorterHistory.comparePlaces(i, i + 1, null);

                sorterHistory.comparision(1);
                if (arrayCopy[i] > arrayCopy[i + 1]) {
                    sorterHistory.assignment(1);
                    sorted = true;

                    sorterHistory.assignment(3);
                    int temp = arrayCopy[i];
                    arrayCopy[i] = arrayCopy[i + 1];
                    arrayCopy[i + 1] = temp;

                    sorterHistory.switchPlaces(i, i + 1, null);

                } else {
                    sorterHistory.noSwitch(i, i + 1, null);
                }

                // i++
                sorterHistory.assignment(1);
            }
            // letzter Vergleich for-schleife
            sorterHistory.comparision(1);
        }

        sorterHistory.finish(null);
        return sorterHistory;
    }
}
