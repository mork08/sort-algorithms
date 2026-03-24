package sort_algorithms.graphics.level.impl;

import KAGO_framework.view.DrawTool;
import sort_algorithms.graphics.ThemeColor;
import sort_algorithms.graphics.level.Level;
import sort_algorithms.model.sorting.SorterHistory;
import sort_algorithms.utils.misc.ColorObject;

public class LevelQuickSort extends Level {
    private double timer = 0.0;
    private double cooldown = 0.3;

    public LevelQuickSort() {
        super("Quick Sort", new ThemeColor(ColorObject.of("#6f13e8"), ColorObject.of("#617eff"), ColorObject.of("#3a224d")));
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
        SorterHistory sorterHistory = new SorterHistory("Quick Sort", array);
        System.arraycopy(array, 0, arrayCopy, 0, array.length);

        this.quickSort(arrayCopy, 0, arrayCopy.length - 1, sorterHistory);

        sorterHistory.finish(null);
        return sorterHistory;
    }

    private void quickSort(int[] array, int low, int high, SorterHistory sorterHistory) {
        if (low >= high) {
            return;
        }

        int pivotIndex = this.partition(array, low, high, sorterHistory);
        this.quickSort(array, low, pivotIndex - 1, sorterHistory);
        this.quickSort(array, pivotIndex + 1, high, sorterHistory);
    }

    private int partition(int[] array, int low, int high, SorterHistory sorterHistory) {
        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            sorterHistory.comparePlaces(j, high, "vergleiche %d mit dem Pivot %d");
            if (array[j] <= pivot) {
                i++;
                if (i != j) {
                    this.swap(array, i, j);
                    sorterHistory.switchPlaces(i, j, "%d wird vor das Pivot %d verschoben");
                } else {
                    sorterHistory.noSwitch(j, high, "%d ist kleiner/gleich dem Pivot %d und bleibt an seiner Position");
                }
            } else {
                sorterHistory.noSwitch(j, high, "%d ist größer als das Pivot %d und bleibt rechts");
            }
        }

        int pivotTargetIndex = i + 1;
        if (pivotTargetIndex != high) {
            this.swap(array, pivotTargetIndex, high);
            sorterHistory.switchPlaces(pivotTargetIndex, high, "das Pivot %d wird an seine endgueltige Position getauscht");
        } else {
            sorterHistory.noSwitch(high, high, "das Pivot %d ist bereits an der richtigen Position");
        }
        return pivotTargetIndex;
    }

    private void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
}
