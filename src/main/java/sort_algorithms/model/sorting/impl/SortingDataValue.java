package sort_algorithms.model.sorting.impl;

import KAGO_framework.view.DrawTool;
import sort_algorithms.Wrapper;
import sort_algorithms.animation.Easings;
import sort_algorithms.animation.tween.Tween;
import sort_algorithms.utils.misc.ColorObject;

import java.awt.*;

public class SortingDataValue {

    private final int value;
    private int[] array;
    private double x;
    private double y;
    private double width;
    private double height;
    private final ColorObject color;

    private boolean marked;

    private Tween MOVEMENT_X;
    private Tween MOVEMENT_Y;
    private Font font;

    public SortingDataValue(int value, int[] array, double x, double y, double width, double height, int fontSize, ColorObject color) {
        this.value = value;
        this.array = array;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.font = Wrapper.getCacheManager().loadFont(fontSize);

        this.marked = false;

        this.MOVEMENT_X = Tween.to(0, 0, 1.0).ease((_x) -> Easings.easeInOutElastic(_x)).onUpdate((v) -> this.x = v.getValueDouble()).loop(false);
        this.MOVEMENT_Y = Tween.to(0, 0, 1.0).ease((_x) -> Easings.easeInOutElastic(_x)).onUpdate((v) -> this.y = v.getValueDouble()).loop(false);
    }

    public void processSwitch(SortingDataValueMovementDirection dir, double x) {
        if (dir == SortingDataValueMovementDirection.UP) {
            this.setupAnimation(x, -this.height - 20.0);

        } else if (dir == SortingDataValueMovementDirection.DOWN) {
            this.setupAnimation(x, this.height + 20.0);
        }
    }

    public void processCompare() {
        this.mark();
    }

    public void mark() {
        this.marked = true;
    }

    public void unmark() {
        this.marked = false;
    }

    private void setupAnimation(double x, double yOff) {
        double startX = this.x;
        double startY = this.y;
        double middleY = startY + yOff;
        double duration = 1.0;
        this.MOVEMENT_Y.redo(startY, middleY, duration).onFinish((o) -> {
            this.MOVEMENT_X.redo(startX, x, duration).onFinish((_o) -> {
                this.MOVEMENT_Y.redo(middleY, startY, duration).onFinish(null).animate();
            }).animate();
        }).animate();
    }

    public boolean finished() {
        return this.MOVEMENT_X.isFinished() && this.MOVEMENT_Y.isFinished();
    }

    public void update(double dt) {}

    public void draw(DrawTool drawTool) {
        drawTool.push();

        drawTool.setCurrentColor(this.color);
        drawTool.drawFilledRectangle(this.x, this.y, this.width, this.height);

        if (this.marked) {
            drawTool.push();

            drawTool.setCurrentColor(this.color.bright());
            drawTool.setLineWidth(5);
            drawTool.drawRectangle(this.x, this.y, this.width, this.height);

            drawTool.pop();
        }

        drawTool.setCurrentColor(Color.WHITE);
        drawTool.setFont(this.font);
        drawTool.drawCenteredText(String.valueOf(this.value), this.x, this.y, this.width, this.height);

        drawTool.pop();
    }

    public boolean isMarked() {
        return this.marked;
    }

    public int getValue() {
        return this.value;
    }

    public int[] getArray() {
        return this.array;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }
}
