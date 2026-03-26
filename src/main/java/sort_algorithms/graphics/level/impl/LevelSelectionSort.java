package sort_algorithms.graphics.level.impl;

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
    protected SorterHistory sort(int[] array) {
        int[] arrayCopy = new int[array.length];
        SorterHistory sorterHistory = new SorterHistory("Selection Sort", array);
        System.arraycopy(array, 0, arrayCopy, 0, array.length);

        // i = 0
        sorterHistory.assignment(1);
        for (int i = 0; i < arrayCopy.length - 1; i++) {
            // i < arrayCopy.length - 1
            sorterHistory.comparision(1);

            sorterHistory.assignment(2);
            int smallest = i;
            boolean swap = false;

            // j = i + 1
            sorterHistory.assignment(1);
            for (int j = i + 1; j < arrayCopy.length; j++) {
                // j < arrayCopy.length
                sorterHistory.comparision(1);

                sorterHistory.comparePlaces(i, j);

                sorterHistory.comparision(1);
                if (arrayCopy[j] < arrayCopy[smallest]) {
                    sorterHistory.assignment(2);
                    smallest = j;
                    swap = true;
                }

                // j++
                sorterHistory.assignment(1);
            }
            // letzter Vergleich for-schleife
            sorterHistory.comparision(1);

            sorterHistory.comparision(1);
            if (swap) {
                sorterHistory.assignment(3);
                int temp = arrayCopy[i];
                arrayCopy[i] = arrayCopy[smallest];
                arrayCopy[smallest] = temp;

                sorterHistory.switchPlaces(i, smallest);
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
