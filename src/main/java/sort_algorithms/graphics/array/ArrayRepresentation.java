package sort_algorithms.graphics.array;

import KAGO_framework.view.DrawTool;
import sort_algorithms.animation.Easings;
import sort_algorithms.animation.tween.Tween;
import sort_algorithms.utils.misc.ColorObject;

import java.awt.*;
import java.util.ArrayList;

public class ArrayRepresentation {
    double x;
    double y;
    double width;
    double height;
    double individualWidth;
    DataRepresentation[] dataRepresentation;
    ColorObject color;

    public ArrayRepresentation(int[] array, double x, double y, double width, double height, ColorObject color) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        individualWidth = width/array.length;
        dataRepresentation = new DataRepresentation[array.length];
        int largest = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > largest) {
                largest = array[i];
            }
        }
        for (int i = 0; i < array.length; i++) {
            dataRepresentation[i] = new DataRepresentation(array[i], largest, individualWidth, height, color);
            dataRepresentation[i].setX(x + i * individualWidth);
            dataRepresentation[i].setY(y);
        }
    }

    /** Starts the animation to swap the places between 2 DataRepresentations
     *
     */
    public void switchPlaces(int fromIndex, int toIndex) {
        dataRepresentation[fromIndex].moveToX(toIndex * individualWidth);
        dataRepresentation[toIndex].moveToX(fromIndex * individualWidth);
        DataRepresentation temp = dataRepresentation[fromIndex];
        dataRepresentation[fromIndex] = dataRepresentation[toIndex];
        dataRepresentation[toIndex] = temp;
    }
    public void draw(DrawTool drawTool) {
        drawTool.push();
        drawTool.drawRectangle(x, y, width, height);
        for (int i = 0; i < dataRepresentation.length; i++) {
            dataRepresentation[i].draw(drawTool);
        }
        drawTool.pop();
    }
}
