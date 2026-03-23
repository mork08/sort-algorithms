package sort_algorithms.graphics.level.impl;

import KAGO_framework.view.DrawTool;
import sort_algorithms.graphics.ThemeColor;
import sort_algorithms.graphics.level.Level;
import sort_algorithms.model.sorting.SorterHistory;
import sort_algorithms.utils.misc.ColorObject;

public class LevelBubbleSort extends Level {
    private double timer = 0.0;
    private double cooldown = 0.3;

    public LevelBubbleSort() {
        super("Bubble Sort");
        this.theme = new ThemeColor(ColorObject.ORANGE, ColorObject.RED);
    }

    @Override
    public void onActive() {}

    @Override
    public void onHide() {}

    @Override
    public void onReset() {}

    @Override
    public void update(double dt) {
        timer += dt;
        if (timer > cooldown) {
            sorterHistory.stepForward();
        }
        timer %= cooldown;
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.push();
        drawTool.setCurrentColor(this.theme.base());
        drawTool.drawFilledCircle(0,0,10);

        visualArray.draw(drawTool);
        drawTool.pop();
    }

    @Override
    protected SorterHistory sort(int[] array) {
        int[] arrayCopy = new int[array.length];
        SorterHistory sorterHistory = new SorterHistory(array, visualArray);
        System.arraycopy(array, 0, arrayCopy, 0, array.length);
        boolean sorted = true;
        while (sorted) {
            sorted = false;
            for (int i = 0; i < arrayCopy.length - 1; i++) {
                if (arrayCopy[i] > arrayCopy[i + 1]) {
                    sorted = true;
                    int temp = arrayCopy[i];
                    arrayCopy[i] = arrayCopy[i + 1];
                    arrayCopy[i + 1] = temp;
                    sorterHistory.switchPlaces(i, i + 1);
                }
            }
        }
        return sorterHistory;
    }
}
