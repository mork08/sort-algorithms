package sort_algorithms.graphics.level.impl;

import sort_algorithms.graphics.ThemeColor;
import sort_algorithms.graphics.level.Level;
import sort_algorithms.model.sorting.SorterHistory;
import sort_algorithms.utils.misc.ColorObject;

public class LevelQuickSort extends Level {

    public LevelQuickSort() {
        super("Quick Sort", new ThemeColor(ColorObject.of("#6f13e8"), ColorObject.of("#617eff"), ColorObject.of("#3a224d")));
    }

    @Override
    public void onActive() {}

    @Override
    public void onHide() {}

    @Override
    protected SorterHistory sort(int[] array) {
        int[] arrayCopy = new int[array.length];
        SorterHistory sorterHistory = new SorterHistory("Quick Sort", array);
        System.arraycopy(array, 0, arrayCopy, 0, array.length);

        this.quickSort(arrayCopy, 0, arrayCopy.length - 1, sorterHistory);

        sorterHistory.finish(null);
        return sorterHistory;
    }

    private void quickSort(int[] array, int low, int high, SorterHistory sorterHistory) {
        sorterHistory.comparision(1);
        if (low >= high) {
            return;
        }

        sorterHistory.assignment(1);
        int pivotIndex = this.partition(array, low, high, sorterHistory);
        this.quickSort(array, low, pivotIndex - 1, sorterHistory);
        this.quickSort(array, pivotIndex + 1, high, sorterHistory);
    }

    private int partition(int[] array, int low, int high, SorterHistory sorterHistory) {
        sorterHistory.assignment(2);
        int pivot = array[high];
        int i = low - 1;

        // j = low
        sorterHistory.assignment(1);
        for (int j = low; j < high; j++) {
            // j < high
            sorterHistory.comparision(1);

            sorterHistory.comparePlaces(j, high, "vergleiche %d mit dem Pivot %d");

            sorterHistory.comparision(1);
            if (array[j] <= pivot) {
                sorterHistory.assignment(1);
                i++;

                sorterHistory.comparision(1);
                if (i != j) {
                    this.swap(array, i, j, sorterHistory);
                    sorterHistory.switchPlaces(i, j, "%d wird vor das Pivot %d verschoben");
                } else {
                    sorterHistory.noSwitch(j, high, "%d ist kleiner/gleich dem Pivot %d und bleibt an seiner Position");
                }
            } else {
                sorterHistory.noSwitch(j, high, "%d ist größer als das Pivot %d und bleibt rechts");
            }

            // j++
            sorterHistory.assignment(1);
        }
        // Letzter Vergleich for-schleife
        sorterHistory.comparision(1);

        sorterHistory.assignment(1);
        int pivotTargetIndex = i + 1;

        sorterHistory.comparision(1);
        if (pivotTargetIndex != high) {
            this.swap(array, pivotTargetIndex, high, sorterHistory);
            sorterHistory.switchPlaces(pivotTargetIndex, high, "das Pivot %d wird an seine endgueltige Position getauscht");
        } else {
            sorterHistory.noSwitch(high, high, "das Pivot %d ist bereits an der richtigen Position");
        }
        return pivotTargetIndex;
    }

    private void swap(int[] array, int index1, int index2, SorterHistory sorterHistory) {
        sorterHistory.assignment(3);

        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
}
