package sort_algorithms.model.sorting.impl;

import KAGO_framework.view.DrawTool;
import sort_algorithms.Wrapper;
import sort_algorithms.model.sorting.SorterHistory;
import sort_algorithms.model.sorting.SortingStep;
import sort_algorithms.model.sorting.SortingStepData;
import sort_algorithms.model.sorting.SortingType;
import sort_algorithms.utils.misc.ColorObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingAlgorithmVisualizer {

    private final double x;
    private final double y;
    private final double width;
    private final double height;
    private final double outlineOffset;
    private final Color color;
    private final SorterHistory history;
    private final List<SortingDataValue> dataValues;
    private final Font titleFont;
    private final Font font;
    private final double cardWidth;
    private final double cardHeight;
    private final double margin;
    private final double baseY;

    private SortingDataValue last1;
    private SortingDataValue last2;

    public SortingAlgorithmVisualizer(SorterHistory history, double x, double y, double width, double height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.history = history;
        this.outlineOffset = 10;
        this.dataValues = new ArrayList<>();
        this.titleFont = Wrapper.getCacheManager().loadFont(45);
        this.font = Wrapper.getCacheManager().loadFont(20);

        double cStartX = 0;
        this.cardWidth = 80;
        this.cardHeight = 80;
        this.margin = (this.width - (this.cardWidth * this.history.getArray().length)) / (this.history.getArray().length);
        this.baseY = this.y + (this.height - this.cardHeight) / 2;

        for (int i = 0; i < this.history.getArray().length; i++) {
            this.dataValues.add(new SortingDataValue(this.getArray()[i], this.getArray(), this.x + this.margin / 2 + cStartX + i * (this.cardWidth + this.margin), this.baseY, this.cardWidth, this.cardHeight, 20, color));
        }
    }

    public void draw(DrawTool drawTool) {
        drawTool.push();

        drawTool.setCurrentColor(this.color);
        drawTool.setLineWidth(4);
        drawTool.drawRectangle(this.x - this.outlineOffset, this.y - this.outlineOffset, this.width + this.outlineOffset * 2, this.height + this.outlineOffset * 2);

        drawTool.setFont(this.titleFont);
        drawTool.setCurrentColor(ColorObject.of(this.color).invert());
        drawTool.drawCenteredText(this.history.getName().toUpperCase(), this.x - this.outlineOffset, this.y - this.outlineOffset - 80, this.width + this.outlineOffset * 2);

        SortingStepData data = this.history.getStep().data();
        int value1 = data.array()[data.index1()];
        int value2 = data.array()[data.index2()];

        drawTool.setFont(this.font);
        drawTool.setCurrentColor(this.color);
        String text = "(" + this.history.getCurrentIndex() + "/ " + this.history.getSteps() + ")  " +this.history.getStep().message(value1, value2).toUpperCase().replaceAll(" ", "  ");
        drawTool.drawCenteredText(text, this.x - this.outlineOffset, this.y - this.outlineOffset - 40, this.width + this.outlineOffset * 2);

        for (SortingDataValue val : this.dataValues) {
            val.draw(drawTool);
        }

        drawTool.pop();
    }

    public boolean allowSkip() {
        SortingStep step = this.history.getStep();
        if (step.type() != SortingType.SWITCH) {
            return true;
        }
        if (this.last1 == null || this.last2 == null) {
            return true;
        }
        if (this.last1 == this.last2) {
            return this.last1.finished();
        }
        return this.last1.finished() && this.last2.finished();
    }

    public void processStep() {
        this.dataValues.forEach(SortingDataValue::unmark);

        SortingStep step = this.history.getStep();
        int i1 = step.data().index1();
        int i2 = step.data().index2();
        SortingDataValue v1 = this.dataValues.get(i1);
        SortingDataValue v2 = this.dataValues.get(i2);
        v1.mark();
        v2.mark();
        this.last1 = v1;
        this.last2 = v2;

        switch (step.type()) {
            case SWITCH -> {
                Collections.swap(this.dataValues, i1, i2);
                v1.processSwitch(SortingDataValueMovementDirection.UP, v2.getX());
                v2.processSwitch(SortingDataValueMovementDirection.DOWN, v1.getX());
            }
            case COMPARE, NO_SWITCH -> {
                v1.processCompare();
                v2.processCompare();
            }
            case FINISH -> {
                this.dataValues.forEach(SortingDataValue::unmark);
            }
        }
    }

    public SorterHistory getHistory() {
        return this.history;
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

    private int[] getArray() {
        return this.history.getStep().data().array();
    }

    public void setAnimationDuration(double animationDuration) {
        dataValues.forEach(value -> value.setAnimationDuration(animationDuration));
    }
}
