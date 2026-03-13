package sort_algorithms.graphics.level.impl;

import KAGO_framework.view.DrawTool;
import sort_algorithms.graphics.ThemeColor;
import sort_algorithms.graphics.level.Level;
import sort_algorithms.utils.misc.ColorObject;

public class LevelSelectionSort extends Level {

    public LevelSelectionSort() {
        super("Selection Sort");
        this.theme = new ThemeColor(ColorObject.GREEN, ColorObject.BLACK);
    }

    @Override
    public void onActive() {}

    @Override
    public void onHide() {}

    @Override
    public void onReset() {}

    @Override
    public void update(double dt) {}

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.push();
        drawTool.setCurrentColor(this.theme.base());
        drawTool.drawFilledCircle(100, 100, 50);
        drawTool.pop();
    }
}
